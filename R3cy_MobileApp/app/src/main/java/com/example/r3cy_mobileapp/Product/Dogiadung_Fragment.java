package com.example.r3cy_mobileapp.Product;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.util.ArrayList;


public class Dogiadung_Fragment extends Fragment {
     private FragmentDogiadungBinding binding;
        ArrayList<Product> products;
        Product product;
        R3cyDB db;
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



    private void createDb() {
        db = new R3cyDB(getContext());
        db.createSampleProduct();
    }



        private void loadData() {
            products = new ArrayList<>();

            Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT  + " WHERE Category = 'Đồ gia dụng' ");
            while (cursor.moveToNext()) {
                try {
                    products.add(new Product(
                            cursor.getInt(0), // ProductID
                            cursor.getBlob(4), // ProductThumb
                            cursor.getString(1), // ProductName
                            cursor.getDouble(9), // SalePrice
                            cursor.getString(6), // Category
                            cursor.getString(3), //Description
                            cursor.getDouble(8) // ProductRate
                    ));

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();

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