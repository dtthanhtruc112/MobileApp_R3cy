package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapter.BlogAdapter;
import com.example.adapter.BlogRelatedApdater;
import com.example.databases.R3cyDB;
import com.example.models.Blog;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityBlogListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;


public class BlogList extends AppCompatActivity {
    ActivityBlogListBinding binding;
    R3cyDB db;
    String email;
    BlogAdapter adapter;
    List<Blog> blogList;
    int selectedBlogId;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở Bloglist: " + email);

        createDb();
        loadData();

        addEvents();


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
        blogList = db.getAllBlogs();
        Log.i("blogList", "Number of items retrieved: " + blogList.size());
        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new BlogAdapter(this, R.layout.item_layout_bloglist, blogList);
        binding.lvBlogList.setAdapter(adapter);
    }

    private void addEvents() {
        binding.lvBlogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBlogId = blogList.get(position).getBlogId();

                Intent intent = new Intent(BlogList.this, BlogDetail.class);
                intent.putExtra("blogId", selectedBlogId);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_blog);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(), Product_List.class);
                    intent1.putExtra("key_email", email);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    return true;
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(),AboutUs.class);
                    intent3.putExtra("key_email", email);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(),UserAccount_Main.class);
                    intent4.putExtra("key_email", email);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_home) {
                    Intent intent5 =new Intent(getApplicationContext(),TrangChu.class);
                    intent5.putExtra("key_email", email);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;}


        });
    }
    public void AddEventForImvThumb(int selectedBlogId){
        Log.i("selectedBlogId", "selectedBlogId: " + selectedBlogId);
        Intent intent = new Intent(BlogList.this, BlogDetail.class);
        intent.putExtra("blogId", selectedBlogId);
        intent.putExtra("key_email", email);
        startActivity(intent);
    }
    public void AddEventForTitle(int selectedBlogId){
        Log.i("selectedBlogId", "selectedBlogId: " + selectedBlogId);
        Intent intent = new Intent(BlogList.this, BlogDetail.class);
        intent.putExtra("blogId", selectedBlogId);
        intent.putExtra("key_email", email);
        startActivity(intent);
    }

}