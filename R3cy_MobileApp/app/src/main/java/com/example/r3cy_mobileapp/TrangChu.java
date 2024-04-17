package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adapter.BannerAdapter;
import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Banners;
import com.example.models.Product;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityTrangChuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrangChu extends AppCompatActivity {

    ActivityTrangChuBinding binding;
    ViewPager2 viewPager;
    ArrayList<Banners> bannerList;
    Timer timer;
    BottomNavigationView navigationView;
    R3cyDB db;
    private List<Product> products;
    Product product;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangChuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Set up viewpager
        bannerList = new ArrayList<>();
        viewPager = findViewById(R.id.vp_HomeBanner);
        int[] images = {R.drawable.banner2,R.drawable.banner1,R.drawable.banner3};
        for (int i =0; i< images.length ; i++){

            Banners banner = new Banners(images[i]);
            bannerList.add(banner);
        }
        BannerAdapter bannerAdapter =(BannerAdapter) new BannerAdapter(bannerList);
        viewPager.setAdapter(bannerAdapter);

        autoSlide();
        addEvents();

//        createDb();
//        loadData();
    }

    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleProduct();
    }

    private void loadData() {

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        binding.rcvProducts.setLayoutManager(layoutManager);
//
//        products = new ArrayList<>();
//        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);
//        while (cursor.moveToNext()) {
//            products.add(new Product(
//                    cursor.getInt(0), //ProductID
//                    cursor.getString(1), //ProductName
//                    cursor.getDouble(2), // ProductPrice
//                    cursor.getString(3), //ProductDescription
//                    cursor.getBlob(4), //ProductThumb
//                    cursor.getInt(5), //Hot
//                    cursor.getString(6), //Category
//                    cursor.getInt(7), //Inventory
//                    cursor.getDouble(8), //ProductRate
//                    cursor.getDouble(9), //SalePrice
//                    cursor.getInt(10), //SoldQuantity
//                    cursor.getString(11), //CreatedDate
//                    cursor.getInt(12), //Status
//                    cursor.getBlob(13), //img1
//                    cursor.getBlob(14), //img2
//                    cursor.getBlob(15) //img3
//            ));
//            Log.d("ProductInfo", "Product ID: " + product.getProductID());
//            Log.d("ProductInfo", "Product Name: " + product.getProductName());
//        }
//        cursor.close();
//        Log.d("ProductInfo", "Number of products retrieved: " + products.size());
//
//
////                String ProductName = cursor.getString(1);
////                String ProductDescription = cursor.getString(3);
////                String Category = cursor.getString(6);
////                Double SalePrice = cursor.getDouble(9);
////                Double ProductRate = cursor.getDouble(8);
//
//
//
//        adapter = new ProductAdapter(this, R.layout.viewholder_category_list, products);
//        binding.rcvProducts.setAdapter(adapter);
    }

    private void autoSlide(){
        if(bannerList == null || bannerList.isEmpty() || viewPager == null){
            return;
        }
        if(timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = bannerList.size() - 1;
                        if(currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else{viewPager.setCurrentItem(0);}
                    }
                });

            }
        }, 1000, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(timer != null){
            timer.cancel();
            timer =null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Sự kiện action bar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search){
            Intent intentSearch = new Intent(TrangChu.this, Product_List.class);
            startActivity(intentSearch);
        } else if (item.getItemId() == R.id.action_cart) {
            Intent intentCart = new Intent(TrangChu.this, CartManage.class);
            startActivity(intentCart);
        } else if (item.getItemId() == R.id.action_noti) {
            Intent intentNoti = new Intent(TrangChu.this, AboutUs.class);
            startActivity(intentNoti);
        }


        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_home);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(),Product_List.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(),AboutUs.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(),AboutUs.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(),UserAccount_Main.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_home) {
                    return true;
                }
                return false;}


        });
    }
}