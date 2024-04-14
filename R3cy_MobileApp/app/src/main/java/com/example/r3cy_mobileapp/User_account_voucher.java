package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountVoucherBinding;

public class User_account_voucher extends AppCompatActivity {
    ActivityUserAccountVoucherBinding binding;
    Coupon coupon;
    R3cyDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

}
