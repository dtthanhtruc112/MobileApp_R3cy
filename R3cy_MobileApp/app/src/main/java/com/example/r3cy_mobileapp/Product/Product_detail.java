package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.PhotoViewpager2Adapter;
import com.example.models.Photo;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Product_detail extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> photos;
    private TextView txtQuantity;
    private ImageView btnIncreaseQuantity;
    private ImageView btnDecreaseQuantity;
    private List<Integer> imageResourceIds;
    private PhotoViewpager2Adapter adapter;
    private int quantity = 1; //Giá trị mặc định số lượng là 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator3);



        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);

        imageResourceIds = new ArrayList<>();


//        giá trị mặc định của số lượng
        txtQuantity.setText(String.valueOf(quantity));

//        sự kiện khi click vào nút +
        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });
//        sự kiện khi click -
        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

//        Truy xuất thông tin sản phẩm từ intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");
            showProductDetail(product);

            // Extract image resource IDs from product
//            imageResourceIds.add(product.getProductThumb()); // Add productThumb
//            imageResourceIds.add(product.getListImage1()); // Add listImage1
//            imageResourceIds.add(product.getListImage2()); // Add listImage2

            // Update ViewPager2 adapter with image resource IDs
            adapter = new PhotoViewpager2Adapter(imageResourceIds); // Change adapter to ImageViewPagerAdapter
            mViewPager2.setAdapter(adapter);
            mCircleIndicator3.setViewPager(mViewPager2);
        }

    }


    private void showProductDetail(Product product) {
        binding.txtProductName.setText(product.getProductName());
        binding.txtSalePrice.setText(String.format("%.0f đ", product.getSalePrice()));
        binding.txtProductDescription.setText(product.getProductDescription());
//        binding.txtProductRate.setText(String.valueOf(product.getProductRate()));
//        binding.txtDiscuss.setText(product.getDiscussContent());

    }




}