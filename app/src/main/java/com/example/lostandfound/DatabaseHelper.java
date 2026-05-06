package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lostandfound.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_ITEMS = "items";

    // Column names
    private static final String COL_ID = "id";
    private static final String COL_POST_TYPE = "post_type";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_DATE = "date";
    private static final String COL_LOCATION = "location";
    private static final String COL_CATEGORY = "category";
    private static final String COL_IMAGE_PATH = "image_path";
    private static final String COL_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_POST_TYPE + " TEXT NOT NULL, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_PHONE + " TEXT NOT NULL, "
                + COL_DESCRIPTION + " TEXT NOT NULL, "
                + COL_DATE + " TEXT NOT NULL, "
                + COL_LOCATION + " TEXT NOT NULL, "
                + COL_CATEGORY + " TEXT NOT NULL, "
                + COL_IMAGE_PATH + " TEXT, "
                + COL_CREATED_AT + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    // Insert a new item
    public long insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_POST_TYPE, item.getPostType());
        values.put(COL_NAME, item.getName());
        values.put(COL_PHONE, item.getPhone());
        values.put(COL_DESCRIPTION, item.getDescription());
        values.put(COL_DATE, item.getDate());
        values.put(COL_LOCATION, item.getLocation());
        values.put(COL_CATEGORY, item.getCategory());
        values.put(COL_IMAGE_PATH, item.getImagePath());

        // Auto-generate timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        values.put(COL_CREATED_AT, sdf.format(new Date()));

        long id = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return id;
    }

    // Get all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS + " ORDER BY " + COL_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    // Get items filtered by category
    public List<Item> getItemsByCategory(String category) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS + " WHERE " + COL_CATEGORY + " = ? ORDER BY " + COL_ID + " DESC",
                new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                items.add(cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    // Get a single item by ID
    public Item getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS + " WHERE " + COL_ID + " = ?",
                new String[]{String.valueOf(id)});

        Item item = null;
        if (cursor.moveToFirst()) {
            item = cursorToItem(cursor);
        }
        cursor.close();
        db.close();
        return item;
    }

    // Delete an item by ID
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Helper: Convert cursor row to Item object
    private Item cursorToItem(Cursor cursor) {
        return new Item(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_POST_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_PATH)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
        );
    }
}
