package com.example.lostandfound;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DatabaseHelper(this);
        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Item item = dbHelper.getItemById(itemId);
        if (item == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvTimeAgo = findViewById(R.id.tvDetailTimeAgo);
        TextView tvLocation = findViewById(R.id.tvDetailLocation);
        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        TextView tvPhone = findViewById(R.id.tvDetailPhone);
        TextView tvDate = findViewById(R.id.tvDetailDate);
        Button btnRemove = findViewById(R.id.btnRemove);

        // Populate data
        tvTitle.setText(item.getPostType() + " " + item.getName());
        tvTimeAgo.setText(getTimeAgo(item.getCreatedAt()));
        tvLocation.setText("At " + item.getLocation());
        tvCategory.setText("Category: " + item.getCategory());
        tvDescription.setText(item.getDescription());
        tvPhone.setText("Contact: " + item.getPhone());
        tvDate.setText("Date: " + item.getDate());

        // Load image
        if (item.getImagePath() != null) {
            File imgFile = new File(item.getImagePath());
            if (imgFile.exists()) {
                ivImage.setImageURI(Uri.fromFile(imgFile));
            }
        }

        // Remove button
        btnRemove.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Remove Item")
                    .setMessage("Are you sure you want to remove this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteItem(itemId);
                        Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(item.getPostType() + " " + item.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
