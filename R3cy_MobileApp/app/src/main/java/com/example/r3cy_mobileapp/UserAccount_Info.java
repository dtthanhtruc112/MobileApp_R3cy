package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.UserInfoAdapter;
import com.example.databases.R3cyDB;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoBinding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = binding.editname;
        username = binding.editusername;
        phone = binding.editphonenumber;
        useremail = binding.editemail;
        gender = binding.editgioitinh;
        birthday = binding.ngaysinh;
        btnedit =binding.btnchinhsua;

        // Nhận giá trị email từ Intent
        email = getIntent().getStringExtra("key_email");

        // Gọi phương thức để tải thông tin người dùng

        getUserDetails();
//        addEvent();
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
