package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CartAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.databinding.ActivityCartManageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CartManage extends AppCompatActivity {

    ActivityCartManageBinding binding;

    R3cyDB db;
    ProductDao productDao;
    CartAdapter adapter;
    String email;
    Customer customer;

    ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở cartmanage: " + email);

        createDb();
        addEvents();
    }


    private void addAllCheckBoxes(boolean isChecked) {
        // Duyệt qua từng item trong ListView
        for (int i = 0; i < binding.lvCartList.getChildCount(); i++) {
            View itemView = binding.lvCartList.getChildAt(i);
            CheckBox chkProductBuy = itemView.findViewById(R.id.chk_ProductBuy);
            // Đặt trạng thái của checkbox trong mỗi item là giống với trạng thái của chk_All
            chkProductBuy.setChecked(isChecked);
        }
    }

    private void createDb() {
        db = new R3cyDB(this);

        if (db != null) {
            Log.d("CartManage", "Database created successfully");
        } else {
            Log.e("CartManage", "Failed to create database");
        }
        db.createSampleProduct();
        db.createSampleDataCustomer();
        db.createSampleDataCart();
        // Khởi tạo productDao sau khi database được khởi tạo xong
        productDao = new ProductDao(db);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

    private void loadData() {
        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);

        // Lấy dữ liệu từ ProductDao
        cartItems = (ArrayList<CartItem>) productDao.getCartItemsForCustomer(customer.getCustomerId());
        Log.i("CartItemSize", "Number of items retrieved: " + cartItems.size());
        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new CartAdapter(this, R.layout.cartitem, cartItems);
        binding.lvCartList.setAdapter(adapter);

        // Set total amount TextView in adapter
        adapter.setTxtTotalAmount(binding.txtTotalAmount);

        // Thiết lập sự kiện lắng nghe khi số lượng giảm
        adapter.setOnQuantityDecreaseListener(new CartAdapter.OnQuantityDecreaseListener() {
            @Override
            public void onDecrease(int position) {

                handleQuantityDecrease(position);
                // Set total amount TextView in adapter
                adapter.setTxtTotalAmount(binding.txtTotalAmount);
            }
        });

        adapter.setOnQuantityIncreaseListener(new CartAdapter.OnQuantityIncreaseListener() {
            @Override
            public void onIncrease(int position) {

                handleQuantityIncrease(position);
                // Set total amount TextView in adapter
                adapter.setTxtTotalAmount(binding.txtTotalAmount);
            }
        });

    }


    // Trong phương thức handleQuantityIncrease và handleQuantityDecrease của CartManage

    private void handleQuantityIncrease(int position) {
        CartItem cartItem = cartItems.get(position);
        int currentQuantity = cartItem.getProductQuantity();
        int newQuantity = currentQuantity + 1;
        cartItem.setProductQuantity(newQuantity);

        adapter.notifyDataSetChanged();

        // Cập nhật số lượng mới vào cơ sở dữ liệu thông qua ProductDao
        if (productDao != null) {
            boolean isUpdated = productDao.updateQuantity(cartItem.getLineId(), newQuantity);
            if (isUpdated) {
                Log.d("CartManage", "Update successful");
            } else {
                Log.e("CartManage", "Failed to update quantity in database.");
            }
        } else {
            Log.e("CartManage", "ProductDao is null. Cannot update quantity.");
        }
    }

    private void handleQuantityDecrease(int position) {
        CartItem cartItem = cartItems.get(position);
        int currentQuantity = cartItem.getProductQuantity();
        if (currentQuantity > 1) {
            int newQuantity = currentQuantity - 1;
            cartItem.setProductQuantity(newQuantity);
            adapter.notifyDataSetChanged();
            if (productDao != null) {
                boolean isUpdated = productDao.updateQuantity(cartItem.getLineId(), newQuantity);
                if (isUpdated) {
                    Log.d("CartManage", "Update successful");
                } else {
                    Log.e("CartManage", "Failed to update quantity in database.");
                }
            } else {
                Log.e("CartManage", "ProductDao is null. Cannot update quantity.");
            }
        }
    }

    // Phương thức để xóa một mục từ cơ sở dữ liệu

    public void deleteCartItem(int lineId) {
        // Thực hiện xóa mục từ cơ sở dữ liệu sử dụng ProductDao hoặc phương thức xóa phù hợp
        if (productDao != null) {
            boolean success = productDao.deleteCartItem(lineId);
            if (success) {
                // Nếu xóa thành công, cập nhật lại dữ liệu và hiển thị
                loadData();
            } else {
                // Xử lý lỗi khi xóa không thành công
                Log.e("CartManage", "Failed to delete item from database.");
            }
        } else {
            Log.e("CartManage", "ProductDao is null. Cannot delete item.");
        }
    }

    private void addEvents() {
        binding.chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Gọi hàm addAllCheckBoxes() khi trạng thái của chk_All thay đổi
                addAllCheckBoxes(isChecked);
            }
        });
        binding.btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một danh sách mới để chứa các sản phẩm được chọn
                ArrayList<CartItem> selectedItems = new ArrayList<>();

                // Duyệt qua danh sách sản phẩm và kiểm tra xem sản phẩm nào được chọn
                for (CartItem item : cartItems) {
                    if (item.isSelected()) {
                        selectedItems.add(item); // Thêm sản phẩm đã được chọn vào danh sách
                    }
                }

                // Kiểm tra xem có sản phẩm được chọn không
                if (selectedItems.isEmpty()) {
                    // Nếu không có sản phẩm nào được chọn, hiển thị Toast thông báo
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu có sản phẩm được chọn, lưu danh sách các sản phẩm đã chọn vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("selected_items", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(selectedItems);
                        String serializedList = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        editor.putString("selected_items_list", serializedList);
                        editor.apply();

                        // Log để kiểm tra dữ liệu đã được lưu xuống SharedPreferences
                        Log.d("SharedPreferences", "Dữ liệu đã được lưu xuống SharedPreferences.");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Chuyển sang trang Checkout
                    Intent intent = new Intent(CartManage.this, Checkout.class);
                    intent.putExtra("key_email", email);
                    startActivity(intent);
                }
            }
        });

    }
}
