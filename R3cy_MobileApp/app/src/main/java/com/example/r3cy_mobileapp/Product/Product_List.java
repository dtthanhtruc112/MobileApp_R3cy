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
    private FrameLayout fragment;
    private List<Product> products;
    ActivityProductListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo danh sách products ở đây
        products = new ArrayList<>();

// khởi tạo ViewPager và TabLayout
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

//        Khởi tạo Adapter với danh sách fragments và products
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments(), getProducts());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText("Đồ gia dụng");
        mTabLayout.getTabAt(1).setText("Đồ trang trí");
        mTabLayout.getTabAt(2).setText("Phụ kiện");
    }

    // Thêm phương thức getProducts() để trả về danh sách sản phẩm
    private List<Product> getProducts() {
        return products;
    }
    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Dogiadung_Fragment());
        fragments.add(new Dotrangtri_Fragment());
        fragments.add(new Phukien_Fragment());
        return fragments;
    }

    //    @Override
    public void replaceFragment(Product p) {
//        chuyển sang product_detail và truyền dữ liệu sản phẩm
//        Intent intent = new Intent(Product_List.this, Product_Detail.class);
//        intent.putExtra("product", p);
//        startActivity(intent);
    }


}

