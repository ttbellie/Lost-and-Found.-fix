package com.example.lostandfound;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public ItemAdapter(List<Item> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);

        holder.tvTitle.setText(item.getPostType() + ": " + item.getName());
        holder.tvCategory.setText(item.getCategory());
        holder.tvTimeAgo.setText(getTimeAgo(item.getCreatedAt()));

        // Load image
        if (item.getImagePath() != null) {
            File imgFile = new File(item.getImagePath());
            if (imgFile.exists()) {
                holder.ivThumbnail.setImageURI(Uri.fromFile(imgFile));
            }
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String getTimeAgo(String createdAt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date created = sdf.parse(createdAt);
            long diffMs = new Date().getTime() - created.getTime();

            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMs);
            long hours = TimeUnit.MILLISECONDS.toHours(diffMs);
            long days = TimeUnit.MILLISECONDS.toDays(diffMs);

            if (minutes < 1) return "Just now";
            if (minutes < 60) return minutes + " min ago";
            if (hours < 24) return hours + " hours ago";
            if (days == 1) return "1 day ago";
            return days + " days ago";
        } catch (ParseException e) {
            return createdAt;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvCategory, tvTimeAgo;

        ViewHolder(View view) {
            super(view);
            ivThumbnail = view.findViewById(R.id.ivThumbnail);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvCategory = view.findViewById(R.id.tvCategory);
            tvTimeAgo = view.findViewById(R.id.tvTimeAgo);
        }
    }
}
