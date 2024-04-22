package com.example.r3cy_mobileapp.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ProductAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
//import com.example.models.Discuss;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.r3cy_mobileapp.AboutUs;
import com.example.r3cy_mobileapp.BlogDetail;
import com.example.r3cy_mobileapp.CartManage;
import com.example.r3cy_mobileapp.Checkout;
import com.example.r3cy_mobileapp.Checkout_AddressList;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.Signup;
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.UserAccount_Main;
import com.example.r3cy_mobileapp.databinding.ActivityProductDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Product_Detail extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    private ProductDao productDao;
    Customer customer;
    Product product;
    int customerId = -1 ;

    TextView txtQuantity, txtProductName,txtDiscuss, txtSalePrice, txtProductDescription, txtProductRate, txtDiscussContent, txtRespondContent;
    ImageView btnIncreaseQuantity;
    ImageView btnDecreaseQuantity, imvProductThumb;
    EditText edtDiscuss, edtCusMail;
    private ProductAdapter adapter;
    private List<Product> products;
    String email;
    BottomNavigationView navigationView;
    private int quantity = 1; //Giá trị mặc định số lượng là 1

    private SQLiteDatabase database;
    int productID;

    R3cyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở product detail: " + email);



        txtProductName = findViewById(R.id.txtProductName);
        txtSalePrice = findViewById(R.id.txtSalePrice);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductRate = findViewById(R.id.txtProductRate);
        imvProductThumb = findViewById(R.id.imvProductThumb);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        txtDiscussContent = findViewById(R.id.txtDiscussContent);
        txtRespondContent = findViewById(R.id.txtRespondContent);
        edtDiscuss = findViewById(R.id.edtDiscuss);
        edtCusMail = findViewById(R.id.edtCusMail);
        txtDiscuss = findViewById(R.id.txtDiscuss);


        txtQuantity.setText(String.valueOf(quantity));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ProductID")) {
            productID = intent.getIntExtra("ProductID", -1);
            if (productID != -1) {
                db = new R3cyDB(this);
                productDao = new ProductDao(db);
                product = db.getProductByID(productID);


                if (product != null) {
                    showProductDetail(product);
                } else {
                    Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
        }

        addEvents();
    }

    private void showProductDetail(Product product) {
        txtProductName.setText(product.getProductName());
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        txtSalePrice.setText(numberFormat.format(product.getSalePrice()));
        txtProductDescription.setText(product.getProductDescription());
        txtProductRate.setText(String.format("%.1f đ", product.getProductRate()));

        byte[] productThumb = product.getProductThumb();
        if (productThumb != null && productThumb.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productThumb, 0, productThumb.length);
            imvProductThumb.setImageBitmap(bitmap);
        } else {
            imvProductThumb.setImageResource(R.drawable.dgd_dia2);
        }
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Thêm vào giỏ hàng
                if (email == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Product_Detail.this);
                    builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
                        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                                startActivity(new Intent(Product_Detail.this, Signin_Main.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Không thực hiện gì nếu nhấn Cancel
                                dialog.dismiss();
                            }
                        });
                    Dialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                            Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                            // Set text color for "Ok" button
                            positiveButton.setTextColor(ContextCompat.getColor(Product_Detail.this, R.color.blue));

                            // Set text color for "Cancel" button
                            negativeButton.setTextColor(ContextCompat.getColor(Product_Detail.this, R.color.blue));
                        }
                    });

                    dialog.show();
                }else{
                    // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
                    customer = db.getCustomerByEmail1(email);
                    Log.d("customer", "customer ở checkout: " + customer.getFullName());

                    customerId = customer.getCustomerId();
                    // Gọi phương thức insertCartItem để thêm thông tin sản phẩm vào bảng cart
                    boolean itemAddedToCart = productDao.insertOrUpdateCartItem(customerId, productID, quantity);

                    if (itemAddedToCart) {
                        Toast.makeText(Product_Detail.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Product_Detail.this, "Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Mở trang BuyNow và load dữ liệu của productid + quantity trên trang Buynow
                if (email == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Product_Detail.this);
                    builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                            startActivity(new Intent(Product_Detail.this, Signin_Main.class));
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Không thực hiện gì nếu nhấn Cancel
                            dialog.dismiss();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                            Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                            // Set text color for "Ok" button
                            positiveButton.setTextColor(ContextCompat.getColor(Product_Detail.this, R.color.blue));

                            // Set text color for "Cancel" button
                            negativeButton.setTextColor(ContextCompat.getColor(Product_Detail.this, R.color.blue));
                        }
                    });

                    dialog.show();
                }else{
                    // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
                    customer = db.getCustomerByEmail1(email);
                    Log.d("customer", "customer ở checkout: " + customer.getFullName());

                    customerId = customer.getCustomerId();

                    ArrayList<CartItem> selectedItems = new ArrayList<>();
                    // Tạo một đối tượng CartItem mới với các tham số tương ứng
                    CartItem newItem = new CartItem(productID, product.getProductName(), product.getCategory(), product.getSalePrice(), quantity, product.getProductThumb(), true, 9900);

                    selectedItems.add(newItem);
                    SharedPreferences sharedPreferences = getSharedPreferences("selected_items", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(selectedItems);
                        String serializedList = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        editor.putString("selected_items_list", serializedList);
                        editor.apply();

                        // Log để kiểm tra dữ liệu đã được lưu xuống SharedPreferences
                        Log.d("SharedPreferences", "Dữ liệu đã được lưu xuống SharedPreferences.");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Chuyển sang trang Checkout
                    Intent intent = new Intent(Product_Detail.this, Checkout.class);
                    intent.putExtra("key_email", email);
                    startActivity(intent);
                }

            }
        });

        Button btnGui = findViewById(R.id.btnGui);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy nội dung từ EditText
                String userInput = edtDiscuss.getText().toString();
                // Hiển thị nội dung trong TextView
                txtDiscuss.setText(userInput);
            }
        });

        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_product);


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
    }
}