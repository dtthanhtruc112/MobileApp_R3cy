package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.adapter.ProductAdapter;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;

public class Product_detail extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        truy xuat doi tuong product tu intent
        product = (Product) getIntent().getSerializableExtra("product");

//        hien thi san pham chi tiet
        if(product != null) {
            binding.imvThumb.setImageResource(product.getProductThumb());
            binding.txtProductName.setText(product.getProductName());
            binding.txtProductPrice.setText(String.valueOf(product.getProductPrice()));
            binding.txtProductDescription.setText(product.getProductDescription());


        }
    }


}