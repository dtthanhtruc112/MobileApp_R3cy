package com.example.r3cy_mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Customer;
import com.example.models.Voucher;
import com.example.r3cy_mobileapp.databinding.ActivityDoiDiemChiTietBinding;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountVoucherDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class User_account_voucher_detail extends AppCompatActivity {

    ActivityUserAccountVoucherDetailBinding binding;
    Coupon coupon;
    Voucher voucher;
    R3cyDB db;
    Customer customer;
    String email;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountVoucherDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        db = new R3cyDB(this);
//        db.createSampleDataCustomer();

//        email = getIntent().getStringExtra("key_email");
        Intent intent =  getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null) {
            email = bundle.getString("key_email");
            Log.d("SharedPreferences", "Email ở đổi điểm chi tiết: " + email);
            if (email != null) {
                Log.d("SharedPreferences", "Email ở đổi điểm chi tiết: " + email);
                // Tiếp tục xử lý với email đã lấy được
            } else {
                // Nếu email là null, hiển thị thông báo yêu cầu đăng nhập trước
//                Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập trước", Toast.LENGTH_SHORT).show();
                // Tiếp tục xử lý theo nhu cầu của bạn, có thể chuyển người dùng đến màn hình đăng nhập
            }
        } else {
            // Nếu bundle là null, cũng hiển thị thông báo yêu cầu đăng nhập trước
//            Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập trước", Toast.LENGTH_SHORT).show();
            // Tiếp tục xử lý theo nhu cầu của bạn, có thể chuyển người dùng đến màn hình đăng nhập
        }


        addEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataCustomer();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null && bundle.containsKey("COUPON")) {
            voucher = (Voucher) bundle.getSerializable("COUPON");
            if (voucher != null) {
                binding.txtCode.setText(voucher.getCOUPON_CODE());
                binding.txtTitle.setText(voucher.getCOUPON_TITLE());
//                binding.txtScore.setText(String.valueOf(coupon.getSCORE_MIN()));

                // Lấy ngày hiện tại
                Date currentDate = new Date();



                // Lấy ngày hết hạn của coupon
                Date expireDate = voucher.getEXPIRE_DATE();

                Log.d("CouponDetails", "Expire Date: " + expireDate); // Log thông tin của ngày hết hạn

                if (expireDate != null) {
                    // Tính số ngày còn lại giữa ngày hết hạn và ngày hiện tại
                    long diffInMillies = expireDate.getTime() - currentDate.getTime();
                    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

                    Log.d("CouponDetails", "Days Left: " + diffInDays); // Log số ngày còn lại

                    // Hiển thị số ngày còn lại trên giao diện của bạn
                    binding.txtDayLeft.setText(String.valueOf(diffInDays));
                    binding.txtDayLeft1.setText(String.valueOf(diffInDays));
                }
            }
        }
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });
    }

        private void reloadData() {
            // Load lại dữ liệu coupon
            loadDataCoupon();

            // Load lại dữ liệu customer
            getDataCustomer();
        }

        private boolean isCustomerEligibleForCoupon(int customerId, int couponId) {
            Cursor cursor = db.getData("SELECT " + R3cyDB.CUSTOMER_IDS + " FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.COUPON_ID + " = " + couponId);
            if (cursor != null && cursor.moveToFirst()) {
                String customerIdsString = cursor.getString(0);
                cursor.close();
                if (customerIdsString != null && !customerIdsString.isEmpty()) {
                    ArrayList<Integer> customerIds = db.parseCustomerIdsFromString(customerIdsString);
                    return customerIds.contains(customerId);
                }
            }
            return false;
        }






        private void getDataCustomer() {


            // Nếu không có email từ SharedPreferences, không thực hiện gì cả
            if (email == null) {
                return;
            }

            // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
            customer = db.getCustomerByEmail1(email);
        }

        private void loadDataCoupon() {
            ArrayList<Voucher> vouchers = new ArrayList<>();
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

                    vouchers.add(new Voucher(
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
        }


}