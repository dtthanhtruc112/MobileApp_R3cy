package com.example.r3cy_mobileapp.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Product;
import com.example.models.ProductAtb;
import com.example.r3cy_mobileapp.AboutUs;
import com.example.r3cy_mobileapp.BlogDetail;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.UserAccount_Main;
import com.example.r3cy_mobileapp.databinding.ActivityProductSearchBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Product_Search extends AppCompatActivity {
    ActivityProductSearchBinding binding;
    RecyclerView rvProducts;
    ProductAdapter adapter;
    ArrayList<Product> products;
    ArrayList<ProductAtb> productAtbs;
    R3cyDB db;
    Dialog filterDialog;
    String email;
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductSearchBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_product_search);

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở search: " + email);

        adapter = new ProductAdapter(this, R.layout.viewholder_category_list, new ArrayList<Product>(), email);
        createDb();
        loadData();
        setupSearchView();
        setupFilterDialog();

        addEvents();
    }

    private void addEvents() {
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_home);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(),Product_List.class);
                    intent1.putExtra("key_email", email);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(), BlogDetail.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(), AboutUs.class);
                    intent3.putExtra("key_email", email);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(), UserAccount_Main.class);
                    intent4.putExtra("key_email", email);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_home) {
                    Intent intent5 =new Intent(getApplicationContext(), TrangChu.class);
                    intent5.putExtra("key_email", email);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;}


        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleProduct();
    }

    private void loadData() {
        products = new ArrayList<>();
        productAtbs = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);
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
                productAtbs.add(new ProductAtb(
                        cursor.getInt(0), // ProductID
                        cursor.getDouble(2), // ProductPrice
                        cursor.getInt(5), // Hot
                        cursor.getInt(7), // Inventory
                        cursor.getInt(10), // SoldQuantity
                        cursor.getString(11), // CreatedDate
                        cursor.getInt(12) // Status
                ));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        setupRecyclerView(products);
    }

    private void setupRecyclerView(ArrayList<Product> products) {
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(this, R.layout.viewholder_category_list, products, email);
        rvProducts.setAdapter(adapter);
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Thực hiện tìm kiếm khi người dùng gửi truy vấn
                performSearch(query);
                return true;
            }

            private void performSearch(String query) {
                // Lọc dữ liệu dựa trên truy vấn tìm kiếm
                List<Product> filteredProducts = filterProducts(query);
                // Cập nhật RecyclerView với kết quả đã lọc
                updateRecyclerView(filteredProducts);
            }

            // Lọc sản phẩm dựa trên đối sánh văn bản
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

            // Cập nhật RecyclerView với kết quả đã lọc
            private void updateRecyclerView(List<Product> filteredProducts) {
                adapter.setProducts(filteredProducts);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                // Thực hiện tìm kiếm từng phần khi người dùng nhập
                performSearch(newQuery);
                return false;
            }

        });
    }


    private void setupFilterDialog() {
        // Khởi tạo dialog
        filterDialog = new Dialog(this);
        filterDialog.setContentView(R.layout.dialog_filter_search);

        // Lấy view của button filter
        LinearLayout btnFilter = findViewById(R.id.btn_filter);
        if (btnFilter != null) {
            btnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi nút được nhấn
                    showFilterDialog();
                }
            });
        }

        TextView filterPhobien = filterDialog.findViewById(R.id.filter_phobien);
        TextView filterMoinhat = filterDialog.findViewById(R.id.filter_moinhat);
        TextView filterMinPrice = filterDialog.findViewById(R.id.filter_minprice);
        TextView filterMaxPrice = filterDialog.findViewById(R.id.filter_maxprice);

        filterPhobien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> filteredProducts = filterProductsByHot(1);
                displayFilteredProducts(filteredProducts);
            }
        });

        filterMoinhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> filteredProducts = filterNewestProducts();
                displayFilteredProducts(filteredProducts);
            }
        });

        filterMaxPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> filteredProducts = filterProductsByPriceDesc();
                displayFilteredProducts(filteredProducts);
            }
        });

        filterMinPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> filteredProducts = filterProductsByPriceAsc();
                displayFilteredProducts(filteredProducts);
            }
        });
    }

    private void showFilterDialog() {
        Window window = filterDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog);
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        filterDialog.show();
    }

    private List<Product> filterProductsByHot(int hotValue) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            int productId = product.getProductID();
            ProductAtb productAtb = getProductAtbById(productId);

            if (productAtb != null && productAtb.getHot() == hotValue) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private ProductAtb getProductAtbById(int productId) {
        for (ProductAtb productAtb : productAtbs) {
            if (productAtb.getProductID() == productId) {
                return productAtb;
            }
        }
        return null;
    }

    private void displayFilteredProducts(List<Product> filteredProducts) {
        adapter.setProducts(filteredProducts);
        adapter.notifyDataSetChanged();
    }

    private List<Product> filterNewestProducts() {
        List<Product> filteredProducts = new ArrayList<>();
        int numProductsToShow = 6;

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                String date1 = getCreatedDateFromProductAtbs(productAtbs, product1.getProductID());
                String date2 = getCreatedDateFromProductAtbs(productAtbs, product2.getProductID());
                return compareDates(date2, date1);
            }

            private int compareDates(String date1, String date2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date d1 = dateFormat.parse(date1);
                    Date d2 = dateFormat.parse(date2);
                    return d2.compareTo(d1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        filteredProducts.addAll(products.subList(0, Math.min(numProductsToShow, products.size())));
        return filteredProducts;
    }

    private String getCreatedDateFromProductAtbs(ArrayList<ProductAtb> productAtbs, int productID) {
        for (ProductAtb productAtb : productAtbs) {
            if (productAtb.getProductID() == productID) {
                return productAtb.getCreatedDate();
            }
        }
        return "";
    }

    private List<Product> filterProductsByPriceDesc() {
        List<Product> filteredProducts = new ArrayList<>();
        int numProductsToShow = 6;

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                double price1 = getProductPrice(product1.getProductID());
                double price2 = getProductPrice(product2.getProductID());
                return Double.compare(price2, price1);
            }
        });

        filteredProducts.addAll(products.subList(0, numProductsToShow));
        return filteredProducts;
    }

    private List<Product> filterProductsByPriceAsc() {
        List<Product> filteredProducts = new ArrayList<>();
        int numProductsToShow = 6;

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                double price1 = getProductPrice(product1.getProductID());
                double price2 = getProductPrice(product2.getProductID());
                return Double.compare(price1, price2);
            }
        });

        filteredProducts.addAll(products.subList(0, numProductsToShow));
        return filteredProducts;
    }

    private double getProductPrice(int productID) {
        for (ProductAtb productAtb : productAtbs) {
            if (productAtb.getProductID() == productID) {
                return productAtb.getProductPrice();
            }
        }
        return 0.0;
    }
}