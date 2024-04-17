package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.r3cy_mobileapp.databinding.ActivityCheckoutAddressListBinding;

public class Checkout_AddressList extends AppCompatActivity {

    ActivityCheckoutAddressListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckoutAddressListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}