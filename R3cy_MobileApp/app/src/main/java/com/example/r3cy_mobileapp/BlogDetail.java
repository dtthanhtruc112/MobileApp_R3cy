package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.r3cy_mobileapp.databinding.ActivityBlogDetailBinding;

public class BlogDetail extends AppCompatActivity {

    ActivityBlogDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}