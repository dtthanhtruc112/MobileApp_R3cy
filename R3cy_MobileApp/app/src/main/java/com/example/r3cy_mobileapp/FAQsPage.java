package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityFaqsPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FAQsPage extends AppCompatActivity {

    ActivityFaqsPageBinding binding;

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;

    TextView txta1;
    ImageView imageView1;
    BottomNavigationView navigationView;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email FAQ: " + email);

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

        addEvents();

    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });

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
                    Intent intent5 =new Intent(getApplicationContext(),TrangChu.class);
                    intent5.putExtra("key_email", email);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;}


        });
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