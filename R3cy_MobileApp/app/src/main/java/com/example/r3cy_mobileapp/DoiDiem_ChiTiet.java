package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.models.Coupon;
import com.example.models.Product;
import com.example.r3cy_mobileapp.databinding.ActivityDoiDiemChiTietBinding;

import java.util.Date;

public class DoiDiem_ChiTiet extends AppCompatActivity {
    
    ActivityDoiDiemChiTietBinding binding;
    Coupon coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoiDiemChiTietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        getData();
    }

    private void getData() {
        Intent intent =  getIntent();

        Bundle bundle = intent.getBundleExtra("Package");
        Coupon coupon = (Coupon) bundle.getSerializable("COUPON");


        binding.txtCode.setText(coupon.getCOUPON_CODE());
        binding.txtTitle.setText(coupon.getCOUPON_TITLE());

        // Lấy ngày hiện tại
        Date currentDate = new Date();

        // Lấy ngày hết hạn của coupon
        Date expireDate = coupon.getEXPIRE_DATE();

        // Tính số ngày còn lại giữa ngày hết hạn và ngày hiện tại
        long diffInMillies = expireDate.getTime() - currentDate.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

        // Hiển thị số ngày còn lại trên giao diện của bạn
        binding.txtDayLeft.setText(String.valueOf(diffInDays));

    }
}