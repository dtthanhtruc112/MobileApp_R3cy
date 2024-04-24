package com.example.r3cy_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.AddressAdapter;
import com.example.adapter.AddressAdapter2;
import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityCheckoutAddressListBinding;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountAddressBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserAccount_Address extends AppCompatActivity {
    ActivityUserAccountAddressBinding binding;
    R3cyDB db;
    AddressAdapter2 adapter;
    ArrayList<Address> addresses;
    String email;
    Customer customer;
    int customerId ;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở checkout: " + email);





        createDb();


        addEvents();
        addEvents2();


        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        customerId = customer.getCustomerId();



    }

    private void addEvents2() {
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_account);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(), Product_List.class);
                    intent1.putExtra("key_email", email);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(),BlogDetail.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
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
    }

    private void addEvents() {

        binding.btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Mở trang thêm địa chỉ mới, thêm địa chỉ mới và lưu xuống DB bảng Address
                Intent intent = new Intent(UserAccount_Address.this, UserAccount_Adress_Edit.class);
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
//        db.createSampleDataAddress();
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
        adapter = new AddressAdapter2(this, R.layout.address_item2, addresses);
        binding.lvAddressList.setAdapter(adapter);

    }
    public void openDialogDeleteAddress(Address a){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAccount_Address.this);
        builder.setTitle("Xác nhận xóa!");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setMessage("Bạn muốn xóa địa chỉ "+ a.getAddressDetails() + ", " + a.getDistrict() + ", " + a.getProvince() + "?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                Delete data
                boolean deleted = db.execSql("DELETE FROM " + db.TBl_ADDRESS + " WHERE " + R3cyDB.ADDRESS_ID + "=" + a.getAddressId());
                if(deleted){
                    Toast.makeText(UserAccount_Address.this, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserAccount_Address.this, "Fail", Toast.LENGTH_SHORT).show();
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
                positiveButton.setTextColor(ContextCompat.getColor(UserAccount_Address.this, R.color.blue));

                // Set text color for "Cancel" button
                negativeButton.setTextColor(ContextCompat.getColor(UserAccount_Address.this, R.color.blue));
            }
        });

        dialog.show();

    }
    public void openEditAddressActivity(Address a){
        Intent intent = new Intent(this, UserAccount_Adress_Edit.class);
        intent.putExtra("ADDRESS_ID", a.getAddressId());
        intent.putExtra("key_email", email);
        startActivity(intent);

    }


    }
