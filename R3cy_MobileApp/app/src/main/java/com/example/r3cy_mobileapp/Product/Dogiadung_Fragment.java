package com.example.r3cy_mobileapp.Product;

import static com.example.databases.R3cyDB.CATEGORY;
import static com.example.databases.R3cyDB.CREATED_DATE;
import static com.example.databases.R3cyDB.HOT;
import static com.example.databases.R3cyDB.INVENTORY;
import static com.example.databases.R3cyDB.PRODUCT_DESCRIPTION;
import static com.example.databases.R3cyDB.PRODUCT_ID;
import static com.example.databases.R3cyDB.PRODUCT_IMG1;
import static com.example.databases.R3cyDB.PRODUCT_IMG2;
import static com.example.databases.R3cyDB.PRODUCT_IMG3;
import static com.example.databases.R3cyDB.PRODUCT_NAME;
import static com.example.databases.R3cyDB.PRODUCT_PRICE;
import static com.example.databases.R3cyDB.PRODUCT_RATE;
import static com.example.databases.R3cyDB.PRODUCT_THUMB;
import static com.example.databases.R3cyDB.SALE_PRICE;
import static com.example.databases.R3cyDB.SOLD_QUANTITY;
import static com.example.databases.R3cyDB.STATUS;
import static com.example.databases.R3cyDB.TBl_PRODUCT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.CartAdapter;
import com.example.adapter.ProductAdapter;
import com.example.dao.ProductDao;
import com.example.dao.ProductDao2;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Product;
import com.example.interfaces.ProductInterface;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.FragmentDogiadungBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Dogiadung_Fragment extends Fragment {
     private FragmentDogiadungBinding binding;
        ArrayList<Product> products;
        Product product;
        R3cyDB db;
        ProductDao2 productDao2;
        ProductAdapter adapter;
        RecyclerView rvProducts;
        Intent intent;
        SQLiteDatabase database;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = FragmentDogiadungBinding.inflate(inflater, container, false);
            return binding.getRoot();


        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

//            getCategory();
            createDb();
            addEvents();

        }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

//    private void getCategory() {
////            Category = getString(1, "Đồ gia dụng");
//        }

//        private void addControls() {
//            RecyclerView rvProducts = binding.rvProducts;
//            rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));
//            products = new ArrayList<>();
//            adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, products);
//            rvProducts.setAdapter(adapter);
//
//        }



    private void createDb() {
        db = new R3cyDB(getContext());

        if (db != null) {
            Log.d("Dogiadung", "Database created successfully");
        } else {
            Log.e("Dogiadung", "Failed to create database");
        }
        db.createSampleProduct();
        // Khởi tạo productDao sau khi database được khởi tạo xong
        productDao2 = new ProductDao2(db);
    }



        private void loadData() {

            // Lấy dữ liệu từ ProductDao
            products = (ArrayList<Product>) productDao2.getProducts();
            Log.i("ProductSize", "Number of items retrieved: " + products.size());

            // Khởi tạo adapter và thiết lập cho ListView
            binding.rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));
            adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, products);
            binding.rvProducts.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }

    private void addEvents() {

        binding.rvProducts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Xử lý sự kiện chạm vào item trong RecyclerView
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
                    int position = rv.getChildAdapterPosition(childView);
                    ProductInterface productInterface = (ProductInterface) requireActivity();
                    Product selectedProduct = (Product) adapter.getItem(position);

                    // Tạo Intent và gửi thông tin sản phẩm sang Product_Detail Activity
                    Intent intent = new Intent(requireContext(), Product_Detail.class);
                    intent.putExtra("ProductID", selectedProduct.getProductID());
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });
    }}