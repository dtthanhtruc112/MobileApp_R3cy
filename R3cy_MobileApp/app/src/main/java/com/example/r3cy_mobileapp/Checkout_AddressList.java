package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.AddressAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.databinding.ActivityCheckoutAddressListBinding;

import java.util.ArrayList;

public class Checkout_AddressList extends AppCompatActivity {

    ActivityCheckoutAddressListBinding binding;
    R3cyDB db;
    AddressAdapter adapter;
    ArrayList<Address> addresses;
    String email;
    Customer customer;
    // Lấy CUSTOMER_ID từ SharedPreferences hoặc bất kỳ nguồn dữ liệu nào khác
    int customerId ; // Thay bằng cách lấy CUSTOMER_ID thích hợp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckoutAddressListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở checkout: " + email);



        createDb();
        addEvents();

        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        customerId = customer.getCustomerId();


    }

    private void addEvents() {
        binding.btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Mở trang thêm địa chỉ mới, thêm địa chỉ mới và lưu xuống DB bảng Address
                Intent intent = new Intent(Checkout_AddressList.this, Checkout_Address.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

    private void loadData() {
        // Lấy dữ liệu từ AddressDao
        addresses = (ArrayList<Address>) db.getAddressesForCustomer(customerId);
        Log.i("Address size", "Number of items retrieved: " + addresses.size());

        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new AddressAdapter(this, R.layout.address_item, addresses);
        binding.lvAddressList.setAdapter(adapter);

    }
    public void openDialogDeleteAddress(Address a){
        AlertDialog.Builder builder = new AlertDialog.Builder(Checkout_AddressList.this);
        builder.setTitle("Xác nhận xóa!");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setMessage("Bạn muốn xóa địa chỉ "+ a.getAddressDetails() + ", " + a.getDistrict() + ", " + a.getProvince() + "?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                Delete data
                boolean deleted = db.execSql("DELETE FROM " + db.TBl_ADDRESS + " WHERE " + R3cyDB.ADDRESS_ID + "=" + a.getAddressId());
                if(deleted){
                    Toast.makeText(Checkout_AddressList.this, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Checkout_AddressList.this, "Fail", Toast.LENGTH_SHORT).show();
                }
                loadData();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                close
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                // Set text color for "Ok" button
                positiveButton.setTextColor(ContextCompat.getColor(Checkout_AddressList.this, R.color.blue));

                // Set text color for "Cancel" button
                negativeButton.setTextColor(ContextCompat.getColor(Checkout_AddressList.this, R.color.blue));
            }
        });

        dialog.show();

    }
    public void openEditAddressActivity(Address a){
        Intent intent = new Intent(this, EditAddress.class);
        intent.putExtra("ADDRESS_ID", a.getAddressId());
        intent.putExtra("key_email", email);
        startActivity(intent);

    }
    public void openCheckoutActivity(Address a){
        Intent intent = new Intent(this, Checkout.class);
        intent.putExtra("SELECTED_ADDRESS_ID", a.getAddressId());
        intent.putExtra("key_email", email);
//        startActivity(intent);
        setResult(RESULT_OK, intent);
        finish();

    }

}