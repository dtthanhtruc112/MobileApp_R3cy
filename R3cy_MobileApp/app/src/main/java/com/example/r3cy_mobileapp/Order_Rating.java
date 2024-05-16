package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.OrderAdapter;
import com.example.adapter.OrderDetailsAdapter;
import com.example.adapter.OrderRatingAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Feedback;
import com.example.models.Order;
import com.example.models.order_rating;
import com.example.r3cy_mobileapp.databinding.ActivityOrderRatingBinding;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Order_Rating extends AppCompatActivity {
    ActivityOrderRatingBinding binding;
    R3cyDB dbR3cy;
    OrderRatingAdapter adapter;
    String email;
    Context context;
    int OrderId;
    List<order_rating> orders;
//    List<Order> orders;
    List<Feedback> feedbacks;
    order_rating orderrating;
    Order order;
    Feedback feedback;
    String orderJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String orderJson = sharedPreferences.getString("ORDER_JSON", "");
//        String email = sharedPreferences.getString("EMAIL", null);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        OrderId = sharedPreferences.getInt("ORDER_ID", -1); // -1 là giá trị mặc định nếu không tìm thấy orderId


        // Convert JSON back to Order object
//        Gson gson = new Gson();
//        Order clickedOrder = gson.fromJson(orderJson, Order.class);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        int orderId = sharedPreferences.getInt("ORDER_ID", -1);
//        String email = sharedPreferences.getString("EMAIL", "");

//        email = getIntent().getStringExtra("key_email");
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
        addEvents2();
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
        dbR3cy.createSampleDataFeedback();
    }
    public void onResume() {
        super.onResume();
        loadData();
    }
    public List<order_rating> getOrder() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null && bundle.containsKey("ORDER")) {
            order = (Order) bundle.getSerializable("ORDER");
        }


//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("Package");
//        if (bundle != null && bundle.containsKey("ORDER")) {
//            order = (Order) bundle.getSerializable("ORDER");
//        }
//        if (intent != null && intent.hasExtra("ORDER_ID")) {
//            order = (Order) intent.getIntExtra("ORDER_ID", OrderId);
//        }
//        int customerId = dbR3cy.getCustomerIdFromCustomer(email);
//            int orderId = dbR3cy.getOrderByID(OrderId);
//            Log.d("SharedPreferences", "Email ở Fragment: " + OrderId);

//        String orderStatus = dbR3cy.getOrderStatus("Chờ lấy hàng");
            List<order_rating> orders = new ArrayList<>();
            SQLiteDatabase db = dbR3cy.getReadableDatabase();
            Cursor cursor = null;

//

//            Gson gson = new Gson();
//            Order clickedOrder = gson.fromJson(orderJson, Order.class);


            try {
                String query = "SELECT " +
                        "o." + R3cyDB.ORDER_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_ORDER_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + ", " +
                        "ol." + R3cyDB.ORDER_SALE_PRICE + ", " +
                        "ol." + R3cyDB.QUANTITY + ", " +
                        "o." + R3cyDB.ORDER_CUSTOMER_ID + ", " +
                        "o." + R3cyDB.ORDER_STATUS + ", " +
                        "IFNULL(f." + R3cyDB.FEEDBACK_CONTENT + ", '') AS " + R3cyDB.FEEDBACK_CONTENT + ", " +
                        "IFNULL(f." + R3cyDB.FEEDBACK_RATING + ", '') AS " + R3cyDB.FEEDBACK_RATING + ", " +
                        "IFNULL(f." + R3cyDB.FEEDBACK_ID + ", '') AS " + R3cyDB.FEEDBACK_ID + ", " +
                        "p." + R3cyDB.PRODUCT_THUMB + ", " +
                        "p." + R3cyDB.PRODUCT_NAME + ", " +
                        "p." + R3cyDB.PRODUCT_PRICE + ", " +
                        "p." + R3cyDB.PRODUCT_ID +
                        " FROM " + R3cyDB.TBl_ORDER + " o " + " " +
                        " INNER JOIN " + R3cyDB.TBl_ORDER_LINE + " ol" +
                        " ON o." + R3cyDB.ORDER_ID + " = ol." + R3cyDB.ORDER_LINE_ORDER_ID + " " +
                        " LEFT JOIN " + R3cyDB.TBl_FEEDBACK + " f" +
                        " ON ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + " = f." + R3cyDB.PRODUCT_ID + " " +
                        " INNER JOIN " + R3cyDB.TBl_PRODUCT + " p" +
                        " ON ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + " = p." + R3cyDB.PRODUCT_ID +
                        " WHERE o. "
                        + R3cyDB.ORDER_ID + " LIKE '%" + OrderId + "%'";

                cursor = db.rawQuery(query, null);


                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") int OrderId = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_ID));
                        @SuppressLint("Range") int OrderLineID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_ID));
                        @SuppressLint("Range") int OrderLineProductID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_PRODUCT_ID));
                        @SuppressLint("Range") byte[] ProductImg = cursor.getBlob(cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB));
                        @SuppressLint("Range") String ProductName = cursor.getString(cursor.getColumnIndex(R3cyDB.PRODUCT_NAME));
                        @SuppressLint("Range") String Quantity = cursor.getString(cursor.getColumnIndex(R3cyDB.QUANTITY));
                        @SuppressLint("Range") double ProductPrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE));
                        @SuppressLint("Range") String FeedbackContent = cursor.getString(cursor.getColumnIndex(R3cyDB.FEEDBACK_CONTENT));
                        @SuppressLint("Range") String FeedbackRating = cursor.getString(cursor.getColumnIndex(R3cyDB.FEEDBACK_RATING));

                        @SuppressLint("Range") int FeedbackID = cursor.getInt(cursor.getColumnIndex(R3cyDB.FEEDBACK_ID));
