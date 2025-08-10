package com.example.n02_appcomic;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n02_appcomic.adapter.ChapterImageAdapter;
import com.example.n02_appcomic.model.ChapterImage;
import com.example.n02_appcomic.model.responsive.ChapterContentResponse;
import com.example.n02_appcomic.viewmodel.ComicViewModel;

import java.util.List;

//public class ReadChapterActivity extends AppCompatActivity {
//    private ComicViewModel comicViewModel;
//    private ChapterImageAdapter chapterImageAdapter;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reading_chapter);
//        RecyclerView recyclerView = findViewById(R.id.rcvImageChapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
//        String chapterApiUrl = getIntent().getStringExtra("chapter_api_url");
//        comicViewModel.getChapterImages(chapterApiUrl).observe(this, response -> {
//            List<ChapterImage> chapterImages = response.getData().getItem().getChapter_image();
//            chapterImageAdapter = new ChapterImageAdapter(chapterImages, response.getData().getDomain_cdn(), response.getData().getItem().getChapter_path());
//            recyclerView.setAdapter(chapterImageAdapter);
//        });
//
//
//    }
//}
public class ReadChapterActivity extends AppCompatActivity {
    private ComicViewModel comicViewModel;
    private ChapterImageAdapter chapterImageAdapter;
    private RecyclerView recyclerView;

    private List<String> chapterApiList;
    private int currentIndex = 0;
    private Observer<ChapterContentResponse> chapterObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_chapter);

        recyclerView = findViewById(R.id.rcvImageChapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);

        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        chapterApiList = getIntent().getStringArrayListExtra("chapter_api_list");
        currentIndex = getIntent().getIntExtra("current_index", 0);

        Button btnPrev = findViewById(R.id.btnPreviousChapter);
        Button btnNext = findViewById(R.id.btnNextChapter);

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadChapter();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < chapterApiList.size() - 1) {
                currentIndex++;
                loadChapter();
            }
        });

        // Observer chỉ tạo 1 lần
        chapterObserver = response -> {
            if (response != null && response.getData() != null) {
                List<ChapterImage> chapterImages = response.getData().getItem().getChapter_image();
                chapterImageAdapter = new ChapterImageAdapter(
                        this,
                        chapterImages,
                        response.getData().getDomain_cdn(),
                        response.getData().getItem().getChapter_path()
                );
                recyclerView.setAdapter(chapterImageAdapter);
                recyclerView.scrollToPosition(0);
            }
        };

        loadChapter(); // load chap đầu tiên
    }

    private void loadChapter() {
        if (chapterApiList == null || chapterApiList.isEmpty()) return;
        String apiUrl = chapterApiList.get(currentIndex);
        comicViewModel.getChapterImages(apiUrl).observe(this, chapterObserver);
    }
}


