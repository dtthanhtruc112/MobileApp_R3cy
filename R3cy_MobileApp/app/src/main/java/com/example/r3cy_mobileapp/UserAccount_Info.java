package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.UserInfoAdapter;
import com.example.databases.R3cyDB;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserAccount_Info extends AppCompatActivity {

    ActivityUserAccountInfoBinding binding;
    TextView name;
    TextView username;
    TextView phone;
    TextView useremail;
    TextView gender;
    TextView birthday;
    Button btnedit;
    String email;
    R3cyDB db;
    UserInfoAdapter adapter;
    ArrayList<UserInfo> userInfo;
    ConstraintLayout editinfo;
    UserInfo userInfos;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        name = binding.editname;
        username = binding.editusername;
        phone = binding.editphonenumber;
        useremail = binding.editemail;
        gender = binding.editgioitinh;
        birthday = binding.ngaysinh;
        btnedit =binding.btnchinhsua;

        // Nhận giá trị email từ Intent
        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở accinfo: " + email);

        // Gọi phương thức để tải thông tin người dùng

        getUserDetails();
//        addEvent();

        addEvents();
    }

    private void addEvents() {
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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getUserDetails() {
        db = new R3cyDB(this);
        // Kiểm tra xem email có null không
        if (email != null) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            ArrayList<UserInfo> customer = db.getLoggedinUserDetails(email);

            // Kiểm tra xem danh sách khách hàng có trống không
            if (customer != null && customer.size() > 0) {
                userInfos = customer.get(0);
                name.setText(userInfos.getFullName());
                username.setText(userInfos.getUserName());
                phone.setText(userInfos.getPhone());
                useremail.setText(userInfos.getEmail());
                gender.setText(userInfos.getGender());
                birthday.setText(userInfos.getBirthday());
            } else {
                // Xử lý trường hợp không tìm thấy thông tin người dùng
                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp không nhận được email từ Intent
            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
        }


    }


//    private void addEvent() {
//        binding.btnchinhsua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info.this, user_acount.class);
//                startActivity(intent);
//            }
//        });
//    }
    public void updateProfile(View view) {
        Intent intent = new Intent(UserAccount_Info.this, UserAccount_Info_Edit.class);
        intent.putExtra("key_userinfo", userInfos);
        startActivity(intent);
    }

}
