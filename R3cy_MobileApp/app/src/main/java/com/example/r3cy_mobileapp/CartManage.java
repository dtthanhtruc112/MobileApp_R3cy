package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.adapter.CartAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.r3cy_mobileapp.databinding.ActivityCartManageBinding;

import java.util.ArrayList;
import java.util.List;

public class CartManage extends AppCompatActivity {

    ActivityCartManageBinding binding;

    R3cyDB db;
    ProductDao productDao;
    CartAdapter adapter;

    ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        createDb();
        productDao = new ProductDao(db);
        addEvents();

    }



    private void addEvents() {
        binding.chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Gọi hàm addAllCheckBoxes() khi trạng thái của chk_All thay đổi
                addAllCheckBoxes(isChecked);
            }
        });



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
        db.createSampleDataCart();
        db.createSampleProduct();
        db.createSampleDataCustomer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

    private void loadData() {
        // Lấy dữ liệu từ ProductDao
        cartItems = (ArrayList<CartItem>) productDao.getCartItemsForCustomer(1);
// In ra kích thước của danh sách cartItems
        Log.i("CartItemSize", "Number of items retrieved: " + cartItems.size());
        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new CartAdapter(this, R.layout.cartitem, cartItems);
        binding.lvCartList.setAdapter(adapter);
    }

}