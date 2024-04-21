package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityAboutUsBinding;

public class AboutUs extends AppCompatActivity {

    ActivityAboutUsBinding binding;
    private ImageView imvtrangchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

        imvtrangchu = findViewById(R.id.imvtrangchu);

        imvtrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUs.this, TrangChu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addEvents() {
        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AboutUs.this, Thugom.class);
                startActivity(intent);
            }
        });

        binding.btnOpenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AboutUs.this, Product_List.class);
                startActivity(intent);
            }
        });
    }
}