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
        coupons = new ArrayList<>();
        Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON);

        while (c.moveToNext()) {
            // Trong vòng lặp while
            String validDateString = c.getString(6); // Lấy chuỗi ngày tháng từ cột VALID_DATE
            String expireDateString = c.getString(7); // Lấy chuỗi ngày tháng từ cột EXPIRE_DATE

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // Định dạng ngày tháng trong cơ sở dữ liệu
            Date validDate = null;
            Date expireDate = null;

            Log.d("CouponDates", "Valid Date: " + validDate);
            Log.d("CouponDates", "Expire Date: " + expireDate);

            try {
                validDate = dateFormat.parse(validDateString); // Chuyển đổi chuỗi thành đối tượng ngày tháng
                expireDate = dateFormat.parse(expireDateString); // Chuyển đổi chuỗi thành đối tượng ngày tháng
            } catch (ParseException e) {
                e.printStackTrace();
            }

            coupons.add(new Coupon(
                    c.getInt(0),  // COUPON_ID
                    c.getString(1),  // COUPON_CODE
                    c.getString(2),  // COUPON_TITLE
                    c.getInt(3),  // SCORE_MIN
                    c.getString(4),  // COUPON_TYPE
                    c.getString(5),  // COUPON_CATEGORY
                    validDate,  // VALID_DATE
                    expireDate,  // EXPIRE_DATE
                    c.getDouble(8),  // MIN_ORDER_VALUE
                    c.getDouble(9),  // MAXIMUM_DISCOUNT
                    c.getDouble(10),  // COUPON_VALUE
                    c.getInt(11)  // MAXIMUM_USERS
            ));
        }
        c.close();


        adapter = new CouponAdapter(this, R.layout.item_doidiem, coupons);
        binding.lvCoupon.setAdapter(adapter);

    }


}