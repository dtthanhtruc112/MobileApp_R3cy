package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.adapter.CartAdapter;
import com.example.adapter.PaymentItemAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        loadData();
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
        String formattedTotalOrderValue = numberFormat.format(totalOrderValue);
        String formattedCouponOrder = numberFormat.format(couponOrder);
        String formattedCouponShipping = numberFormat.format(couponShipping);
        String formattedShippingFee = numberFormat.format(shippingFee);
        String formattedTotalAmount = numberFormat.format(totalAmount);

// Gán giá trị định dạng vào TextView
        binding.txtTotalOrderValue.setText(formattedTotalOrderValue);
        binding.txtShippingfee.setText(formattedShippingFee);
        binding.txtCouponShipping.setText(formattedCouponShipping);
        binding.txtDiscountOrder.setText(formattedCouponOrder);
        binding.txtTotalAmount.setText(formattedTotalAmount);



        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new PaymentItemAdapter(this, R.layout.payment_item, selectedItems);
        binding.lvPaymentItemList.setAdapter(adapter);

    }
    // Phương thức để tính tổng số tiền từ danh sách các mục đã chọn
    private double calculateTotalAmount(ArrayList<CartItem> selectedItems) {
        double totalAmount = 0.0;
        for (CartItem item : selectedItems) {
            totalAmount += item.getProductPrice() * item.getProductQuantity();
        }
        return totalAmount;
    }
}