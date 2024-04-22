package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.adapter.CouponAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountVoucherBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class User_account_voucher extends AppCompatActivity {
    ActivityUserAccountVoucherBinding binding;
    Coupon coupon;
    R3cyDB db;
    CouponAdapter adapter;
    Customer customer;
    ArrayList<Coupon> coupons;
    String email;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở voucher acc: " + email);
//        }
//        createDb();
//        getDataCustomer();
        loadData();
        addEvents();

    }
    private void getDataCustomer() {
        db = new R3cyDB(this);
        int customerId = customer.getCustomerId();
        Cursor c1 = db.getData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER + " WHERE " + R3cyDB.CUSTOMER_IDS + " LIKE '%" + customerId + "%'");
        if (c1.moveToFirst()) {
            customer = new Customer(c1.getInt(0),
                    c1.getString(1),
                    c1.getString(2),
                    c1.getInt(3),
                    c1.getString(4),
                    c1.getString(5),
                    c1.getString(6),
                    c1.getInt(7),
                    c1.getString(8),
                    c1.getBlob(9),
                    c1.getString(10)
            );
        }
        c1.close();
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        Log.i("test", "onResume");
//
//            loadData();
//        }




    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataCoupon();
    }

private boolean isCustomerEligibleForCoupon(int customerId) {
    Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.CUSTOMER_IDS + " LIKE '%" + customerId + "%'");
    if (cursor != null && cursor.moveToFirst()) {
        String customerIdsString = cursor.getString(0);
        cursor.close();
        return true; // Nếu có kết quả từ truy vấn, tức là khách hàng đủ điều kiện
    }
    return false;
}
//    private int getCustomerIdFromCoupon(int couponId) {
//        int customerId = -1; // Giá trị mặc định nếu không tìm thấy customerId
//        Cursor cursor = db.getData("SELECT " + R3cyDB.CUSTOMER_ID + " FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.COUPON_ID + " = " + couponId);
//        if (cursor != null && cursor.moveToFirst()) {
//            @SuppressLint("Range") int customerid = cursor.getString(cursor.getColumnIndex(R3cyDB.CUSTOMER_ID));
//            cursor.close();
//        }
//        return customerId;
//    }



    private void loadData() {
        if (customer != null) {
             // Lấy email từ đăng nhập hiện tại
            db = new R3cyDB(this);
            int customerId = db.getCustomerIdFromCustomer(email);
        if (customerId != -1) {
//            boolean isCustomerEligible = isCustomerEligibleForCoupon(customerId);
//            if (isCustomerEligible) {
                ArrayList<Coupon> coupons = new ArrayList<>();
                Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.CUSTOMER_IDS + " LIKE '%" + customerId + "%'");

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

                adapter = new CouponAdapter(this, R.layout.item_voucher, coupons);
                binding.lvVoucher.setAdapter(adapter);
            } else {
                Toast.makeText(User_account_voucher.this, "Không tìm thấy phiếu giảm giá cho khách hàng này", Toast.LENGTH_SHORT).show();
            Log.e("Error", "Customer object is null");

        }
        } else {
            Toast.makeText(User_account_voucher.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }


//    }


    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });

        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_home);


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
    }

}
