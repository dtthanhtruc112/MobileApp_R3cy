package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.r3cy_mobileapp.databinding.ActivityUserAccountPaymentBinding;

public class UserAccount_Payment extends AppCompatActivity {
    ActivityUserAccountPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}