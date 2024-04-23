package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.adapter.CouponAdapter;
import com.example.adapter.CouponAdapterRecycler;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityTichDiemBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TichDiem extends AppCompatActivity {

    ActivityTichDiemBinding binding;
    CouponAdapterRecycler adapter;
    ArrayList<Coupon> coupons;
    Customer customer;
    R3cyDB db;
    private PieChart pieChart;
    private int scoreDifference;
    String email;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTichDiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new R3cyDB(this);
        db.createSampleDataCoupon();
        db.createSampleDataCustomer();

        pieChart = findViewById(R.id.pie_chart);

        email = getIntent().getStringExtra("key_email");
        if (email == null || email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TichDiem.this);
            builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập truy cập vào trang tích điểm");
            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                    startActivity(new Intent(TichDiem.this, Signin_Main.class));
                }
            });
            builder.setNegativeButton("Về trang chủ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(TichDiem.this, TrangChu.class));

                    // Chuyển về trang chủ khi nhấn cancel
//                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                    Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                    // Set text color for "Ok" button
                    positiveButton.setTextColor(ContextCompat.getColor(TichDiem.this, R.color.blue));

                    // Set text color for "Cancel" button
                    negativeButton.setTextColor(ContextCompat.getColor(TichDiem.this, R.color.blue));
                }
            });

            dialog.show();
        }

        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        getDataCustomer();
        createPieChart();
    }

    private void createPieChart() {
        float currentScore = Float.parseFloat(binding.txtCurrentScore.getText().toString());
        float nextScore = Float.parseFloat(binding.txtNextScore.getText().toString());

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(currentScore, "Điểm hiện tại"));
        entries.add(new PieEntry(nextScore - currentScore, "Điểm còn lại"));

        // Định nghĩa màu
        int[] colors = {ContextCompat.getColor(this, R.color.dark_blue), ContextCompat.getColor(this, R.color.blue)};

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors); // Đặt màu từ mảng màu
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setDrawValues(false); // Ẩn số liệu trên biểu đồ

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Điểm thành viên");
        pieChart.animateY(1000);
        pieChart.invalidate(); // Refresh chart
    }



    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvCoupon.setLayoutManager(layoutManager);

        // Load data
        ArrayList<Coupon> coupons = new ArrayList<>();
        Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON);

        while (c.moveToNext()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date validDate = dateFormat.parse(c.getString(6));
                Date expireDate = dateFormat.parse(c.getString(7));

//                ArrayList<Integer> customerIds = new ArrayList<>();
//                String customerIdsString = c.getString(12);
//                if (customerIdsString != null && !customerIdsString.isEmpty()) {
//                    String[] ids = customerIdsString.replaceAll("\\[|\\]", "").split(",\\s*");
//                    for (String id : ids) {
//                        customerIds.add(Integer.parseInt(id.trim()));
//                    }
//                }

                ArrayList<Integer> customerIds = db.parseCustomerIdsFromString(c.getString(12));

//                ArrayList<Integer> customerIds = db.getCustomerIdsForCoupon(c.getInt(0));


                // Ghi log ra mảng customerIds
                Log.d("CustomerIds", "CustomerIds: " + customerIds);

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

        adapter = new CouponAdapterRecycler(this, coupons, email);
        Log.d("SharedPreferences", "Email ở tích điểm: " + email);
        binding.rcvCoupon.setAdapter(adapter);
    }

    private void getDataCustomer() {

        // Lấy email từ SharedPreferences
//        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
//        String email = preferences.getString("string", "");



        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);

//        Cursor c1 = db.getData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER);
//        if (c1.moveToFirst()){
//            customer = new Customer(c1.getInt(0),
//                    c1.getString(1),
//                    c1.getString(2),
//                    c1.getInt(3),
//                    c1.getString(4),
//                    c1.getString(5),
//                    c1.getString(6),
//                    c1.getInt(7),
//                    c1.getString(8),
//                    c1.getBlob(9),
//                    c1.getString(10)
//            );
//        }
//        c1.close();

        binding.txtCurrentClass.setText(customer.getCustomerType());
        binding.txtCurrentScore.setText(String.valueOf(customer.getMembershipScore()));

// Lấy thông tin về loại hạng tiếp theo và mục tiêu điểm tiếp theo
        String txtNextClass = db.getNextMembershipType(customer.getCustomerType());
        binding.txtNextClass.setText(txtNextClass);
        int txtNextScore = db.getNextMembershipTarget(customer.getMembershipScore());
        binding.txtNextScore.setText(String.valueOf(txtNextScore));

// Tính toán chênh lệch điểm giữa nextscore và current score
        int scoreDifference = txtNextScore - customer.getMembershipScore();
        binding.txtScoreLeft.setText(String.valueOf(scoreDifference));



    }


    private void addEvents() {
        binding.btnTichluy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TichDiem.this, Product_List.class);
                intent.putExtra("key_email", email);
                startActivity(intent);

                intent = new Intent("com.example.r3cy_mobileapp.ACTION_POINTS_ACCUMULATED");
                intent.putExtra("message", "Bạn đã tích thêm " + scoreDifference + " điểm");
                // Gửi broadcast
                sendBroadcast(intent);
            }
        });

        binding.btnDoidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TichDiem.this, DoiDiem.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

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
}