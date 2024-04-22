package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r3cy_mobileapp.Fragment.Policy_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_baomat_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_dichvu_Fragment;
import com.example.r3cy_mobileapp.Fragment.Policy_doitra_Fragment;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityMainBinding;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountPolicyBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserAccount_Policy extends AppCompatActivity {

    ActivityUserAccountPolicyBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TabAdapter adapter;
    TextView textView1;

    ImageView btnback;
    BottomNavigationView navigationView;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");
        Log.d("Intent", "Email ở policy: " + email);


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

        addEvents();

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

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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