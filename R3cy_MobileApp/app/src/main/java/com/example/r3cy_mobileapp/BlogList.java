package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.adapter.BlogAdapter;
import com.example.adapter.BlogRelatedApdater;
import com.example.databases.R3cyDB;
import com.example.models.Blog;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityBlogListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;


public class BlogList extends AppCompatActivity {
    com.example.r3cy_mobileapp.databinding.ActivityBlogListBinding binding;
    R3cyDB db;
    String email;
    Customer customer;
    BlogAdapter adapter;
    BlogRelatedApdater adapter1;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.r3cy_mobileapp.databinding.ActivityBlogListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở cartmanage: " + email);

        createDb();


    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }


    private void createDb() {
        db = new R3cyDB(this);

        if (db != null) {
            Log.d("CartManage", "Database created successfully");
        } else {
            Log.e("CartManage", "Failed to create database");
        }
        db.createSampleDataBlog();

    }

    private void loadData() {

    }

}