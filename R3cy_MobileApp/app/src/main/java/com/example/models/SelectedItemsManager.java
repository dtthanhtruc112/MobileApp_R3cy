package com.example.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SelectedItemsManager {
    private static final String SHARED_PREFS_NAME = "selected_items";

    // Lưu trạng thái của một item vào SharedPreferences
    public static void saveItemState(Context context, int itemId, boolean isSelected) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(String.valueOf(itemId), isSelected);
        editor.apply();
    }

    // Đọc trạng thái của một item từ SharedPreferences
    public static boolean getItemState(Context context, int itemId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        // Nếu không tìm thấy trạng thái của item, mặc định là false
        return prefs.getBoolean(String.valueOf(itemId), false);
    }
}