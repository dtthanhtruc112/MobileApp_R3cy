package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.databinding.ActivityCheckoutAddressBinding;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountAdressAddBinding;

public class UserAccount_Adress_Add extends AppCompatActivity {

    ActivityUserAccountAdressAddBinding binding ;
    R3cyDB db;
    String email;
    Customer customer;
    // Lấy CUSTOMER_ID từ SharedPreferences hoặc bất kỳ nguồn dữ liệu nào khác
    int customerId ; // Thay bằng cách lấy CUSTOMER_ID thích hợp


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountAdressAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở checkout address: " + email);



        createDb();

        addEvent();

        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        customerId = customer.getCustomerId();
    }
    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataAddress();
        db.createSampleDataCustomer();

        if (db != null) {
            Log.d("AddressListDB", "Database created successfully");
        } else {
            Log.e("AddressListDB", "Failed to create database");
        }
    }



    private void addEvent() {
        // Khởi tạo Adapter và gắn dữ liệu vào Spinner
        // Khai báo mảng chứa các lựa chọn
        String[] addressTypes = {"nhà riêng", "văn phòng"};
        ArrayAdapter<String> adapterAddress = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, addressTypes);
        binding.addressTypeSpinner.setAdapter(adapterAddress);


        binding.btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveAddressClicked();
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void btnSaveAddressClicked() {
        // Lấy giá trị từ Spinner
        String addressType = (String) binding.addressTypeSpinner.getSelectedItem();

        // Lấy dữ liệu từ các EditText
        String receiverName = binding.edtReceiverName.getText().toString();
        String receiverPhone = binding.edtReceiverPhone.getText().toString();
        String province = binding.edtProvince.getText().toString();
        String district = binding.edtDistrict.getText().toString();
        String ward = binding.edtWard.getText().toString();
        String detailAddress = binding.edtDetailAddress.getText().toString();
        int DefaultAddress = binding.switchDefaultAddress.isChecked() ? 1 : 0;



        // Kiểm tra và xử lý dữ liệu
        if (receiverName.isEmpty() || receiverPhone.isEmpty() || province.isEmpty() || district.isEmpty() || ward.isEmpty() || detailAddress.isEmpty()) {
            Toast.makeText(UserAccount_Adress_Add.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
//             Truy vấn SQL để kiểm tra xem địa chỉ mới có giống với bất kỳ địa chỉ nào đã được lưu trước đó của cùng một customerId không
            String query = "SELECT * FROM " + R3cyDB.TBl_ADDRESS + " WHERE " + R3cyDB.CUSTOMER_ID + " = " + customerId;
            Cursor cursor = db.getData(query);

            // Biến để kiểm tra xem có ít nhất một bản ghi nào đó không giống với dữ liệu mới không
            boolean isDifferent = true;

            // Lặp qua các bản ghi để kiểm tra xem dữ liệu mới có giống với bất kỳ bản ghi nào không
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String savedReceiverName = cursor.getString(cursor.getColumnIndex(R3cyDB.RECEIVER_NAME));
                    @SuppressLint("Range") String savedReceiverPhone = cursor.getString(cursor.getColumnIndex(R3cyDB.RECEIVER_PHONE));
                    @SuppressLint("Range") String savedProvince = cursor.getString(cursor.getColumnIndex(R3cyDB.PROVINCE));
                    @SuppressLint("Range") String savedDistrict = cursor.getString(cursor.getColumnIndex(R3cyDB.DISTRICT));
                    @SuppressLint("Range") String savedWard = cursor.getString(cursor.getColumnIndex(R3cyDB.WARD));
                    @SuppressLint("Range") String savedDetailAddress = cursor.getString(cursor.getColumnIndex(R3cyDB.ADDRESS_DETAILS));
                    @SuppressLint("Range") int savedIsDefault = cursor.getInt(cursor.getColumnIndex(R3cyDB.IS_DEFAULT));
                    @SuppressLint("Range") String savedAddressType = cursor.getString(cursor.getColumnIndex(R3cyDB.ADDRESS_TYPE));

                    // So sánh dữ liệu mới với dữ liệu đã lưu
                    if (receiverName.equals(savedReceiverName) &&
                            receiverPhone.equals(savedReceiverPhone) &&
                            province.equals(savedProvince) &&
                            district.equals(savedDistrict) &&
                            ward.equals(savedWard) &&
                            detailAddress.equals(savedDetailAddress) &&
                            DefaultAddress == savedIsDefault &&
                            addressType.equals(savedAddressType)) {
                        // Nếu tất cả các cột giống nhau, đặt isDifferent thành false và thoát khỏi vòng lặp
                        isDifferent = false;
                        break;
                    }
                } while (cursor.moveToNext());

                // Đóng con trỏ sau khi sử dụng
                cursor.close();
            }

            // Nếu isDifferent vẫn là true, có ít nhất một cột khác nhau, lưu trữ dữ liệu mới


            if (isDifferent) {
                // Thực hiện lưu địa chỉ xuống cơ sở dữ liệu
                String insertQuery = "INSERT INTO " + R3cyDB.TBl_ADDRESS + "(" +
                        R3cyDB.CUSTOMER_ID + ", " +
                        R3cyDB.RECEIVER_NAME + ", " +
                        R3cyDB.RECEIVER_PHONE + ", " +
                        R3cyDB.PROVINCE + ", " +
                        R3cyDB.DISTRICT + ", " +
                        R3cyDB.WARD + ", " +
                        R3cyDB.ADDRESS_DETAILS + ", " +
                        R3cyDB.IS_DEFAULT + ", " +
                        R3cyDB.ADDRESS_TYPE +
                        ") VALUES (" +
                        customerId + ", '" +
                        receiverName + "', '" +
                        receiverPhone + "', '" +
                        province + "', '" +
                        district + "', '" +
                        ward + "', '" +
                        detailAddress + "', " +
                        DefaultAddress + ", '" +
                        addressType + "')";

                boolean insertSuccess = db.execSql(insertQuery);
                if (insertSuccess) {
                    // Hiển thị thông báo lưu thành công
                    Toast.makeText(UserAccount_Adress_Add.this, "Đã lưu địa chỉ thành công", Toast.LENGTH_SHORT).show();

                    // Đặt địa chỉ mới làm địa chỉ mặc định nếu được chọn
                    if (DefaultAddress == 1) {
                        int newAddressId = db.getLastInsertedAddressId(); // Lấy ID của địa chỉ vừa được thêm vào
                        setDefaultAddress(customerId, newAddressId);
                    }

                    // Chuyển đến màn hình khác (ví dụ: Danh sách địa chỉ)
                    Intent intent = new Intent(UserAccount_Adress_Add.this, UserAccount_Address.class);
                    intent.putExtra("key_email", email);
                    startActivity(intent);

                    // Hoặc kết thúc activity hiện tại nếu không cần chuyển đến màn hình khác
                    // finish();
                } else {
                    Log.e("Lỗi", "Lưu địa chỉ thất bại: " + insertQuery);
                    // Hiển thị thông báo lưu thất bại nếu có lỗi xảy ra khi thực hiện câu lệnh SQL
                    Toast.makeText(UserAccount_Adress_Add.this, "Lưu địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UserAccount_Adress_Add.this, "Địa chỉ này đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Hàm đặt địa chỉ mới làm địa chỉ mặc định
    private void setDefaultAddress(int customerId, int newAddressId) {
        // Cập nhật tất cả các địa chỉ khác của customerId về không phải là địa chỉ mặc định
        String updateQuery = "UPDATE " + R3cyDB.TBl_ADDRESS +
                " SET " + R3cyDB.IS_DEFAULT + " = 0" +
                " WHERE " + R3cyDB.CUSTOMER_ID + " = " + customerId +
                " AND " + R3cyDB.ADDRESS_ID + " != " + newAddressId;

        db.execSql(updateQuery);
    }
}