package com.example.r3cy_mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.adapter.CartAdapter;
import com.example.adapter.PaymentItemAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.databinding.ActivityCheckoutBinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Checkout extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    R3cyDB db;
    ProductDao productDao;
    PaymentItemAdapter adapter;

    ArrayList<CartItem> selectedItems;

    Address address;
    private int addressIdFromIntent;
//    address khi đổi địa chỉ nhận hàng
    private static final int ADDRESS_SELECTION_REQUEST_CODE = 1;

    int customerId;

    String email;
    Customer customer;
//    Thay customer lấy sau khi login ra


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở checkout: " + email);



        createDb();
        addEvents();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadDataCart();

        // Lấy ID của địa chỉ từ Intent
        addressIdFromIntent = getIntent().getIntExtra("ADDRESS_ID", -1);
        if (addressIdFromIntent == -1) {
            // Xử lí lỗi nếu không có ID
            Toast.makeText(this, "Không lấy được addressId từ intent", Toast.LENGTH_SHORT).show();
            displayAddress();
        } else {
            displayAddress();
        }


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

    private void loadDataCart() {
        // Lấy danh sách các sản phẩm đã chọn từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("selected_items", Context.MODE_PRIVATE);
        String serializedList = sharedPreferences.getString("selected_items_list", null);
        selectedItems = new ArrayList<>();

        if (serializedList != null) {
            try {
                byte[] bytes = Base64.decode(serializedList, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                selectedItems = (ArrayList<CartItem>) objectInputStream.readObject();
                // Log để kiểm tra dữ liệu đã được lấy ra chưa
                for (CartItem item : selectedItems) {
                    Log.d("CartItem", "Product Name: " + item.getProductName() + ", Quantity: " + item.getProductQuantity() + ", Price: " + item.getProductPrice());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("SharedPreferences", "Không có dữ liệu được lưu trong SharedPreferences.");
        }

        // Tính tổng số tiền từ danh sách các mục đã chọn
        double totalAmount = calculateTotalAmount(selectedItems);
        double shippingFee = 25000; //        cố định bằng 25000
        double couponShipping = 0;//        Hoặc bằng 1 hàm nào đó tính couponshipping
        double couponOrder = 0; //        Hoặc bằng 1 hàm nào đó tính couponorder
        double totalOrderValue = totalAmount + shippingFee -couponOrder - couponShipping;

        // Định dạng số
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

        // Gán giá trị định dạng vào TextView
        binding.txtTotalOrderValue.setText(numberFormat.format(totalOrderValue));
        binding.txtShippingfee.setText(numberFormat.format(shippingFee));
        binding.txtCouponShipping.setText(numberFormat.format(couponShipping));
        binding.txtDiscountOrder.setText(numberFormat.format(couponOrder));
        binding.txtTotalAmount.setText(numberFormat.format(totalAmount));


        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new PaymentItemAdapter(this, R.layout.payment_item, selectedItems);
        binding.lvPaymentItemList.setAdapter(adapter);

    }

    private void addEvents() {
        binding.addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Checkout.this, Checkout_AddressList.class);
//                startActivity(intent);
                Intent intent = new Intent(Checkout.this, Checkout_AddressList.class);
                intent.putExtra("key_email", email);
                startActivityForResult(intent, ADDRESS_SELECTION_REQUEST_CODE);
            }
        });
    }
    private double calculateTotalAmount(ArrayList<CartItem> selectedItems) {
        double totalAmount = 0.0;
        for (CartItem item : selectedItems) {
            totalAmount += item.getProductPrice() * item.getProductQuantity();
        }
        return totalAmount;
    }
    // Kiểm tra và hiển thị địa chỉ mặc định hoặc địa chỉ có addressId lớn nhất
//    private void displayAddress() {
//        Address defaultAddress = db.getDefaultAddress(customerId);
//        Address maxAddress = db.getMaxAddress(customerId);
//
//        if (defaultAddress != null) {
//            displayAddressOnUI(defaultAddress);
//        } else if (maxAddress != null) {
//            displayAddressOnUI(maxAddress);
//        } else {
//            // Không có địa chỉ nào, hiển thị nút "Thêm địa chỉ nhận hàng"
//            displayAddAddressButton();
//        }
//    }
    private void displayAddress() {
        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        Log.d("customer", "customer ở checkout: " + customer.getFullName());
        customerId = customer.getCustomerId();
        // Nếu chưa có addressIdFromIntent (tức là chưa chọn địa chỉ từ trang danh sách địa chỉ)
        if (addressIdFromIntent == -1) {
            Address defaultAddress = db.getDefaultAddress(customerId);
            Address maxAddress = db.getMaxAddress(customerId);

            if (defaultAddress != null) {
                displayAddressOnUI(defaultAddress);
            } else if (maxAddress != null) {
                displayAddressOnUI(maxAddress);
            } else {
                // Không có địa chỉ nào, hiển thị nút "Thêm địa chỉ nhận hàng"
                displayAddAddressButton();
            }
        } else {
            // Nếu đã có addressIdFromIntent, hiển thị địa chỉ tương ứng
            Address selectedAddress = db.getAddressById(addressIdFromIntent);
            if (selectedAddress != null) {
                displayAddressOnUI(selectedAddress);
            }
        }
    }


    // Hiển thị địa chỉ lên giao diện
    private void displayAddressOnUI(Address address) {
        binding.txtAddAddress.setVisibility(View.GONE);
        // Cập nhật TextViews với thông tin địa chỉ
        binding.txtReceiver.setText(address.getReceiverName() + " | " + address.getReceiverPhone());
        binding.txtAddress.setText(address.getAddressDetails() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince());
    }

    // Hiển thị nút "Thêm địa chỉ nhận hàng" trên giao diện
    private void displayAddAddressButton() {
        // Ẩn các TextView hiển thị địa chỉ
        binding.addressLayout.setVisibility(View.GONE);

        binding.txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, Checkout_Address.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_SELECTION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Nhận addressId được chọn từ trang danh sách địa chỉ
                int selectedAddressId = data.getIntExtra("SELECTED_ADDRESS_ID", -1);
                if (selectedAddressId != -1) {
                    // Cập nhật addressIdFromIntent với địa chỉ được chọn
                    addressIdFromIntent = selectedAddressId;
                    // Hiển thị địa chỉ đã được chọn
                    displayAddress();
                }
            }
        }
    }




}