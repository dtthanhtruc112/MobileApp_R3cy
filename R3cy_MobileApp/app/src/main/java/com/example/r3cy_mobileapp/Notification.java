package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.NotificationAdapter;
import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Notification extends AppCompatActivity {

    private R3cyDB db;
    private ImageView imvTrangChu;
    private ListView lvnotification;
    private NotificationAdapter adapter;
    BottomNavigationView navigationView;
    String email;
    private int customerId;

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("message");
//            Log.d("Notification","Message receiver: " + message);
//            if (message != null) {
//                notifications.add(message);
//                adapter.notifyDataSetChanged();
//            }
//            recreate();
//        }
//    };
//
//    private BroadcastReceiver orderSuccessReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("orderSuccess");
//            Log.d("Notification","Message orderSuccessReceiver: " + message);
//            if (message != null) {
//                notifications.add(message);
//                adapter.notifyDataSetChanged();
//                Log.d("Notification","Add message vào noti thành công và thông báo adapter thay đổi data");
//            }
//            recreate();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

//        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
//        email = preferences.getString("string", "");

        Log.d("SharedPreferences", "Email ở noti: " + email);

        db = new R3cyDB(this);
        imvTrangChu = findViewById(R.id.imvTrangChu);
        lvnotification = findViewById(R.id.lvnotification);

        customerId = db.getCustomerIdFromCustomer(email);
        Log.d("SharedPreferences", "CustomerID ở noti: " + customerId);
        // Gọi phương thức để lấy danh sách thông báo của customerId
        ArrayList<String[]> notifications = db.getNotificationsByCustomerId(customerId);
        Log.d("Notification", "Danh sách noti: " + notifications);

        // Khởi tạo adapter và gán cho ListView
        adapter = new NotificationAdapter(this, notifications);
        lvnotification.setAdapter(adapter);

        imvTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notification.this, TrangChu.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
                finish();
            }
        });
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("message");
//        if (message != null) {
//            notifications.add(message);
//            adapter.notifyDataSetChanged();
//        }
//
//        // Đăng ký BroadcastReceiver
//        registerReceiver(receiver, new IntentFilter("com.example.r3cy_mobileapp.ACTION_POINTS_ACCUMULATED"));
//
//        // Đăng ký BroadcastReceiver để nhận thông điệp từ Checkout khi đặt hàng thành công
//        registerReceiver(orderSuccessReceiver, new IntentFilter("com.example.r3cy_mobileapp.ACTION_ORDER_SUCCESS"));

        addEvents();
    }

    private void addEvents() {

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        // Hủy đăng ký BroadcastReceiver
//        unregisterReceiver(receiver);
//        unregisterReceiver(orderSuccessReceiver);
//    }
}