package com.example.n02_appcomic.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n02_appcomic.R;
import com.example.n02_appcomic.adapter.FavoriteAdapter;
import com.example.n02_appcomic.database.DatabaseHelper;
import com.example.n02_appcomic.model.Item;
import com.example.n02_appcomic.utils.SessionManager;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerFavorites;
    FavoriteAdapter adapter;
    DatabaseHelper dbHelper;
    SessionManager sessionManager; // nếu bạn dùng để lưu userId

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
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Ánh xạ RecyclerView
        recyclerFavorites = findViewById(R.id.recyclerFavorites);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));

        // DB & Session
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        int userId = sessionManager.getUserId(); // Lấy ID người dùng đã login

        // Lấy danh sách từ DB
        List<Item> favoriteList = dbHelper.getFavorites(userId);

        // Gán adapter
        adapter = new FavoriteAdapter(this, favoriteList);
        recyclerFavorites.setAdapter(adapter);
    }
}
