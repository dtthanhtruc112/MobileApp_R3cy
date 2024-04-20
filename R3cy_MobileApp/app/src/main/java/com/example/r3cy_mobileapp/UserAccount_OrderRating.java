package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Order;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountOrderRatingBinding;

public class UserAccount_OrderRating extends AppCompatActivity {
    ActivityUserAccountOrderRatingBinding binding;
    Order order;
    R3cyDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountOrderRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new R3cyDB(this);
        db.createSampleDataFeedback();

        addEvents();
    }

    private void addEvents() {
    }

    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null && bundle.containsKey("ORDER")) {
            order = (Order) bundle.getSerializable("ORDER");
            if (order != null) {
                binding.txtproductname.setText(order.getProductName());
                binding.productcount.setText(String.valueOf(order.getQuantity()));
                binding.productprice.setText(String.valueOf(order.getProductPrice()));
                byte[] imageData = order.getProductImg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                binding.productimg.setImageBitmap(bitmap);
            }
        }
    }
}