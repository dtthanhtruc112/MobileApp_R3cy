package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoEditBinding;

import java.util.ArrayList;

public class UserAccount_Info_Edit extends AppCompatActivity {
    ActivityUserAccountInfoEditBinding binding;
    EditText name, username, phone, useremail, gender, birthday;
    Button btnedit, btnCancel;
    String email;
    R3cyDB db;
    UserInfo userInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = binding.editname;
        username = binding.editusername;
        phone = binding.editphonenumber;
        useremail = binding.editemail;
        gender = binding.editgioitinh;
        birthday = binding.ngaysinh;
        btnedit =binding.btnSave;
//        btnCancel =binding.btnCancle;

        userInfos = (UserInfo) getIntent().getSerializableExtra("key_userinfo");
        name.setText(userInfos.getFullName());
        username.setText(userInfos.getUserName());
        phone.setText(userInfos.getPhone());
        useremail.setText(userInfos.getEmail());
        birthday.setText(userInfos.getBirthday());
        gender.setText(userInfos.getGender());
        // Gọi phương thức để tải thông tin người dùng
//        db = new R3cyDB(this);
//
//        getUserDetails();
//        addEvents();


    }

//    private void addEvents() {
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
//                intent.putExtra("key_email", email);
//                startActivity(intent);
//            }
//        });
//    }

    public  void  upateUserProfile(View view){
        String name1 = name.getText().toString();
        String username1 = username.getText().toString();
        String phone1 = phone.getText().toString();
        String email = useremail.getText().toString();
        String gender1 = gender.getText().toString();
        String birthday1 = birthday.getText().toString();

        db = new R3cyDB(this);
        boolean b = db.upDateUserProfile(userInfos.getEmail(), name1, username1, phone1, gender1, birthday1);
        if (b){
            Toast.makeText(this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
            intent.putExtra("key_email", email);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Lưu dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
//                intent.putExtra("key_email", email);
//                startActivity(intent);
//            }
//        });

    }



//    private void getUserDetails() {
//        db = new R3cyDB(this);
//        // Kiểm tra xem email có null không
//        if (email != null) {
//            // Lấy thông tin người dùng từ cơ sở dữ liệu
//            ArrayList<UserInfo> customer = db.getLoggedinUserDetails(email);
//
//            // Kiểm tra xem danh sách khách hàng có trống không
//            if (customer != null && customer.size() > 0) {
//                UserInfo userInfo = customer.get(0);
//                name.setText(userInfo.getFullName());
//                username.setText(userInfo.getUserName());
//                phone.setText(userInfo.getPhone());
//                useremail.setText(userInfo.getEmail());
//                gender.setText(userInfo.getGender());
//                birthday.setText(userInfo.getBirthday());
//            } else {
//                // Xử lý trường hợp không tìm thấy thông tin người dùng
//                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Xử lý trường hợp không nhận được email từ Intent
//            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}