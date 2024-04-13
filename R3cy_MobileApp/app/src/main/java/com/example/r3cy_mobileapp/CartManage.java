package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.r3cy_mobileapp.databinding.ActivityCartManageBinding;

public class CartManage extends AppCompatActivity {

    ActivityCartManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}