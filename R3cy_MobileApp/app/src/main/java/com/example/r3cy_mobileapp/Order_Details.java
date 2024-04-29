package com.example.r3cy_mobileapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.adapter.OrderAdapter;
import com.example.adapter.OrderDetailsAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Order;
import com.example.r3cy_mobileapp.databinding.ActivityOrderDetailsBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Order_Details extends AppCompatActivity {
    ActivityOrderDetailsBinding binding;
    R3cyDB dbR3cy;
    OrderDetailsAdapter adapter;
    String email;
    Context context;
    int OrderId;
    List<Order> orders;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("Package");
//        email = bundle.getString("key_email");
////        OrderId = bundle.getInt("ORDER_ID");
//        OrderId = getIntent().getIntExtra("ORDER_ID", OrderId);
//        Log.d("Intent", "OrderId: " + OrderId);
//
////        email = getIntent().getStringExtra("key_email");
//        Log.d("Intent", "Email ở orderrating: " + email);
////        email = getIntent().getStringExtra("key_email");
//        Log.d("SharedPreferences", "Email : " + email);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        OrderId = sharedPreferences.getInt("ORDER_ID", -1); // -1 là giá trị mặc định nếu không tìm thấy orderId

        Log.d("OrderDetails", "Email: " + email);
        Log.d("OrderDetails", "OrderId: " + OrderId);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        createDb();
        addEvents();
    }
    private void addEvents () {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createDb() {
        dbR3cy = new R3cyDB(this);
        dbR3cy.createSampleDataOrder();
        dbR3cy.createSampleDataOrderLine();
        dbR3cy.createSampleProduct();
    }
    public void onResume() {
        super.onResume();
        loadData();
    }
    public List<Order> getOrder() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null && bundle.containsKey("ORDER")) {
            order = (Order) bundle.getSerializable("ORDER");
        }
//        if (intent != null && intent.hasExtra("ORDER_ID")) {
//            order = (Order) intent.getIntExtra("ORDER_ID", OrderId);
//        }
//        int customerId = dbR3cy.getCustomerIdFromCustomer(email);
        int orderId = dbR3cy.getOrderByID(OrderId);
        Log.d("SharedPreferences", "Email ở Fragment: " + email);

//        String orderStatus = dbR3cy.getOrderStatus("Chờ lấy hàng");
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = dbR3cy.getReadableDatabase();
        Cursor cursor = null;
//

        try {
            String query = "SELECT " +
                    "o." + R3cyDB.ORDER_ID + ", " +
                    "ol." + R3cyDB.ORDER_LINE_ID + ", " +
                    "ol." + R3cyDB.ORDER_LINE_ORDER_ID + ", " +
                    "ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + ", " +
                    "ol." + R3cyDB.ORDER_SALE_PRICE + ", " +
                    "ol." + R3cyDB.QUANTITY + ", " +
                    "o." + R3cyDB.ORDER_CUSTOMER_ID + ", " +
                    "o." + R3cyDB.TOTAL_ORDER_VALUE + ", " +
                    "o." + R3cyDB.ORDER_STATUS + ", " +
                    "o." + R3cyDB.TOTAL_AMOUNT + ", " +
                    "p." + R3cyDB.PRODUCT_THUMB + ", " +
                    "p." + R3cyDB.PRODUCT_NAME + ", " +
                    "p." + R3cyDB.PRODUCT_PRICE + ", " +
                    "p." + R3cyDB.PRODUCT_ID +
                    " FROM " + R3cyDB.TBl_ORDER + " o " + " " +
                    " INNER JOIN " + R3cyDB.TBl_ORDER_LINE + " ol" +
                    " ON o." + R3cyDB.ORDER_ID + " = ol." + R3cyDB.ORDER_LINE_ORDER_ID + " " +
                    " INNER JOIN " + R3cyDB.TBl_PRODUCT + " p" +
                    " ON ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + " = p." + R3cyDB.PRODUCT_ID +
                    " WHERE o. "
                    + R3cyDB.ORDER_ID + " LIKE '%" + orderId + "%'";

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int OrderId = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_ID));
                    @SuppressLint("Range") int OrderLineID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_ID));
                    @SuppressLint("Range") int OrderLineProductID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_PRODUCT_ID));
                    @SuppressLint("Range") double OrderSalePrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.ORDER_SALE_PRICE));
                    @SuppressLint("Range") double ProductPrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE));
                    @SuppressLint("Range") String Quantity = cursor.getString(cursor.getColumnIndex(R3cyDB.QUANTITY));
                    @SuppressLint("Range") int OrderCustomerID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_CUSTOMER_ID));
                    @SuppressLint("Range") double TotalOrderValue = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_ORDER_VALUE));
                    @SuppressLint("Range") String OrderStatus = cursor.getString(cursor.getColumnIndex(R3cyDB.ORDER_STATUS));
                    @SuppressLint("Range") double TotalAmount = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_AMOUNT));
                    @SuppressLint("Range") byte[] ProductImg = cursor.getBlob(cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB));
                    @SuppressLint("Range") String ProductName = cursor.getString(cursor.getColumnIndex(R3cyDB.PRODUCT_NAME));

                    Order order = new Order(OrderId, OrderLineID, OrderLineProductID, OrderSalePrice, Quantity, OrderCustomerID, ProductPrice, TotalOrderValue, OrderStatus, TotalAmount, ProductImg, ProductName);
                    orders.add(order);
                } while (cursor.moveToNext());
            } else {
                Log.d("DATABASE", "No data retrieved.");
            }
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error retrieving data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return orders;
    }

    private void loadData() {
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Order> Order;
        orders = (List<Order>) getOrder();
        adapter = new OrderDetailsAdapter(this, R.layout.item_quanlydonhang2, orders);
        binding.lvOrderDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}