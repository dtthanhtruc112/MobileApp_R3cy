package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoBinding;

public class UserAccount_Info extends AppCompatActivity {

    ActivityUserAccountInfoBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
    protected void onResume() {
        super.onResume();
        Log.i("test","onResume");
        loadData();
    }

    private void loadData() {
//        int customerId = getCurrentCustomerId(); // Phương thức này cần được thay thế bằng cách lấy ID của khách hàng hiện tại từ session hoặc bất kỳ cơ chế nào bạn sử dụng để quản lý đăng nhập
//
//// Thực hiện truy vấn để lấy dữ liệu sản phẩm cho khách hàng đã đăng nhập
//        Cursor cursor = db.queryData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER +
//                " WHERE " + R3cyDB.CUSTOMER_ID_COLUMN + " = " + customerId);
//
//        Customer customer = null;
//
//// Nếu có kết quả từ truy vấn và con trỏ có thể di chuyển đến dòng đầu tiên
//        if (cursor != null && cursor.moveToFirst()) {
//            // Lấy thông tin của khách hàng từ cột của dòng đầu tiên
//            customer = new Customer(
//                    cursor.getInt(cursor.getColumnIndex(R3cyDB.CUSTOMER_ID)),
//                    cursor.getString(cursor.getColumnIndex(R3cyDB.CUSTOMER_NAME)),
//                    cursor.getString(cursor.getColumnIndex(R3cyDB.CUSTOMER_EMAIL)),
//                    // Thêm các thông tin khác của khách hàng tương ứng với cột của bảng
//            );
//        }
//
//// Đóng con trỏ sau khi sử dụng xong
//        if (cursor != null) {
//            cursor.close();
//        }


    }
}