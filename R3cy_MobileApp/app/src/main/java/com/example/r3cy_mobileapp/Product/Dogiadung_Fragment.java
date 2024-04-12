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

import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Product;
import com.example.interfaces.ProductInterface;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.FragmentDogiadungBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Dogiadung_Fragment extends Fragment {
     private FragmentDogiadungBinding binding;
//        private SQLiteDatabase database;
        private List<Product> products;
        Product product;

        R3cyDB db;
        private ProductAdapter adapter;
        RecyclerView rvProducts;

        String Category = "Đồ gia dụng";

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = FragmentDogiadungBinding.inflate(inflater, container, false);
            return binding.getRoot();


        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            addControls();
            getCategory();
            createDb();
            loadData();

        }

        private void getCategory() {
//            Category = getString(1, "Đồ gia dụng");
        }

        private void addControls() {
            rvProducts = binding.rvProducts;
            products = new ArrayList<>();
            adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, products);
            rvProducts.setAdapter(adapter);

        }

        private void createDb() {
            db = new R3cyDB(getContext());
            db.createSampleProduct();
        }



        private void loadData() {

            binding.rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));

            products = new ArrayList<>();
            Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);
            while (cursor.moveToNext()) {
                products.add(new Product(
                        cursor.getInt(0), //ProductID
                        cursor.getString(1), //ProductName
                        cursor.getDouble(2), // ProductPrice
                        cursor.getString(3), //ProductDescription
                        cursor.getBlob(4), //ProductThumb
                        cursor.getInt(5), //Hot
                        cursor.getString(6), //Category
                        cursor.getInt(7), //Inventory
                        cursor.getDouble(8), //ProductRate
                        cursor.getDouble(9), //SalePrice
                        cursor.getInt(10), //SoldQuantity
                        cursor.getString(11), //CreatedDate
                        cursor.getInt(12), //Status
                        cursor.getBlob(13), //img1
                        cursor.getBlob(14), //img2
                        cursor.getBlob(15) //img3
                ));
                Log.d("ProductInfo", "Product ID: " + product.getProductID());
                Log.d("ProductInfo", "Product Name: " + product.getProductName());
            }
            cursor.close();
            Log.d("ProductInfo", "Number of products retrieved: " + products.size());


//                String ProductName = cursor.getString(1);
//                String ProductDescription = cursor.getString(3);
//                String Category = cursor.getString(6);
//                Double SalePrice = cursor.getDouble(9);
//                Double ProductRate = cursor.getDouble(8);



            adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, products);
            binding.rvProducts.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }}