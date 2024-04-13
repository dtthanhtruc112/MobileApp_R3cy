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
        return fragments.get(position);

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

