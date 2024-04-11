package com.example.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.models.Product;
import com.example.r3cy_mobileapp.Product.Dogiadung_Fragment;
import com.example.r3cy_mobileapp.Product.Dotrangtri_Fragment;
import com.example.r3cy_mobileapp.Product.Phukien_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //    private List<Fragment> fragments = new ArrayList<>();
    private List<Product> products;
    private List<Fragment> fragments;
    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<Product> products) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.products = products;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        //Kiểm tra và đặt dữ liệu sản phẩm cho từng fragment
        if (fragment instanceof Dogiadung_Fragment) {
            Bundle bundle = new Bundle();
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if ("Đồ gia dụng".equals(product.getCategory())) {
                    filteredProducts.add(product);
                }
            }
            bundle.putSerializable("products", filteredProducts);
            fragment.setArguments(bundle);
        } else if (fragment instanceof Dotrangtri_Fragment) {
            Bundle bundle = new Bundle();
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if ("Đồ trang trí".equals(product.getCategory())) {
                    filteredProducts.add(product);
                }
            }
            bundle.putSerializable("products", filteredProducts);
            fragment.setArguments(bundle);
        } else if (fragment instanceof Phukien_Fragment) {
            Bundle bundle = new Bundle();
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if ("Phụ kiện".equals(product.getCategory())) {
                    filteredProducts.add(product);
                }
            }
            bundle.putSerializable("products", filteredProducts);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Đồ gia dụng";
                break;
            case 1:
                title = "Đồ trang trí";
                break;
            case 2:
                title = "Phụ kiện";
                break;
        }
        return title;
    }
}

