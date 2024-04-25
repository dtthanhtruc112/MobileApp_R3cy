package com.example.r3cy_mobileapp;

import static com.example.databases.R3cyDB.ADDRESS_ID;
import static com.example.databases.R3cyDB.TBl_ADDRESS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.databinding.ActivityEditAddressBinding;

public class EditAddress extends AppCompatActivity {
    ActivityEditAddressBinding binding;
    R3cyDB db;
    private int addressId;
    String email;
    Customer customer;
    // Lấy CUSTOMER_ID từ SharedPreferences hoặc bất kỳ nguồn dữ liệu nào khác
    int customerId ; // Thay bằng cách lấy CUSTOMER_ID thích hợp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDb();

        email = getIntent().getStringExtra("key_email");
        Log.d("SharedPreferences", "Email ở editaddress: " + email);

        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        customerId = customer.getCustomerId();


        // Lấy ID của địa chỉ cần chỉnh sửa từ Intent
        addressId = getIntent().getIntExtra("ADDRESS_ID", -1);
        if (addressId == -1) {
            // Xử lí lỗi nếu không có ID
            Toast.makeText(this, "Không lấy được addressId từ intent", Toast.LENGTH_SHORT).show();
            finish();
        }

        addEvents();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadAddressData();
    }

    private void addEvents() {
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

        // Lấy CUSTOMER_ID từ SharedPreferences hoặc bất kỳ nguồn dữ liệu nào khác=> thêm vào sau
//        int customerId = 1; // Thay bằng cách lấy CUSTOMER_ID thích hợp

        // Lấy dữ liệu ban đầu của địa chỉ từ cơ sở dữ liệu
        Address initialAddress = db.getAddressById(addressId);


        // Kiểm tra và xử lý dữ liệu
        if (receiverName.isEmpty() || receiverPhone.isEmpty() || province.isEmpty() || district.isEmpty() || ward.isEmpty() || detailAddress.isEmpty()) {
            Toast.makeText(EditAddress.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (initialAddress != null && initialAddress.getAddressType() != null) {
            // So sánh dữ liệu mới với dữ liệu ban đầu
            boolean dataChanged = !receiverName.equals(initialAddress.getReceiverName()) ||
                    !receiverPhone.equals(initialAddress.getReceiverPhone()) ||
                    !province.equals(initialAddress.getProvince()) ||
                    !district.equals(initialAddress.getDistrict()) ||
                    !ward.equals(initialAddress.getWard()) ||
                    !detailAddress.equals(initialAddress.getAddressDetails()) ||
                    DefaultAddress != initialAddress.getIsDefault()
//                    || !addressType.equals(initialAddress.getAddressType())
                    ;

            if (dataChanged) {
                // Nếu có sự thay đổi, thực hiện cập nhật dữ liệu
                String updateQuery = "UPDATE " + R3cyDB.TBl_ADDRESS + " SET " +
                        R3cyDB.RECEIVER_NAME + " = '" + receiverName + "', " +
                        R3cyDB.RECEIVER_PHONE + " = '" + receiverPhone + "', " +
                        R3cyDB.PROVINCE + " = '" + province + "', " +
                        R3cyDB.DISTRICT + " = '" + district + "', " +
                        R3cyDB.WARD + " = '" + ward + "', " +
                        R3cyDB.ADDRESS_DETAILS + " = '" + detailAddress + "', " +
                        R3cyDB.IS_DEFAULT + " = " + DefaultAddress + ", " +
                        R3cyDB.ADDRESS_TYPE + " = '" + addressType + "' WHERE " +
                        R3cyDB.ADDRESS_ID + " = " + addressId;

                boolean updateSuccess = db.execSql(updateQuery);

                if (updateSuccess) {
                    // Hiển thị thông báo cập nhật thành công
                    Toast.makeText(EditAddress.this, "Đã cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    // Đặt địa chỉ mới làm địa chỉ mặc định nếu được chọn
                    if (DefaultAddress == 1) {
                        setDefaultAddress(customerId, addressId);
                    }

                    // Chuyển đến màn hình khác (ví dụ: Danh sách địa chỉ)
                    Intent intent = new Intent(EditAddress.this, Checkout_AddressList.class);
                    intent.putExtra("key_email", email);
                    startActivity(intent);

                } else {
                    // Hiển thị thông báo cập nhật thất bại
                    Toast.makeText(EditAddress.this, "Cập nhật địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("Lỗi", "Cập nhật địa chỉ thất bại: " + updateQuery);
                }
            } else {
                // Nếu không có sự thay đổi, thông báo cho người dùng biết
                Toast.makeText(EditAddress.this, "Bạn chưa thay đổi gì so với dữ liệu ban đầu", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý khi không lấy được dữ liệu ban đầu
            Toast.makeText(EditAddress.this, "Không thể lấy được dữ liệu ban đầu", Toast.LENGTH_SHORT).show();
        }
    }


    // Hàm đặt địa chỉ mới làm địa chỉ mặc định
    @SuppressLint("Range")
    private void setDefaultAddress(int customerId, int addressId) {
        Log.d("setDefaultAddress", "Address ID hiện tại: " + addressId);

        // Log giá trị của isdefault của các địa chỉ trước khi cập nhật
        String selectQuery = "SELECT * FROM " + R3cyDB.TBl_ADDRESS + " WHERE " + R3cyDB.CUSTOMER_ID + " = " + customerId;
        Cursor cursor = db.getData(selectQuery);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int AddressId = cursor.getInt(cursor.getColumnIndex(R3cyDB.ADDRESS_ID));
                @SuppressLint("Range") int isDefault = cursor.getInt(cursor.getColumnIndex(R3cyDB.IS_DEFAULT));
                Log.d("BeforeSetDefaultAddress", "Address ID: " + AddressId + ", isDefault: " + isDefault);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Cập nhật tất cả các địa chỉ khác của customerId về không phải là địa chỉ mặc định,
        // loại trừ địa chỉ mới được cập nhật
        String updateQuery = "UPDATE " + R3cyDB.TBl_ADDRESS +
                " SET " + R3cyDB.IS_DEFAULT + " = 0" +
                " WHERE " + R3cyDB.CUSTOMER_ID + " = " + customerId +
                " AND " + R3cyDB.ADDRESS_ID + " != " + addressId;

        db.execSql(updateQuery);

        // Đặt địa chỉ mới làm địa chỉ mặc định
        String setDefaultQuery = "UPDATE " + R3cyDB.TBl_ADDRESS +
                " SET " + R3cyDB.IS_DEFAULT + " = 1" +
                " WHERE " + R3cyDB.CUSTOMER_ID + " = " + customerId +
                " AND " + R3cyDB.ADDRESS_ID + " = " + addressId;

        db.execSql(setDefaultQuery);

        // Log giá trị của isdefault của các địa chỉ sau khi cập nhật
        Cursor cursorAfterUpdate = db.getData(selectQuery);
        if (cursorAfterUpdate != null && cursorAfterUpdate.moveToFirst()) {
            do {
                int AddressId = cursorAfterUpdate.getInt(cursorAfterUpdate.getColumnIndex(R3cyDB.ADDRESS_ID));
                @SuppressLint("Range") int isDefault = cursorAfterUpdate.getInt(cursorAfterUpdate.getColumnIndex(R3cyDB.IS_DEFAULT));
                Log.d("AfterSetDefaultAddress", "Address ID: " + AddressId + ", isDefault: " + isDefault);
            } while (cursorAfterUpdate.moveToNext());
            cursorAfterUpdate.close();
        }
    }










    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataAddress();

        if (db != null) {
            Log.d("AddressListDB", "Database created successfully");
        } else {
            Log.e("AddressListDB", "Failed to create database");
        }
    }
    private void loadAddressData() {
        Address address = db.getAddressById(addressId);
        // Kiểm tra xem địa chỉ có tồn tại hay không
        if (address != null) {
            // Hiển thị dữ liệu của địa chỉ lên các EditText
            binding.edtReceiverName.setText(address.getReceiverName());
            binding.edtReceiverPhone.setText(address.getReceiverPhone());
            binding.edtProvince.setText(address.getProvince());
            binding.edtDistrict.setText(address.getDistrict());
            binding.edtWard.setText(address.getWard());
            binding.edtDetailAddress.setText(address.getAddressDetails());
            // Đặt trạng thái của switchDefaultAddress dựa trên giá trị của isDefault trong địa chỉ
            binding.switchDefaultAddress.setChecked(address.getIsDefault() == 1);
        } else {
            // Xử lí lỗi nếu không tìm thấy địa chỉ
            Toast.makeText(this, "Không tìm thấy địa chỉ", Toast.LENGTH_SHORT).show();
            finish(); // Đóng trang chỉnh sửa nếu không tìm thấy địa chỉ
        }
    }



}