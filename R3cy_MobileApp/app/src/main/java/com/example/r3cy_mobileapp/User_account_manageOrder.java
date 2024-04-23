package com.example.r3cy_mobileapp;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.Fragment.OrderManage_cholayhang_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_choxuly_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_danggiao_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_hoanthanh_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_tatca_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_baomat_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_dichvu_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_doitra_Fragment;
import com.example.r3cy_mobileapp.Product.Dogiadung_Fragment;
import com.example.r3cy_mobileapp.Product.Dotrangtri_Fragment;
import com.example.r3cy_mobileapp.Product.Phukien_Fragment;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountManageOrderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class User_account_manageOrder extends AppCompatActivity {
    ActivityUserAccountManageOrderBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;
    TextView textView1;

    ImageView btnback;
    R3cyDB db;
    BottomNavigationView navigationView;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountManageOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở manageorder: " + email);

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
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        createDB();

        addEvents();

    }
    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderManage_tatca_Fragment().newInstance(email)); // Khởi tạo fragment và truyền email
        fragments.add(new OrderManage_hoanthanh_Fragment().newInstance(email)); // Khởi tạo fragment và truyền email
        fragments.add(new OrderManage_danggiao_Fragment().newInstance(email)); // Khởi tạo fragment và truyền email
        fragments.add(new OrderManage_cholayhang_Fragment().newInstance(email)); // Khởi tạo fragment và truyền email
        fragments.add(new OrderManage_choxuly_Fragment().newInstance(email)); // Khởi tạo fragment và truyền email
        return fragments;
    }

    private void addEvents() {
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_account);


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
                    Intent intent2 =new Intent(getApplicationContext(),BlogDetail.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
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
                    Intent intent5 =new Intent(getApplicationContext(),TrangChu.class);
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


    private void createDB() {

            db = new R3cyDB(this);
            db.createSampleDataOrder();

    }
    public void showFragment(int position) {
        viewPager2.setCurrentItem(position);
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
                    return new OrderManage_tatca_Fragment().newInstance(email);
                case 1:
                    return new OrderManage_choxuly_Fragment().newInstance(email);
                case 2:
                    return new OrderManage_cholayhang_Fragment().newInstance(email);
                case 3:
                    return new OrderManage_danggiao_Fragment().newInstance(email);
                default:
                    return new OrderManage_hoanthanh_Fragment().newInstance(email);

            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

}