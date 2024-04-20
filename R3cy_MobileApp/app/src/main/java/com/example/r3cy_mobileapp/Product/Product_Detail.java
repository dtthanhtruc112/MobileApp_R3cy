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
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> photos;
    TextView txtQuantity, txtProductName, txtSalePrice, txtProductDescription, txtProductRate, txtDiscussContent, txtRespondContent;
    ImageView btnIncreaseQuantity;
    ImageView btnDecreaseQuantity, imvProductThumb;
    EditText edtDiscuss, edtCusMail;
    private List<Integer> imageResourceIds;
    private PhotoViewpager2Adapter adapter;
    private int quantity = 1; //Giá trị mặc định số lượng là 1

    private static final String DATABASE_NAME = "r3cy_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";
    private SQLiteDatabase database;
    int ProductID = -1;
    R3cyDB db = new R3cyDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new R3cyDB(this);
        db.createSampleProduct();

        addEvents();

//        mViewPager2 = findViewById(R.id.view_pager_2);
//        mCircleIndicator3 = findViewById(R.id.circle_indicator3);


        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        txtDiscussContent = findViewById(R.id.txtDiscussContent);
        txtRespondContent = findViewById(R.id.txtRespondContent);

//        imageResourceIds = new ArrayList<>();

//        giá trị mặc định của số lượng
        txtQuantity.setText(String.valueOf(quantity));

//        Khai báo và khởi tạo đối tượng DB
//        String discussContent = db.getDiscussContent(ProductID);
//        String respondContent = db.getRespondContent(ProductID);

////        Lấy ID sản phẩm từ Intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("ProductID")) {
//            int ProductID = intent.getIntExtra("ProductID", -1);
//
////             Truy vấn dữ liệu sản phẩm từ cơ sở dữ liệu
//            Product product = db.getProductByID(ProductID);
//
////             Hiển thị thông tin sản phẩm trên giao diện
//            showProductDetail(product);
//        }

        addControls();
        addEvents();
        initUI();

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


        //        sự kiện khi click vào nút +
//        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quantity++;
//                txtQuantity.setText(String.valueOf(quantity));
//            }
//        });
////        sự kiện khi click -
//        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(quantity > 1) {
//                    quantity--;
//                    txtQuantity.setText(String.valueOf(quantity));
//                }
//            }
//        });






    }

    private void initUI() {
        imvProductThumb = findViewById(R.id.imvProductThumb);
        txtProductName = findViewById(R.id.txtProductName);
        txtSalePrice = findViewById(R.id.txtSalePrice);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductRate = findViewById(R.id.txtProductRate);
        txtDiscussContent = findViewById(R.id.txtDiscussContent);
        txtRespondContent = findViewById(R.id.txtRespondContent);


        //        Truy xuất thông tin sản phẩm từ intent
        Intent intent = getIntent();
        int ProductID = intent.getIntExtra("ProductID", -1);
        R3cyDB db = new R3cyDB(this);

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM PRODUCT WHERE PRODUCTID = ?", new String[] {ProductID + ""});

            if(cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                String ProductName = cursor.getString(1);
                double ProductPrice = cursor.getDouble(2);
                String ProductDescription = cursor.getString(3);
                byte[] ProductThumb = cursor.getBlob(4);
                int Hot = cursor.getInt(5);
                String Category = cursor.getString(6);
                int Inventory = cursor.getInt(7);
                double ProductRate = cursor.getDouble(8);
                double SalePrice = cursor.getDouble(9);
                int SoldQuantity = cursor.getInt(10);
                String CreatedDate = cursor.getString(11);
                int Status = cursor.getInt(12);
                byte[] Img1 = cursor.getBlob(13);
                byte[] Img2 = cursor.getBlob(14);
                byte[] Img3 = cursor.getBlob(15);

            }else {
                Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Log.e("Product_Detail", "Lỗi truy vấn database: " + e.getMessage());
            Toast.makeText(this, "Lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        } finally {
            database.close(); // Đóng database
        }


        //            Truy vấn discuss
        Product product = null;
//        String DiscussContent = db.getDiscussContent(product.getProductID());
//        String RespondContent = db.getRespondContent(product.getProductID());
//
//        txtDiscussContent.setText(DiscussContent);
//        txtRespondContent.setText(RespondContent);
    }







    private void showProductDetail(Product product) {
//        binding.imvThumb.setImageBitmap(product.getProductThumb());
        binding.txtProductName.setText(product.getProductName());
        binding.txtSalePrice.setText(String.format("%.0f đ", product.getSalePrice()));
        binding.txtProductDescription.setText(product.getProductDescription());
        binding.txtProductRate.setText(String.valueOf(product.getProductRate()));


    }






}