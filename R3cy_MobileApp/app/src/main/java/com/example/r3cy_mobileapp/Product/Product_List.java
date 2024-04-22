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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.adapter.ProductAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.databases.R3cyDB;
import com.example.interfaces.ProductInterface;
import com.example.models.Product;
import com.example.r3cy_mobileapp.AboutUs;
import com.example.r3cy_mobileapp.BlogDetail;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.UserAccount_Main;
import com.example.r3cy_mobileapp.databinding.ActivityProductListBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    ActivityProductListBinding binding;
    R3cyDB dbHelper;
    String email;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở productlist: " + email);


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

        addEvents();



    }

    private void createDb() {
        dbHelper = new R3cyDB(this);
        dbHelper.createSampleProduct();
    }

    private List<Product> getProducts() {
        products = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + R3cyDB.TBl_PRODUCT;
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexSalePrice = cursor.getColumnIndex(R3cyDB.SALE_PRICE);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductDescription = cursor.getColumnIndex(R3cyDB.PRODUCT_DESCRIPTION);
                    int columnIndexProductRate = cursor.getColumnIndex(R3cyDB.PRODUCT_RATE);


                    int productID = cursor.getInt(columnIndexProductId);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);
                    String productName = cursor.getString(columnIndexProductName);
                    double salePrice = cursor.getDouble(columnIndexSalePrice);
                    String productCategory = cursor.getString(columnIndexCategory);
                    String productDescription = cursor.getString(columnIndexProductDescription);
                    double productRate = cursor.getDouble(columnIndexProductRate);


                    Product product = new Product(productID, productThumb, productName, salePrice, productCategory, productDescription, productRate);
                    products.add(product);
                } while (cursor.moveToNext());
            } else {
                Log.d("R3cyDB", "No products found in database");
            }
        } catch (SQLiteException e) {
            Log.e("R3cyDB", "Error getting all products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
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
        intent.putExtra("key_email", email);
        startActivity(intent);
    }

    private void addEvents() {
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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




}

