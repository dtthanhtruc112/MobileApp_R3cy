package com.example.r3cy_mobileapp;

import static com.example.databases.R3cyDB.ORDER_LINE_ORDER_ID;
import static com.example.databases.R3cyDB.ORDER_LINE_PRODUCT_ID;
import static com.example.databases.R3cyDB.ORDER_SALE_PRICE;
import static com.example.databases.R3cyDB.QUANTITY;
import static com.example.databases.R3cyDB.TBl_ORDER_LINE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.adapter.OrderAdapter;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Coupon;
import com.example.models.Customer;
import com.example.models.Order;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountOrderRatingBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class UserAccount_OrderRating extends AppCompatActivity {
    ActivityUserAccountOrderRatingBinding binding;
    Order order;
    R3cyDB db;
    BottomNavigationView navigationView;
    String email;
    Customer customer;
    int customerId;
    String orderJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountOrderRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//        Intent intent =  getIntent();
//        Bundle bundle = intent.getBundleExtra("Package");
//        email = bundle.getString("key_email");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        orderJson = sharedPreferences.getString("ORDER_JSON", "");
        email = sharedPreferences.getString("EMAIL", null);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        int orderId = sharedPreferences.getInt("ORDER_ID", -1);
//        String email = sharedPreferences.getString("EMAIL", "");

//        email = getIntent().getStringExtra("key_email");
        Log.d("Intent", "Email ở orderrating: " + email);

        db = new R3cyDB(this);
        db.createSampleDataFeedback();
//        customer = db.getCustomerByEmail1(email);
//        customerId = db.getCustomerIdFromCustomer(email);

        addEvents();
        addEvents2();
    }


    private void btnSaveFeedbackClicked() {
//        customerId = db.getCustomerIdFromCustomer(email);

        String feedbackContent = binding.txtdanhgiachitiet.getText().toString();
        String productName = binding.txtproductname.getText().toString();
        String feedbackRating = binding.txtproductRating.getText().toString();

        if (feedbackRating.isEmpty() || feedbackContent.isEmpty()) {
            Toast.makeText(UserAccount_OrderRating.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // Get product ID from product name
            int productId = db.getProductIdFromProductName(productName);
//            int customerId = db.getCustomerIdFromCustomer(email);

            if (productId !=-1) {

                insertFeedback(productId, feedbackRating, feedbackContent);

            } else {
                Toast.makeText(UserAccount_OrderRating.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();

            }
            }
        }
//    }


    private void insertFeedback(int productId, String content, String rating) {
        // Khởi tạo một đối tượng ContentValues để lưu các cặp key-value
        ContentValues values = new ContentValues();
        values.put(R3cyDB.FEEDBACK_PRODUCT_ID, productId);
        values.put(R3cyDB.FEEDBACK_CONTENT, content);
        values.put(R3cyDB.FEEDBACK_RATING, rating);
//        values.put(R3cyDB.FEEDBACK_CUSTOMER_ID, customerId);

        // Thực hiện lưu dữ liệu vào cơ sở dữ liệu
        long newRowId = db.insertDataFeedback( productId, content, rating);

        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Gửi đánh giá thành công.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), User_account_manageOrder.class);
            intent.putExtra("key_email", email);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Lỗi! Đánh giá chưa được gửi.", Toast.LENGTH_SHORT).show();
        }
    }


        private void addEvents () {

            navigationView = findViewById(R.id.mn_home);
            navigationView.setSelectedItemId(R.id.item_account);


            navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.item_product) {
                        Intent intent1 = new Intent(getApplicationContext(), Product_List.class);
                        intent1.putExtra("key_email", email);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (item.getItemId() == R.id.item_blog) {
                        Intent intent2 = new Intent(getApplicationContext(), BlogDetail.class);
                        intent2.putExtra("key_email", email);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                    } else if (item.getItemId() == R.id.item_store) {
                        Intent intent3 = new Intent(getApplicationContext(), AboutUs.class);
                        intent3.putExtra("key_email", email);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (item.getItemId() == R.id.item_account) {
                        Intent intent4 = new Intent(getApplicationContext(), UserAccount_Main.class);
                        intent4.putExtra("key_email", email);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (item.getItemId() == R.id.item_home) {
                        Intent intent5 = new Intent(getApplicationContext(), TrangChu.class);
                        intent5.putExtra("key_email", email);
                        intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent5);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    return false;
                }


            });
        }

        protected void onResume () {
            super.onResume();
            getData();
        }

        private void getData () {
            try {
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
            } catch (Exception e) {
                e.printStackTrace();
            }
//            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            String orderJson = sharedPreferences.getString("ORDER_JSON", null);
//            String email = sharedPreferences.getString("EMAIL", null);
//            Intent intent = getIntent();
//            Bundle bundle = intent.getBundleExtra("Package");
//            if (bundle != null && bundle.containsKey("ORDER")) {
//                order = (Order) bundle.getSerializable("ORDER");
//            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            int orderId = sharedPreferences.getInt("ORDER_ID", -1);
//            String email = sharedPreferences.getString("EMAIL", "");
//
//            // Kiểm tra orderId và email có tồn tại
//            if (orderId != -1 ) {
//            if (orderJson != null && email != null) {
//                Gson gson = new Gson();
//                Order clickedOrder = gson.fromJson(orderJson, Order.class);
//                if (order != null) {
//                    binding.txtproductname.setText(order.getProductName());
//                    binding.productcount.setText(String.valueOf(order.getQuantity()));
//                    binding.productprice.setText(String.valueOf(order.getProductPrice()));
//                    byte[] imageData = order.getProductImg();
//                    Bitmap bitmap = decodeSampledBitmapFromByteArray(imageData, 100, 100);
//                    binding.productimg.setImageBitmap(bitmap);
//                }
//            }
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String orderJson = sharedPreferences.getString("ORDER_JSON", null);
            String email = sharedPreferences.getString("EMAIL", null);

            if (orderJson != null) {
                Gson gson = new Gson();
                Order clickedOrder = gson.fromJson(orderJson, Order.class);
                if (clickedOrder != null) {
                    binding.txtproductname.setText(clickedOrder.getProductName());
                    binding.productcount.setText(String.valueOf(clickedOrder.getQuantity()));
                    binding.productprice.setText(String.valueOf(clickedOrder.getProductPrice()));
                    byte[] imageData = clickedOrder.getProductImg();
                    Bitmap bitmap = decodeSampledBitmapFromByteArray(imageData, 150, 150);
                    binding.productimg.setImageBitmap(bitmap);
                }
            }
        }
        public static Bitmap decodeSampledBitmapFromByteArray ( byte[] byteArray, int reqWidth,
        int reqHeight){
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        }

        public static int calculateInSampleSize (
                BitmapFactory.Options options,int reqWidth, int reqHeight){
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 3;
                final int halfWidth = width / 3;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }


        private void addEvents2 () {
            binding.btnSaveRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSaveFeedbackClicked();
                }
            });
        }
    }

