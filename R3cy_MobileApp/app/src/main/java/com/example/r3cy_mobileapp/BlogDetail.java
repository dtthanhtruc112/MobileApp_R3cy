package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapter.BlogAdapter;
import com.example.adapter.BlogRelatedApdater;
import com.example.databases.R3cyDB;
import com.example.models.Blog;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityBlogDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class BlogDetail extends AppCompatActivity {

    ActivityBlogDetailBinding binding;
    R3cyDB db;
    String email;
    BlogAdapter adapter;
    BlogRelatedApdater adapter1;
    List<Blog> blogList;
    Blog blog;
    int selectedBlogId;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");
        selectedBlogId = getIntent().getIntExtra("blogId", selectedBlogId);

        Log.d("SharedPreferences", "Email ở BlogDetails: " + email);

        createDb();
        loadDataDetail();
        loadDataSuggest();
        addEvents();

    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.lvSuggestBlogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBlogId = blogList.get(position).getBlogId();

                Intent intent = new Intent(BlogDetail.this, BlogDetail.class);
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

    private void loadDataDetail() {
        blog = db.getBlogById(selectedBlogId);
        if (blog != null) {
            String blogTitle = blog.getBlogTitle();
            binding.txtblogTitle.setText(blogTitle);
            binding.txtblogContent.setText(blog.getBlogContent());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(blog.getBlogDate());
            binding.txtblogDate.setText(  "Ngày đăng: "+ formattedDate);
        } else {
            Log.i("LoadBlogDetail", "Blog " + blog);
        }

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

    private void loadDataSuggest() {
        blogList = db.getAllBlogs();
        Log.i("blogList", "Number of items retrieved: " + blogList.size());

        if (selectedBlogId != 0) {
            Iterator<Blog> iterator = blogList.iterator();
            while (iterator.hasNext()) {
                Blog blog = iterator.next();
                if (blog.getBlogId() == selectedBlogId) {
                    iterator.remove();
                    break;
                }
            }
        }
        // Khởi tạo adapter và thiết lập cho ListView
        adapter1 = new BlogRelatedApdater(this, R.layout.related_blog_itemlayout, blogList);
        binding.lvSuggestBlogList.setAdapter(adapter1);
    }
}