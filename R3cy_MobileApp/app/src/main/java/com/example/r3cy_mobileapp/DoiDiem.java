package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapter.CouponAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.databinding.ActivityDoiDiemBinding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoiDiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }
}