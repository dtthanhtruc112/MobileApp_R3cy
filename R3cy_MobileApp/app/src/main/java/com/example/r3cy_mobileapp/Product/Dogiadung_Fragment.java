package com.example.r3cy_mobileapp.Product;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Dogiadung_Fragment extends Fragment {
    private static final String DATABASE_NAME = "r3cy_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";
    private SQLiteDatabase database;
    R3cyDB db;
    private ProductAdapter adapter;
    private RecyclerView rvProducts;
    FragmentDogiadungBinding binding;
    ArrayList<Product> products;


    ItemTouchHelper itemTouchHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDogiadungBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        RecyclerView rvProducts = binding.rvProducts;
        rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        List<Product> productList = new ArrayList<>(products);
        adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, productList);
        rvProducts.setAdapter(adapter);

       loadData();
       addEvents();
        return rootView;

    }


    private void addControls() {

    }



    private void loadData() {
        try {
            // Mở hoặc tạo database nếu chưa tồn tại
            database = openOrCreateDatabase();
            // Sao chép dữ liệu từ file database vào trong ứng dụng
            copyDatabaseFromAssets();

            // Truy vấn dữ liệu từ database
            List<Product> products = new ArrayList<>();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);
            while (cursor.moveToNext()) {
                // Tạo đối tượng Product từ dữ liệu trong Cursor và thêm vào danh sách products
                products.add(new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getDouble(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getBlob(11),
                        cursor.getBlob(12),
                        cursor.getBlob(13),
                        cursor.getString(14),
                        cursor.getInt(15)
                ));
            }
            cursor.close();

            // Cập nhật dữ liệu lên RecyclerView thông qua adapter
            adapter.setData(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase openOrCreateDatabase() {
        return requireContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    private void copyDatabaseFromAssets() {
        try {
            InputStream inputStream = requireContext().getAssets().open(DATABASE_NAME);
            String outFileName = requireContext().getDatabasePath(DATABASE_NAME).getPath();
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (database != null) {
            database.close();
        }
        binding = null;
    }

    private void addEvents() {

//        binding.rvProducts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                // Xử lý sự kiện chạm vào item trong RecyclerView
//                View childView = rv.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
//                    int position = rv.getChildAdapterPosition(childView);
//                    ProductInterface productInterface = (ProductInterface) requireActivity();
//                    Product selectedProduct = (Product) adapter.getItem(position);
//
//                    // Tạo Intent và gửi thông tin sản phẩm sang Product_Detail Activity
//                    Intent intent = new Intent(requireContext(), Product_Detail.class);
//                    intent.putExtra("product", selectedProduct);
//                    startActivity(intent);
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//
//        });

//
    }



}