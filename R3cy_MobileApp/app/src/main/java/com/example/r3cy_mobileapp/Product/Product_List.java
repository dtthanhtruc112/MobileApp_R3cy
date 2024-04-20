package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

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

    private List<Product> getProducts() {
        return products;
    }

    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleProduct1();
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

