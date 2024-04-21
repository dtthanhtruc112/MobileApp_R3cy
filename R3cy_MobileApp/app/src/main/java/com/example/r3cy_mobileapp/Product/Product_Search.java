package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.ProductAdapter;
import com.example.dao.ProductDao;
import com.example.dao.ProductDao3;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_Search extends AppCompatActivity {
    ActivityProductSearchBinding binding;
    RecyclerView rvProducts;
    ProductAdapter adapter;
//    List<Product> products;
    List<Product> productsFiltered;
    ArrayList<Product> products;
    Product product;
    private Dialog filterDialog;
    R3cyDB dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Khởi taooj R3cyDB
        dbHelper = new R3cyDB(this);


        createDb();
        addEvents();
    }



    private void createDb() {
        products = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + R3cyDB.TBl_PRODUCT;
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexSalePrice = cursor.getColumnIndex(R3cyDB.SALE_PRICE);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductDescription = cursor.getColumnIndex(R3cyDB.PRODUCT_DESCRIPTION);
                    int columnIndexProductRate = cursor.getColumnIndex(R3cyDB.PRODUCT_RATE);


                    int productID = cursor.getInt(columnIndexProductId);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);
                    String productName = cursor.getString(columnIndexProductName);
                    double salePrice = cursor.getDouble(columnIndexSalePrice);
                    String productCategory = cursor.getString(columnIndexCategory);
                    String productDescription = cursor.getString(columnIndexProductDescription);
                    double productRate = cursor.getDouble(columnIndexProductRate);


                    Product product = new Product(productID, productThumb, productName, salePrice, productCategory, productDescription, productRate);
                    products.add(product);
                } while (cursor.moveToNext());
            } else {
                Log.d("R3cyDB", "No products found in database");
            }
        } catch (SQLiteException e) {
            Log.e("R3cyDB", "Error getting all products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        rvProducts = binding.rvProducts;
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(this, R.layout.viewholder_category_list, products);
        rvProducts.setAdapter(adapter);


        SearchView searchView = findViewById(R.id.search_view);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Thực hiện tìm kiếm khi người dùng gửi truy vấn
                performSearch(query);
                return true;
            }

            private void performSearch(String query) {
//                Lọc dữ liệu dựa trên truy vấn tìm kiếm
                List<Product> filteredProducts = filterProducts(query);

//                Cập nhật Recyclerview với kết quả đã lọc
                updateRecyclerView(filteredProducts);
            }


//                Lọc sản phẩm dựa trên đối sánh văn bản
            private List<Product> filterProducts(String query) {
                List<Product> filteredProducts = new ArrayList<>();
                for (Product product : products) {
                    if (product.getProductName().toLowerCase().contains(query.toLowerCase()) ||
                            product.getCategory().toLowerCase().contains(query.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
                return filteredProducts;
            }

//            Cập nhật RecyclerView với kết quả đã lọc
            private void updateRecyclerView(List<Product> filteredProducts) {
                adapter.setProducts(filteredProducts);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
//                thực hiện tìm kiếm từng phần khi người dùng nhập
                performSearch(newQuery);
                return false;
            }
        });



    }


    private void addEvents() {
        //        Sự kiện click vào mỗi sản phẩm trên recyclerview



}}


