package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.databases.R3cyDB;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    private R3cyDB db;
    private ImageView imvTrangChu;
    private ListView lvnotification;
    private ArrayList<String> notifications;
    private ArrayAdapter<String> adapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if (message != null) {
                notifications.add(message);
                adapter.notifyDataSetChanged();
            }
            recreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        db = new R3cyDB(this);
        imvTrangChu = findViewById(R.id.imvTrangChu);
        lvnotification = findViewById(R.id.lvnotification);

        notifications = new ArrayList<>(); // Khởi tạo notifications
        adapter = new ArrayAdapter<>(this, R.layout.item_notification, R.id.notification_message, notifications);
        lvnotification.setAdapter(adapter);

        imvTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notification.this, TrangChu.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        if (message != null) {
            notifications.add(message);
            adapter.notifyDataSetChanged();
        }

        // Đăng ký BroadcastReceiver
        registerReceiver(receiver, new IntentFilter("com.example.r3cy_mobileapp.ACTION_POINTS_ACCUMULATED"));
    }

////    @Override
////    protected void onDestroy() {
//        super.onDestroy();
////        // Hủy đăng ký BroadcastReceiver
////        unregisterReceiver(receiver);
////    }
}