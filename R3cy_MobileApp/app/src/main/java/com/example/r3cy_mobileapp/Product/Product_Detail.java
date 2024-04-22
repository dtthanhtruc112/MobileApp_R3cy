package com.example.r3cy_mobileapp.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
//import com.example.models.Discuss;
import com.example.models.Product;
import com.example.r3cy_mobileapp.AboutUs;
import com.example.r3cy_mobileapp.BlogDetail;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.UserAccount_Main;
import com.example.r3cy_mobileapp.databinding.ActivityProductDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Product_Detail extends AppCompatActivity {
    ActivityProductDetailBinding binding;

    TextView txtQuantity, txtProductName,txtDiscuss, txtSalePrice, txtProductDescription, txtProductRate, txtDiscussContent, txtRespondContent;
    ImageView btnIncreaseQuantity;
    ImageView btnDecreaseQuantity, imvProductThumb;
    EditText edtDiscuss, edtCusMail;
    private ProductAdapter adapter;
    private List<Product> products;
    Product product;
    String email;
    BottomNavigationView navigationView;
    private int quantity = 1; //Giá trị mặc định số lượng là 1

    private SQLiteDatabase database;
    int ProductID = -1;
    R3cyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở product detail: " + email);


        txtProductName = findViewById(R.id.txtProductName);
        txtSalePrice = findViewById(R.id.txtSalePrice);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductRate = findViewById(R.id.txtProductRate);
        imvProductThumb = findViewById(R.id.imvProductThumb);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        txtDiscussContent = findViewById(R.id.txtDiscussContent);
        txtRespondContent = findViewById(R.id.txtRespondContent);
        edtDiscuss = findViewById(R.id.edtDiscuss);
        edtCusMail = findViewById(R.id.edtCusMail);
        txtDiscuss = findViewById(R.id.txtDiscuss);


        txtQuantity.setText(String.valueOf(quantity));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ProductID")) {
            int productID = intent.getIntExtra("ProductID", -1);
            if (productID != -1) {
                db = new R3cyDB(this);
                Product product = db.getProductByID(productID);

                if (product != null) {
                    showProductDetail(product);
                } else {
                    Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
        }

        addEvents();
    }

    private void showProductDetail(Product product) {
        txtProductName.setText(product.getProductName());
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        txtSalePrice.setText(numberFormat.format(product.getSalePrice()));
        txtProductDescription.setText(product.getProductDescription());
        txtProductRate.setText(String.format("%.1f đ", product.getProductRate()));

        byte[] productThumb = product.getProductThumb();
        if (productThumb != null && productThumb.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productThumb, 0, productThumb.length);
            imvProductThumb.setImageBitmap(bitmap);
        } else {
            imvProductThumb.setImageResource(R.drawable.dgd_dia2);
        }
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        Button btnGui = findViewById(R.id.btnGui);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy nội dung từ EditText
                String userInput = edtDiscuss.getText().toString();
                // Hiển thị nội dung trong TextView
                txtDiscuss.setText(userInput);
            }
        });

        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_product);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(),Product_List.class);
                    intent1.putExtra("key_email", email);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(), BlogDetail.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(), AboutUs.class);
                    intent3.putExtra("key_email", email);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(), UserAccount_Main.class);
                    intent4.putExtra("key_email", email);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_home) {
                    Intent intent5 =new Intent(getApplicationContext(), TrangChu.class);
                    intent5.putExtra("key_email", email);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;}


        });
    }
}