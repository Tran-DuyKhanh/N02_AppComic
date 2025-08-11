package com.example.n02_appcomic.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.n02_appcomic.ComicDetailActivity;
import com.example.n02_appcomic.R;
import com.example.n02_appcomic.model.Category;
import com.example.n02_appcomic.model.ChaptersLatest;
import com.example.n02_appcomic.model.Item;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private List<Item> favorites;

    public FavoriteAdapter(Context context, List<Item> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Item item = favorites.get(position);

        // Tên truyện
        holder.txtTitle.setText(item.getName());

        // Tác giả
        if (item.getAuthor() != null && !item.getAuthor().isEmpty()) {
            holder.txtAuthor.setText(item.getAuthor().get(0));
        } else {
            holder.txtAuthor.setText("Không rõ");
        }

        // Chap mới nhất
        // Thể loại
        if (item.getCategory() != null && !item.getCategory().isEmpty()) {
            // Lấy danh sách tên thể loại và nối bằng dấu phẩy
            StringBuilder categoriesText = new StringBuilder();
            for (Category category : item.getCategory()) {
                if (categoriesText.length() > 0) categoriesText.append(", ");
                categoriesText.append(category.getName());
            }
            holder.txtCategory.setText(categoriesText.toString());
        } else {
            holder.txtCategory.setText("Không rõ thể loại");
        }


        //Log.d("Chuong", latestChapters.toString());
        // Load ảnh bằng Glide
        String imageUrl = "https://img.otruyenapi.com/uploads/comics/" + item.getThumbURL();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bg_image)
                .into(holder.imgComic);

        //click load truyện
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComicDetailActivity.class);
            intent.putExtra("slug", item.getSlug());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgComic;
        TextView txtTitle, txtAuthor, txtCategory;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComic = itemView.findViewById(R.id.imgComic);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }
}