//                    @SuppressLint("Range") double OrderSalePrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.ORDER_SALE_PRICE));
//                    @SuppressLint("Range") int OrderCustomerID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_CUSTOMER_ID));
//                    @SuppressLint("Range") double TotalOrderValue = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_ORDER_VALUE));
//                    @SuppressLint("Range") double TotalAmount = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_AMOUNT));
//                    @SuppressLint("Range") String OrderDate = cursor.getString(cursor.getColumnIndex(R3cyDB.ORDER_DATE));
                        @SuppressLint("Range") String OrderStatus = cursor.getString(cursor.getColumnIndex(R3cyDB.ORDER_STATUS));
                        if (FeedbackRating == null) {
                            FeedbackRating = "";
                        }
                        if (FeedbackContent == null) {
                            FeedbackContent = "";
                        }


                        order_rating order = new order_rating(OrderId, OrderLineID, OrderLineProductID, ProductImg, ProductName, Quantity, ProductPrice, FeedbackContent, FeedbackRating, FeedbackID, OrderStatus);
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
        List<order_rating> Order;
        orders = (List<order_rating>) getOrder();
        adapter = new OrderRatingAdapter(this, R.layout.item_orderrating, orders);
        binding.lvOrderDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void btnSaveFeedbackClicked(String email, List<order_rating> feedbackDataList) {
        SQLiteDatabase db = dbR3cy.getWritableDatabase();

        try {
            db.beginTransaction();

            for (order_rating feedbackData : feedbackDataList) {
                int productId = feedbackData.getProductID();
                String content = feedbackData.getFeedbackcontent();
                String rating = feedbackData.getFeedbackrating();


                ContentValues values = new ContentValues();
                values.put(R3cyDB.FEEDBACK_PRODUCT_ID, productId);
                values.put(R3cyDB.FEEDBACK_CONTENT, content);
                values.put(R3cyDB.FEEDBACK_RATING, rating);


                long newRowId = db.insert(R3cyDB.TBl_FEEDBACK, null, values);


                if (newRowId == -1) {
                    Log.e("INSERT_FEEDBACK", "Error inserting feedback");
                    Toast.makeText(getApplicationContext(), "Lỗi! Đánh giá chưa được gửi.", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getApplicationContext(), "Gửi thành công.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), User_account_manageOrder.class);
////            intent.putExtra("EMAIL", email);
//            startActivity(intent);
//                    finish();

                }

            }
            updateOrderStatus(OrderId, "Đã đánh giá");


            Toast.makeText(getApplicationContext(), "Gửi thành công.", Toast.LENGTH_SHORT).show();
            db.setTransactionSuccessful();
            finish();


        } catch (Exception e) {
            Log.e("INSERT_FEEDBACK", "Error inserting feedback: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
//            finish();
//            if (db != null) {
//                db.endTransaction();
//                db.close();
//            }
//            finish();

        }




//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        email = sharedPreferences.getString("EMAIL", "");
//        OrderId = sharedPreferences.getInt("ORDER_ID", -1);

        // After inserting feedback, start the next activity

    }
    private void updateOrderStatus(int orderId, String status) {
        SQLiteDatabase db = dbR3cy.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(R3cyDB.ORDER_STATUS, status);

            int rowsAffected = db.update(R3cyDB.TBl_ORDER, values, R3cyDB.ORDER_ID + "=?", new String[]{String.valueOf(orderId)});
            if (rowsAffected == 1) {
                Log.d("UPDATE_ORDER_STATUS", "Order status updated successfully");
            } else {
                Log.e("UPDATE_ORDER_STATUS", "Failed to update order status");
            }
        } catch (Exception e) {
            Log.e("UPDATE_ORDER_STATUS", "Error updating order status: " + e.getMessage());
        } finally {
//            db.close();
        }
    }

    private void addEvents2 () {
        binding.btnDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView listView = findViewById(R.id.lvOrderDetails);

                List<order_rating> feedbackDataList = new ArrayList<>();
                for (int i = 0; i < listView.getCount(); i++) {
                    View view = listView.getChildAt(i);
                    OrderRatingAdapter.ViewHolder holder = (OrderRatingAdapter.ViewHolder) view.getTag();
                    String feedbackContent = holder.txtFeedbackContent.getText().toString();
                    String feedbackRating = holder.txtFeedbackRating.getText().toString();
                    order_rating order = (order_rating) adapter.getItem(i);
                    order_rating feedbackData = new order_rating(OrderId, order.getOrderID(), order.getProductID(), order.getProductImg(), order.getProductname(), order.getProductquantity(), order.getProductprice(),feedbackContent, String.valueOf(feedbackRating),  order.getFeedbackID(), order.getOrderStatus());
                    feedbackDataList.add(feedbackData);
                }


                // Call btnSaveFeedbackClicked with email and feedbackDataList
                btnSaveFeedbackClicked(email, feedbackDataList);
            }
        });
    }

}