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
        btnCancel =binding.btnCancle;

        // Nhận giá trị email từ Intent
//        email = getIntent().getStringExtra("key_userinfo");
        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra("key_userinfo");
        name.setText(userInfo.getFullName());
        username.setText(userInfo.getUserName());
        phone.setText(userInfo.getPhone());
        useremail.setText(userInfo.getEmail());
        birthday.setText(userInfo.getBirthday());
        gender.setText(userInfo.getGender());
//        UserInfo userInfo = (UserInfo)  getIntent().getSerializableExtra("key_email");
        // Gọi phương thức để tải thông tin người dùng
//        db = new R3cyDB(this);
//
//        getUserDetails();
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
//                intent.putExtra("key_email", email);
//                startActivity(intent);
//            }
//        });


    }

//    public void updateData(UserInfo info) {
//        EditText edtName = findViewById(R.id.editname);
//        edtName.setText(info.getFullName());
//
//        EditText edtUsername = findViewById(R.id.editusername);
//        edtUsername.setText(info.getUserName());
//
//        EditText edtPhone = findViewById(R.id.editphonenumber);
//        edtPhone.setText(info.getPhone());
//
//        EditText edtEmail = findViewById(R.id.editemail);
//        edtEmail.setText(info.getEmail());
//
//        EditText edtGender = findViewById(R.id.editgioitinh);
//        edtGender.setText(info.getGender());
//
//        EditText edtBirthday = findViewById(R.id.ngaysinh);
//        edtBirthday.setText(info.getBirthday());
//
//
//        Button btnSave = findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Update Data
//                boolean updated = db.execSql("UPDATE " + R3cyDB.TBL_CUSTOMER + " SET " + R3cyDB.FULLNAME + "='" + edtName.getText().toString() + "', " + R3cyDB.USERNAME + "=" + edtUsername.getText().toString() + "', " + R3cyDB.PHONE
//                        + "=" + edtPhone.getText().toString() + "', "
//                        + R3cyDB.EMAIL + "=" + edtEmail.getText().toString() +
//                        "', " + R3cyDB.GENDER + "=" + edtGender.getText().toString() +
//                        "', " + R3cyDB.BIRTHDAY + "=" + edtBirthday.getText().toString() +
//                        " WHERE " + R3cyDB.CUSTOMER_ID + "=" + info.getCustomerid());
//                //UPDATE product SET ProductName='Test', ProductPrice=12000 WHERE ProductCode=2
//                if(updated){
//                    Toast.makeText(UserAccount_Info_Edit.this, "Success !", Toast.LENGTH_SHORT).show();
//                    // Reload the data or perform any other necessary actions after successful update
//                    getUserDetails();
//                } else {
//                    Toast.makeText(UserAccount_Info_Edit.this, "Fail !", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
////        Button btnCancel = findViewById(R.id.btnCancle);
////        btnCancel.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
////                intent.putExtra("key_email", email);
////                startActivity(intent);
////                // Handle cancel action if needed
////
////            }
////        });
//    }

    private void getUserDetails() {
        db = new R3cyDB(this);
        // Kiểm tra xem email có null không
        if (email != null) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            ArrayList<UserInfo> customer = db.getLoggedinUserDetails(email);

            // Kiểm tra xem danh sách khách hàng có trống không
            if (customer != null && customer.size() > 0) {
                UserInfo userInfo = customer.get(0);
                name.setText(userInfo.getFullName());
                username.setText(userInfo.getUserName());
                phone.setText(userInfo.getPhone());
                useremail.setText(userInfo.getEmail());
                gender.setText(userInfo.getGender());
                birthday.setText(userInfo.getBirthday());
            } else {
                // Xử lý trường hợp không tìm thấy thông tin người dùng
                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp không nhận được email từ Intent
            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
        }

    }
}