package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.PhotoViewpager2Adapter;
import com.example.databases.R3cyDB;
//import com.example.models.Discuss;
import com.example.models.Photo;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Product_Detail extends AppCompatActivity {
    ActivityProductDetailBinding binding;

    TextView txtQuantity, txtProductName, txtSalePrice, txtProductDescription, txtProductRate, txtDiscussContent, txtRespondContent;
    ImageView btnIncreaseQuantity;
    ImageView btnDecreaseQuantity, imvProductThumb;
    EditText edtDiscuss, edtCusMail;
    private List<Integer> imageResourceIds;
    private PhotoViewpager2Adapter adapter;
    private int quantity = 1; //Giá trị mặc định số lượng là 1

    private SQLiteDatabase database;
    int ProductID = -1;
    R3cyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        txtProductName = findViewById(R.id.txtProductName);
        txtSalePrice = findViewById(R.id.txtSalePrice);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductRate = findViewById(R.id.txtProductRate);
        imvProductThumb= findViewById(R.id.imvProductThumb);


        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        txtDiscussContent = findViewById(R.id.txtDiscussContent);
        txtRespondContent = findViewById(R.id.txtRespondContent);



//        giá trị mặc định của số lượng
        txtQuantity.setText(String.valueOf(quantity));


        //    truy xuất thông tin sản phẩm từ intent
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("ProductName") && intent.hasExtra("SalePrice") && intent.hasExtra("ProductDescription") && intent.hasExtra("ProductRate") && intent.hasExtra("ProductThumb")) {
            String productName = intent.getStringExtra("ProductName");
            double salePrice = intent.getDoubleExtra("SalePrice", 0.0);
            String productDescription = intent.getStringExtra("ProductDescription");
            double productRate = intent.getDoubleExtra("ProductRate", 0.0);
            byte[] productThumb = intent.getByteArrayExtra("ProductThumb");

            // Hiển thị thông tin sản phẩm
            showProductDetail(productName, salePrice, productDescription, productRate, productThumb);
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
        }

        addControls();
        addEvents();

    }

    private void showProductDetail(String productName, double salePrice, String productDescription, double productRate, byte[] productThumb) {

        txtProductName.setText(productName);
        txtSalePrice.setText(String.format("%.0f đ", salePrice));
        txtProductDescription.setText(productDescription);
        txtProductRate.setText(String.format("%.1f đ", productRate));

        // Hiển thị hình ảnh sản phẩm (nếu có)
        if (productThumb != null && productThumb.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productThumb, 0, productThumb.length);
            imvProductThumb.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            imvProductThumb.setImageResource(R.drawable.dgd_dia2);
        }
    }

    private void addControls() {
        edtDiscuss = findViewById(R.id.edtDiscuss);
        edtCusMail = findViewById(R.id.edtCusMail);
    }


    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });



        // Sự kiện khi click vào nút +
//        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quantity++;
//                txtQuantity.setText(String.valueOf(quantity));
//            }
//        });

        // Sự kiện khi click -
        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });
    }

        //            Truy vấn discuss
        Product product = null;
//        String DiscussContent = db.getDiscussContent(product.getProductID());
//        String RespondContent = db.getRespondContent(product.getProductID());
//
//        txtDiscussContent.setText(DiscussContent);
//        txtRespondContent.setText(RespondContent);
    }







//    private void showProductDetail(Product product) {
//        binding.imvProductThumb.setImageBitmap(product.getProductThumb());
//        binding.txtProductName.setText(product.getProductName());
//        binding.txtSalePrice.setText(String.format("%.0f đ", product.getSalePrice()));
//        binding.txtProductDescription.setText(product.getProductDescription());
//        binding.txtProductRate.setText(String.valueOf(product.getProductRate()));
//
//
//    }

