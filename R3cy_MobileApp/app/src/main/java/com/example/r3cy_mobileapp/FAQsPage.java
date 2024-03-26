package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.r3cy_mobileapp.Fragment.Fags_dathang_thanhtoan_Fragment;
import com.example.r3cy_mobileapp.Fragment.Faqs_chamsoc_Fragment;
import com.example.r3cy_mobileapp.Fragment.Faqs_giaohang_Fragment;
import com.example.r3cy_mobileapp.Fragment.Faqs_sanpham_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_baomat_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_dichvu_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_doitra_Fragment;
import com.example.r3cy_mobileapp.databinding.ActivityFaqsPageBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FAQsPage extends AppCompatActivity {

    ActivityFaqsPageBinding binding;

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;

    TextView txta1;
    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tabLayout = findViewById(R.id.FaqsLayout);
        viewPager2 = findViewById(R.id.pagerfaq);
        adapter = new TabAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chăm sóc");
                        break;
                    case 1:
                        tab.setText("Đặt hàng & Thanh toán");
                        break;
                    case 2:
                        tab.setText("Giao hàng");
                        break;
                    case 3:
                        tab.setText("Sản phẩm");
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
                    return new Faqs_chamsoc_Fragment();
                case 1:
                    return new Fags_dathang_thanhtoan_Fragment();
                case 2:
                    return new Faqs_giaohang_Fragment();
                default:
                    return new Faqs_sanpham_Fragment();

            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}