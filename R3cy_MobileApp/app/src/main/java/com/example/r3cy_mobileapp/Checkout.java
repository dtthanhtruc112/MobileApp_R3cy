package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.adapter.CartAdapter;
import com.example.adapter.PaymentItemAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.CartItem;
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

    int customerId = 1;
//    Thay customer lấy sau khi login ra


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDb();
        loadData();
        displayAddress(); // Hiển thị địa chỉ
        addEvents();
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

    private void loadData() {
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
                Intent intent = new Intent(Checkout.this, Checkout_AddressList.class);
                startActivity(intent);
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
    private void displayAddress() {
        Address defaultAddress = db.getDefaultAddress(customerId);
        Address maxAddress = db.getMaxAddress(customerId);

        if (defaultAddress != null) {
            // Hiển thị địa chỉ mặc định
            displayAddressOnUI(defaultAddress);
        } else if (maxAddress != null) {
            // Hiển thị địa chỉ có addressId lớn nhất
            displayAddressOnUI(maxAddress);
        } else {
            // Không có địa chỉ nào, hiển thị nút "Thêm địa chỉ nhận hàng"
            displayAddAddressButton();
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
//        binding.txtReceiver.setVisibility(View.GONE);
//        binding.txtAddress.setVisibility(View.GONE);


        binding.txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, Checkout_Address.class);
                startActivity(intent);
            }
        });

    }

}