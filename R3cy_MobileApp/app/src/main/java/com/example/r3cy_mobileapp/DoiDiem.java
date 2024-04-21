package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapter.CouponAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityDoiDiemBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DoiDiem extends AppCompatActivity {

    ActivityDoiDiemBinding binding;
    CouponAdapter adapter;
    ArrayList<Coupon> coupons;
    R3cyDB db;
    String email;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoiDiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        createDb();

        addEvents();



    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataCoupon();
    }

    private void loadData() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON);

        while (c.moveToNext()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date validDate = dateFormat.parse(c.getString(6));
                Date expireDate = dateFormat.parse(c.getString(7));

                ArrayList<Integer> customerIds = new ArrayList<>();
                String customerIdsString = c.getString(12);
                if (customerIdsString != null && !customerIdsString.isEmpty()) {
                    String[] ids = customerIdsString.replaceAll("\\[|\\]", "").split(",\\s*");
                    for (String id : ids) {
                        customerIds.add(Integer.parseInt(id.trim()));
                    }
                }

                coupons.add(new Coupon(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3),
                        c.getString(4),
                        c.getString(5),
                        validDate,
                        expireDate,
                        c.getDouble(8),
                        c.getDouble(9),
                        c.getDouble(10),
                        c.getInt(11),
                        customerIds
                ));
            } catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        c.close();


        adapter = new CouponAdapter(this, R.layout.item_doidiem, coupons);
        binding.lvCoupon.setAdapter(adapter);

    }


    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });

        binding.lvCoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy coupon tại vị trí đó
                Coupon clickedCoupon = coupons.get(position);

                // Tạo một Bundle và đưa thông tin của coupon vào
                Bundle bundle = new Bundle();
                bundle.putSerializable("COUPON", clickedCoupon);
                bundle.putString("key_email", email);

                // Tạo Intent để chuyển đến trang mới và đính kèm Bundle
                Intent intent = new Intent(DoiDiem.this, DoiDiem_ChiTiet.class);
                intent.putExtra("Package", bundle);
                startActivity(intent);
            }
        });

        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_home);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(), Product_List.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(),BlogDetail.class);
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
}