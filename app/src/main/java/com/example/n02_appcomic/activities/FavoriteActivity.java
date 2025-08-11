package com.example.n02_appcomic.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n02_appcomic.R;
import com.example.n02_appcomic.adapter.FavoriteAdapter;
import com.example.n02_appcomic.database.DatabaseHelper;
import com.example.n02_appcomic.model.Item;
import com.example.n02_appcomic.utils.SessionManager;
import com.example.n02_appcomic.viewmodel.ComicViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerFavorites;
    FavoriteAdapter adapter;
    DatabaseHelper dbHelper;
    SessionManager sessionManager;
    private ComicViewModel comicViewModel;
    private List<Item> favoriteComics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarFavorites);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Yêu thích");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // RecyclerView
        recyclerFavorites = findViewById(R.id.recyclerFavorites);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));

        // DB & Session
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        int userId = sessionManager.getUserId();

        // Adapter (gắn với favoriteComics)
        adapter = new FavoriteAdapter(this, favoriteComics);
        recyclerFavorites.setAdapter(adapter);

        // ViewModel
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        // Load từ API
        loadFavorites(userId);
    }

    private void loadFavorites(int userID) {
        List<String> slugs = dbHelper.getAllFavoriteSlugs(userID); // chỉ lấy slug

        for (String slug : slugs) {
            comicViewModel.getComicDetail(slug).observe(this, comic -> {
                if (comic != null) {
                    favoriteComics.add(comic);
                    adapter.notifyItemInserted(favoriteComics.size() - 1);
                }
            });
        }
    }
}