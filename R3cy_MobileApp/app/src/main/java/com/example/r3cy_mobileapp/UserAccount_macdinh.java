package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;

import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountMacdinhBinding;

import java.util.concurrent.Executor;

public class UserAccount_macdinh extends AppCompatActivity {
    ActivityUserAccountMacdinhBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountMacdinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        View.OnClickListener signUpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_macdinh.this, Signup.class);
                startActivity(intent);
            }
        };
        View.OnClickListener signInClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_macdinh.this, Signin_Main.class);
                startActivity(intent);
            }
        };

        binding.iconpen.setOnClickListener(signUpClickListener);
        binding.imvUservatar.setOnClickListener(signUpClickListener);
        binding.usInfo.setOnClickListener(signUpClickListener);
        binding.usPolicy.setOnClickListener(signUpClickListener);
        binding.usManageOrder.setOnClickListener(signUpClickListener);
        binding.usAddress.setOnClickListener(signUpClickListener);
        binding.usPayment.setOnClickListener(signUpClickListener);
        binding.usVoucher.setOnClickListener(signUpClickListener);
        binding.btnchoxuly.setOnClickListener(signUpClickListener);
        binding.txtchoxuly.setOnClickListener(signUpClickListener);
        binding.btncholayhang.setOnClickListener(signUpClickListener);
        binding.txtcholayhang.setOnClickListener(signUpClickListener);
        binding.btndanggiao.setOnClickListener(signUpClickListener);
        binding.txtdanggiao.setOnClickListener(signUpClickListener);
        binding.btndanhgia.setOnClickListener(signUpClickListener);
        binding.txtdanhgia.setOnClickListener(signUpClickListener);
        binding.btndangky.setOnClickListener(signUpClickListener);
        binding.btndangnhap.setOnClickListener(signInClickListener);
    };



}