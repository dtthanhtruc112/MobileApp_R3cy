package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r3cy_mobileapp.Fragment.OrderManage_cholayhang_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_choxuly_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_danggiao_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_hoanthanh_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_tatca_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_baomat_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_dichvu_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_doitra_Fragment;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountManageOrderBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class User_account_manageOrder extends AppCompatActivity {
    ActivityUserAccountManageOrderBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;
    TextView textView1;

    ImageView btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountManageOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tabLayout = findViewById(R.id.tabLayout_manageOrder);
        viewPager2 = findViewById(R.id.pager_manageOrder);
        adapter = new TabAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Tất cả đơn hàng");
                        break;
                    case 1:
                        tab.setText("Chờ xử lý");
                        break;
                    case 2:
                        tab.setText("Chờ lấy hàng");
                        break;
                    case 3:
                        tab.setText("Đang giao");
                        break;
                    case 4:
                        tab.setText("Hoàn thành");
                        break;
                }
            }
        }).attach();
    }
    private class TabAdapter extends FragmentStateAdapter {

        public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new OrderManage_tatca_Fragment();
                case 1:
                    return new OrderManage_choxuly_Fragment();
                case 2:
                    return new OrderManage_cholayhang_Fragment();
                case 3:
                    return new OrderManage_danggiao_Fragment();
                default:
                    return new OrderManage_hoanthanh_Fragment();

            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

}