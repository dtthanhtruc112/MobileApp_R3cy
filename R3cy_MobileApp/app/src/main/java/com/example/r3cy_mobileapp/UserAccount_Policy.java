package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r3cy_mobileapp.Fragment.Policy_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_baomat_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_dichvu_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_doitra_Fragment;
import com.example.r3cy_mobileapp.databinding.ActivityMainBinding;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountPolicyBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserAccount_Policy extends AppCompatActivity {

    ActivityUserAccountPolicyBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;
    TextView textView1;

    ImageView btnback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        TextView textView1 = findViewById(R.id.txtpolicybanhang_phivanchuyen);
//        String list1 = "• Item 1\n• Item 2\n• Item 3";
//        setBulletList(textView1, list1);
//
//        TextView textView2 = findViewById(R.id.txtpolicybanhang_tggiaohang);
//        String list2 = "• Item A\n• Item B\n• Item C";
//        setBulletList(textView2, list2);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.pager);
        adapter = new TabAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chính sách bán hàng");
                        break;
                    case 1:
                        tab.setText("Chính sách bảo mật");
                        break;
                    case 2:
                        tab.setText("Điều khoản dịch vụ");
                        break;
                    case 3:
                        tab.setText("Điều khoản đổi trả");
                        break;
                }
            }
        }).attach();



    }

//    private void setBulletList(TextView textView, String s) {
//
//            SpannableString items = new SpannableString(s);
//
//            items.setSpan(new BulletSpan(16, Color.BLACK), 0, items.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            textView.setText(items);
//        }


    private class TabAdapter extends FragmentStateAdapter {


        public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new Policy_Fragment();
                case 1:
                    return new Policy_baomat_Fragment();
                case 2:
                    return new Policy_dichvu_Fragment();
                default:
                    return new Policy_doitra_Fragment();

            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

}