package com.example.r3cy_mobileapp.Product;

import static com.example.databases.R3cyDB.CATEGORY;
import static com.example.databases.R3cyDB.CREATED_DATE;
import static com.example.databases.R3cyDB.HOT;
import static com.example.databases.R3cyDB.INVENTORY;
import static com.example.databases.R3cyDB.PRODUCT_DESCRIPTION;
import static com.example.databases.R3cyDB.PRODUCT_ID;
import static com.example.databases.R3cyDB.PRODUCT_IMG1;
import static com.example.databases.R3cyDB.PRODUCT_IMG2;
import static com.example.databases.R3cyDB.PRODUCT_IMG3;
import static com.example.databases.R3cyDB.PRODUCT_NAME;
import static com.example.databases.R3cyDB.PRODUCT_PRICE;
import static com.example.databases.R3cyDB.PRODUCT_RATE;
import static com.example.databases.R3cyDB.PRODUCT_THUMB;
import static com.example.databases.R3cyDB.SALE_PRICE;
import static com.example.databases.R3cyDB.SOLD_QUANTITY;
import static com.example.databases.R3cyDB.STATUS;
import static com.example.databases.R3cyDB.TBl_PRODUCT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.adapter.ProductAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.databases.R3cyDB;
import com.example.interfaces.ProductInterface;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductListBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Product_List extends AppCompatActivity implements ProductInterface {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Product> products;
//    private List<Product> products;
    ActivityProductListBinding binding;
    R3cyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


// khởi tạo ViewPager và TabLayout
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

//        Khởi tạo cơ sở dữ liệu và tải dữ liệu sản phẩm từ cơ sở dữ liệu
        createDb();

//        Khởi tạo Adapter với danh sách fragments và products
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments(), getProducts());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText("Đồ gia dụng");
        mTabLayout.getTabAt(1).setText("Đồ trang trí");
        mTabLayout.getTabAt(2).setText("Phụ kiện");



    }
        private void createDb() {
        db = new R3cyDB(this);
        db.createSampleProduct1();
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + TBl_PRODUCT);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int ProductID = cursor.getInt(cursor.getColumnIndex(PRODUCT_ID));
                @SuppressLint("Range") String ProductName = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
                @SuppressLint("Range") double SalePrice = cursor.getDouble(cursor.getColumnIndex(SALE_PRICE));
                @SuppressLint("Range") String ProductDescription = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIPTION));
                @SuppressLint("Range") byte[] ProductThumb = cursor.getBlob(cursor.getColumnIndex(PRODUCT_THUMB));
                @SuppressLint("Range") String Category = cursor.getString(cursor.getColumnIndex(CATEGORY));
                @SuppressLint("Range") int Inventory = cursor.getInt(cursor.getColumnIndex(INVENTORY));
                @SuppressLint("Range") double ProductPrice = cursor.getDouble(cursor.getColumnIndex(PRODUCT_PRICE));
                @SuppressLint("Range") double ProductRate = cursor.getDouble(cursor.getColumnIndex(PRODUCT_RATE));
                @SuppressLint("Range") int SoldQuantity = cursor.getInt(cursor.getColumnIndex(SOLD_QUANTITY));
                @SuppressLint("Range") String CreatedDate = cursor.getString(cursor.getColumnIndex(CREATED_DATE));
                @SuppressLint("Range") int Status = cursor.getInt(cursor.getColumnIndex(STATUS));
                @SuppressLint("Range") byte[] Img1 = cursor.getBlob(cursor.getColumnIndex(PRODUCT_IMG1));
                @SuppressLint("Range") byte[] Img2 = cursor.getBlob(cursor.getColumnIndex(PRODUCT_IMG2));
                @SuppressLint("Range") byte[] Img3 = cursor.getBlob(cursor.getColumnIndex(PRODUCT_IMG3));
                @SuppressLint("Range") int Hot = cursor.getInt(cursor.getColumnIndex(HOT));

                // Tạo một đối tượng Product từ dữ liệu
                Product product = new Product(ProductID, ProductName,  ProductPrice,  ProductDescription,  ProductThumb,  Hot,  Category,  Inventory,  ProductRate,  SalePrice,  SoldQuantity,  CreatedDate,  Status,  Img1,  Img2,  Img3);

                // Thêm sản phẩm vào danh sách
                products.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return products;
    }



    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Dogiadung_Fragment());
        fragments.add(new Dotrangtri_Fragment());
        fragments.add(new Phukien_Fragment());
        return fragments;
    }

    public void replaceFragment(Product p) {
//        chuyển sang product_detail và truyền dữ liệu sản phẩm
        Intent intent = new Intent(Product_List.this, Product_Detail.class);
        intent.putExtra("productID", p.getProductID());
        startActivity(intent);
    }




}

