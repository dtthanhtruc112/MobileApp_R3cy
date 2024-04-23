package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.BannerAdapter;
import com.example.adapter.ProductAdapter;
import com.example.adapter.PromotionBannerAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Banners;
import com.example.models.Product;
import com.example.models.ProductAtb;
import com.example.models.PromotionBanner;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.Product.Product_Search;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityTrangChuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
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
    private List<PromotionBanner> promotionBanners;
    Product product;
    ProductAdapter adapter;
    PromotionBannerAdapter adapterpromotion;
    String email;
    ArrayList<ProductAtb> productAtbs;

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

        email = getIntent().getStringExtra("key_email");

//        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
//        email = preferences.getString("string", "");

        Log.d("SharedPreferences", "Email: " + email);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        autoSlide();
        addEvents();

        createDb();
        loadData();
        loadpromotionbanner();
    }


    private void createDb() {

        db = new R3cyDB(this);
        db.createSampleProduct();
        db.createSampleDataCustomer();
    }

    private void loadData() {
        LinearLayoutManager layoutManagerProducts = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvProducts.setLayoutManager(layoutManagerProducts);

        LinearLayoutManager layoutManagerProduct = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvProduct.setLayoutManager(layoutManagerProduct);



        products = new ArrayList<>();
        productAtbs = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);

        try {
            while (cursor.moveToNext()) {
                try {
                    products.add(new Product(
                            cursor.getInt(0), // ProductID
                            cursor.getBlob(4), // ProductThumb
                            cursor.getString(1), // ProductName
                            cursor.getDouble(9), // SalePrice

                            cursor.getString(6), // Category
                            cursor.getString(3),
                            cursor.getDouble(8) // ProductRate

                    ));

                    productAtbs.add(new ProductAtb(
                            cursor.getInt(0), // ProductID
                            cursor.getDouble(2), // ProductPrice
                            cursor.getInt(5), // Hot
                            cursor.getInt(7), // Inventory
                            cursor.getInt(10), // SoldQuantity
                            cursor.getString(11), // CreatedDate
                            cursor.getInt(12) // Status
                    ));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            cursor.close(); // Close the cursor to avoid memory leaks
        }

        List<Product> filteredProducts = filterProductsByHot(1);
        List<Product> filteredProducts1 = filterProductsByHot(0);

        Log.d("ProductInfo", "Number of products retrieved: " + products.size());

        adapter = new ProductAdapter(this, R.layout.viewholder_category_list,filteredProducts, email);
        Log.d("SharedPreferences", "Email load data: " + email);

        binding.rcvProducts.setAdapter(adapter);

        adapter = new ProductAdapter(this, R.layout.viewholder_category_list,filteredProducts1, email);
        Log.d("SharedPreferences", "Email load data: " + email);
        binding.rcvProduct.setAdapter(adapter);
//        binding.rcvProduct.setAdapter(adapter);
    }

    private void loadpromotionbanner() {
        LinearLayoutManager layoutManagerProducts = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvpromotionbanners.setLayoutManager(layoutManagerProducts);

        promotionBanners = new ArrayList<>();
        promotionBanners.add(new PromotionBanner(R.drawable.ctkm3, "Tri ân khách hàng"));
        promotionBanners.add(new PromotionBanner(R.drawable.ctkm1, "Ngày lễ Black Friday"));

        adapterpromotion = new PromotionBannerAdapter(this, promotionBanners);
        binding.rcvpromotionbanners.setAdapter(adapterpromotion);

    }

    private List<Product> filterProductsByHot(int hotValue) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            int productId = product.getProductID();
            ProductAtb productAtb = getProductAtbById(productId);

            if (productAtb != null && productAtb.getHot() == hotValue) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private ProductAtb getProductAtbById(int productId) {
        for (ProductAtb productAtb : productAtbs) {
            if (productAtb.getProductID() == productId) {
                return productAtb;
            }
        }
        return null;
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
    protected void onResume() {
        super.onResume();
        if (email != null) {
            // Đã đăng nhập
            Toast.makeText(TrangChu.this, "Đã đăng nhập", Toast.LENGTH_SHORT).show();
            // Thiết lập văn bản cho nút đăng nhập
            binding.btnDangnhap.setText("Đã đăng nhập");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(timer != null){
            timer.cancel();
            timer =null;
        }



        // Xóa dữ liệu email từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("string"); // Xóa dữ liệu email
        editor.apply();

        Log.d("SharedPreferences", "Email ở Ondestroy: " + email);




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
            Intent intentSearch = new Intent(TrangChu.this, Product_Search.class);
            intentSearch.putExtra("key_email", email);
            startActivity(intentSearch);
        } else if (item.getItemId() == R.id.action_cart) {
            Intent intentCart = new Intent(TrangChu.this, CartManage.class);
            intentCart.putExtra("key_email", email);
            startActivity(intentCart);
        } else if (item.getItemId() == R.id.action_noti) {
            Intent intentNoti = new Intent(TrangChu.this, Notification.class);
            intentNoti.putExtra("key_email", email);
            startActivity(intentNoti);
        }


        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email != null) {
                    // Đã đăng nhập
                    Toast.makeText(TrangChu.this, "Đã đăng nhập", Toast.LENGTH_SHORT).show();
                    // Thiết lập văn bản cho nút đăng nhập
                    binding.btnDangnhap.setText("Đã đăng nhập");
                    // Thực hiện hành động sau khi đăng nhập thành công
                    // Ví dụ: điều hướng đến màn hình hoặc hành động mong muốn ở đây
                } else {
                    // Chưa đăng nhập
                    Intent intent = new Intent(TrangChu.this, Signin_Main.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        binding.btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, CustomProduct.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnThugom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TrangChu.this, Thugom.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, FAQsPage.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnTichdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, TichDiem.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnTracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email == null || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TrangChu.this);
                    builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập truy cập vào trang tài khoản");
                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                            startActivity(new Intent(TrangChu.this, Signin_Main.class));
                        }
                    });
                    builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            // Chuyển về trang chủ khi nhấn cancel
//                    dialog.dismiss();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                            Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                            // Set text color for "Ok" button
                            positiveButton.setTextColor(ContextCompat.getColor(TrangChu.this, R.color.blue));

                            // Set text color for "Cancel" button
                            negativeButton.setTextColor(ContextCompat.getColor(TrangChu.this, R.color.blue));
                        }
                    });

                    dialog.show(); } else {
                    Intent intent = new Intent(TrangChu.this, User_account_manageOrder.class);
                    intent.putExtra("key_email", email);
                    startActivity(intent);
                }

            }
        });

        binding.btnopenProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, Product_List.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnopenProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, Product_List.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });


        binding.btnopenProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, Product_List.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });




        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_home);


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
                    Intent intent2 =new Intent(getApplicationContext(), BlogList.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(),AboutUs.class);
                    intent3.putExtra("key_email", email);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(),UserAccount_Main.class);
                    intent4.putExtra("key_email", email);
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