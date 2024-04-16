package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoBinding;

import java.util.ArrayList;

public class UserAccount_Info extends AppCompatActivity {

    ActivityUserAccountInfoBinding binding;
    TextView name, useremail;
    String email;
    R3cyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = binding.editname;
        useremail = binding.editemail;

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
                UserInfo userInfo = customer.get(0);
                name.setText(userInfo.getFullName());
                useremail.setText(userInfo.getEmail());
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
    public void  openDialogEdit(UserInfo p) {
        Dialog dialog = new Dialog(UserAccount_Info.this);
        dialog.setContentView(R.layout.user_account_info_edit);

        EditText edtName = dialog.findViewById(R.id.editname);
        edtName.setText(p.getFullName());

        EditText editEmail = dialog.findViewById(R.id.editemail);
        editEmail.setText(String.valueOf(p.getEmail()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update Data
                boolean updated = db.execSql("UPDATE " + R3cyDB.TBL_CUSTOMER + " SET " + R3cyDB.FULLNAME + "='" + edtName.getText().toString() + "', " + R3cyDB.EMAIL + "=" + editEmail.getText().toString() + " WHERE " + R3cyDB.CUSTOMER_ID + "=" + p.getCustomerid());
                //UPDATE product SET ProductName='Test', ProductPrice=12000 WHERE ProductCode=2
                if (updated) {
                    Toast.makeText(UserAccount_Info.this, "Success !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserAccount_Info.this, "Fail !", Toast.LENGTH_SHORT).show();
                }
                getUserDetails();
                dialog.dismiss();
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btnCancle);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }



//    protected void onResume() {
//        super.onResume();
//        Log.i("test","onResume");
//        loadData();
//    }
//
//    private void loadData() {
////        int customerId = getCurrentCustomerId(); // Phương thức này cần được thay thế bằng cách lấy ID của khách hàng hiện tại từ session hoặc bất kỳ cơ chế nào bạn sử dụng để quản lý đăng nhập
////
////// Thực hiện truy vấn để lấy dữ liệu sản phẩm cho khách hàng đã đăng nhập
////        Cursor cursor = db.queryData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER +
////                " WHERE " + R3cyDB.CUSTOMER_ID_COLUMN + " = " + customerId);
////
////        Customer customer = null;
////
////// Nếu có kết quả từ truy vấn và con trỏ có thể di chuyển đến dòng đầu tiên
////        if (cursor != null && cursor.moveToFirst()) {
////            // Lấy thông tin của khách hàng từ cột của dòng đầu tiên
////            customer = new Customer(
////                    cursor.getInt(cursor.getColumnIndex(R3cyDB.CUSTOMER_ID)),
////                    cursor.getString(cursor.getColumnIndex(R3cyDB.CUSTOMER_NAME)),
////                    cursor.getString(cursor.getColumnIndex(R3cyDB.CUSTOMER_EMAIL)),
////                    // Thêm các thông tin khác của khách hàng tương ứng với cột của bảng
////            );
////        }
////
////// Đóng con trỏ sau khi sử dụng xong
////        if (cursor != null) {
////            cursor.close();
////        }
//
//
//    }
}
