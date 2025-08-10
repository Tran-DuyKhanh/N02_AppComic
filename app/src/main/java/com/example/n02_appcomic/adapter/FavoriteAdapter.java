package com.example.n02_appcomic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.n02_appcomic.R;
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
//        if (item.getCategory() != null && !item.getCategory().isEmpty()) {
//            holder.txtCategory.setText(item.getChaptersLatests().get(0).getChapterName());
//        } else {
//            holder.txtCategory.setText("Chưa có chap");
//        }
        List<ChaptersLatest> latestChapters = item.getChaptersLatests();
        if (latestChapters != null && !latestChapters.isEmpty()) {
            String chapterText = latestChapters.get(0).getChapterName(); // hoặc getChapterTitle()
            holder.txtCategory.setText("Chương "+ chapterText);
        } else {
            holder.txtCategory.setText("Chưa có chương");
        }

        // Load ảnh bằng Glide
        String imageUrl = "https://img.otruyenapi.com/uploads/comics/" + item.getThumbURL();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bg_image)
                .into(holder.imgComic);
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


