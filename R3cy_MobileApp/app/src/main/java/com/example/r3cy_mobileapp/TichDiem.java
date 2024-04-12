package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.adapter.CouponAdapter;
import com.example.adapter.CouponAdapterRecycler;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityTichDiemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TichDiem extends AppCompatActivity {

    ActivityTichDiemBinding binding;
    CouponAdapterRecycler adapter;
    ArrayList<Coupon> coupons;
    R3cyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTichDiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new R3cyDB(this);
        db.createSampleDataCoupon();
        db.createSampleDataCustomer();
        
        loadData();
        addEvents();
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvCoupon.setLayoutManager(layoutManager);


//        Load data
        coupons = new ArrayList<>();
        Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON);
        // Trong vòng lặp while
        String validDateString = c.getString(6); // Lấy chuỗi ngày tháng từ cột VALID_DATE
        String expireDateString = c.getString(7); // Lấy chuỗi ngày tháng từ cột EXPIRE_DATE

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // Định dạng ngày tháng trong cơ sở dữ liệu
        Date validDate = null;
        Date expireDate = null;

        try {
            validDate = dateFormat.parse(validDateString); // Chuyển đổi chuỗi thành đối tượng ngày tháng
            expireDate = dateFormat.parse(expireDateString); // Chuyển đổi chuỗi thành đối tượng ngày tháng
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (c.moveToNext()) {
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

        adapter = new CouponAdapterRecycler(this, coupons, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí của item được chọn từ tag
                int position = (int) v.getTag();

                // Lấy coupon tại vị trí đó
                Coupon clickedCoupon = coupons.get(position);

                // Tạo một Bundle và đưa thông tin của coupon vào
                Bundle bundle = new Bundle();
                bundle.putSerializable("COUPON", clickedCoupon);

                // Tạo Intent để chuyển đến trang mới và đính kèm Bundle
                Intent intent = new Intent(TichDiem.this, DoiDiem_ChiTiet.class);
                intent.putExtra("Package", bundle);
                startActivity(intent);
            }
        });
        binding.rcvCoupon.setAdapter(adapter);
    }

    private void addEvents() {
        binding.btnTichluy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TichDiem.this, Product_List.class);
                startActivity(intent);
            }
        });

        binding.btnDoidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TichDiem.this, DoiDiem.class);
                startActivity(intent);
            }
        });
    }
}