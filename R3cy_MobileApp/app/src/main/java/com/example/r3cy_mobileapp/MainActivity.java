package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.r3cy_mobileapp.Modau.Modau1;
import com.example.r3cy_mobileapp.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Kiểm tra xem ứng dụng đã được tải lần đầu tiên hay chưa
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // Nếu là lần chạy đầu tiên, mở Modau1 và đánh dấu đã chạy lần đầu
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, Modau1.class);
            startActivity(intent);
        } else {
            // Nếu không phải lần chạy đầu tiên, mở TrangChu
            Intent intent = new Intent(MainActivity.this, TrangChu.class);
            startActivity(intent);
        }

        // Kết thúc MainActivity để ngăn nó được hiển thị lại
        finish();
    }

    private void playSound() {
        try {
            AssetFileDescriptor descriptor = assetManager.openFd("sounds/ting.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}