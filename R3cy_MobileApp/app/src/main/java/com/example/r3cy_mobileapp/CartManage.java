package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CartAdapter;
import com.example.adapter.ProductAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductAtb;
import com.example.models.SelectedItemsManager;
import com.example.models.VoucherCheckout;
import com.example.r3cy_mobileapp.Product.Product_Detail;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityCartManageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartManage extends AppCompatActivity {

    ActivityCartManageBinding binding;
    Product product;
    ProductAdapter adapter1;
    ArrayList<ProductAtb> productAtbs;
    private List<Product> products;

    R3cyDB db;
    ProductDao productDao;
    CartAdapter adapter;
    String email;
    Customer customer;
    double shippingFee = 25000;
    double couponShipping = 0 ;
    double couponOrder = 0 ;
    double couponDiscount = 0;
    double totalAmount;
    double totalOrderValue;
    int couponid;

    ArrayList<CartItem> cartItems;
    public static final int CART_VOUCHER_REQUEST_CODE = 1;
    int voucherId;
    VoucherCheckout voucherCheckout;

    ArrayList<CartItem> selectedItems;
    ArrayList<Integer> selectedItemIdsfromIntent;
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở cartmanage: " + email);

        createDb();
        loadData();
        addEvents();
    }


    private void addAllCheckBoxes(boolean isChecked) {
        // Duyệt qua từng item trong ListView
        for (int i = 0; i < binding.lvCartList.getChildCount(); i++) {
            View itemView = binding.lvCartList.getChildAt(i);
            CheckBox chkProductBuy = itemView.findViewById(R.id.chk_ProductBuy);
            // Đặt trạng thái của checkbox trong mỗi item là giống với trạng thái của chk_All
            chkProductBuy.setChecked(isChecked);
        }
    }

    private void createDb() {
        db = new R3cyDB(this);

        if (db != null) {
            Log.d("CartManage", "Database created successfully");
        } else {
            Log.e("CartManage", "Failed to create database");
        }
        db.createSampleProduct();
        db.createSampleDataCustomer();
        db.createSampleDataCart();
        // Khởi tạo productDao sau khi database được khởi tạo xong
        productDao = new ProductDao(db);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadDataSuggest();

        if(selectedItemIdsfromIntent == null){
            Log.d("selectedItemIdsfromIntent", "Không lấy được từ intent");
        }else{
            Log.d("selectedItemIdsfromIntent", "selectedItemIdsfromIntent: " + selectedItemIdsfromIntent);
        }
    }

    private void processVoucher(int voucherId) {
        // Kiểm tra nếu voucherIdFromIntent không phải là giá trị mặc định (-1)
        if (voucherId != -1) {
            voucherCheckout = db.getCouponById(voucherId);
            Log.d("voucherCheckout", "voucherCheckout" + voucherCheckout);
            if("percent".equals(voucherCheckout.getCOUPON_TYPE())) {
                if("order".equals(voucherCheckout.getCOUPON_CATEGORY())) {
                    couponOrder = voucherCheckout.getCOUPON_VALUE()*totalAmount;
                    if(couponOrder > voucherCheckout.getMAXIMUM_DISCOUNT()){
                        couponOrder = voucherCheckout.getMAXIMUM_DISCOUNT();
                    }
                    couponDiscount = couponOrder;
                }else
                {
                    couponShipping = voucherCheckout.getCOUPON_VALUE()*shippingFee;
                    if(couponShipping > voucherCheckout.getMAXIMUM_DISCOUNT()){
                        couponShipping = voucherCheckout.getMAXIMUM_DISCOUNT();
                        if (couponShipping > shippingFee) {
                            couponShipping = shippingFee;
                        }
                    }
                    couponDiscount = couponShipping;
                }
            } else {
                if("order".equals(voucherCheckout.getCOUPON_CATEGORY())){
                    couponOrder = voucherCheckout.getCOUPON_VALUE();
                    couponDiscount = couponOrder;
                }else
                {
                    couponShipping = voucherCheckout.getCOUPON_VALUE();
                    if (couponShipping > shippingFee) {
                        couponShipping = shippingFee;
                    }
                    couponDiscount = couponShipping;
                }

            }
            binding.txtDiscount.setText(numberFormat.format(couponDiscount));
            totalOrderValue = totalAmount + shippingFee -couponOrder - couponShipping;
            if(totalOrderValue < 0){
                totalOrderValue = 0;
            }

        }else {
            Log.d("processVoucher", "Không lấy được voucherId từ intent");
        }
    }


    private void loadData() {
        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CartManage.this);
            builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                    startActivity(new Intent(CartManage.this, Signin_Main.class));
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
                    positiveButton.setTextColor(ContextCompat.getColor(CartManage.this, R.color.blue));

                    // Set text color for "Cancel" button
                    negativeButton.setTextColor(ContextCompat.getColor(CartManage.this, R.color.blue));
                }
            });

            dialog.show();
        }else{
            // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
            customer = db.getCustomerByEmail1(email);

            // Lấy dữ liệu từ ProductDao
            cartItems = (ArrayList<CartItem>) productDao.getCartItemsForCustomer(customer.getCustomerId());
            Log.i("CartItemSize", "Number of items retrieved: " + cartItems.size());
            // Khởi tạo adapter và thiết lập cho ListView
            adapter = new CartAdapter(this, R.layout.cartitem, cartItems);
            binding.lvCartList.setAdapter(adapter);



            // Set total amount TextView in adapter
            adapter.setTxtTotalAmount(binding.txtTotalAmount);

            // Thiết lập sự kiện lắng nghe khi số lượng giảm
            adapter.setOnQuantityDecreaseListener(new CartAdapter.OnQuantityDecreaseListener() {
                @Override
                public void onDecrease(int position) {

                    handleQuantityDecrease(position);
                    // Set total amount TextView in adapter
                    adapter.setTxtTotalAmount(binding.txtTotalAmount);
                }
            });

            adapter.setOnQuantityIncreaseListener(new CartAdapter.OnQuantityIncreaseListener() {
                @Override
                public void onIncrease(int position) {

                    handleQuantityIncrease(position);
                    // Set total amount TextView in adapter
                    adapter.setTxtTotalAmount(binding.txtTotalAmount);
                }
            });
        }





    }
    private void loadDataSuggest() {
        GridLayoutManager layoutManagerProducts = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        binding.rcvProducts.setLayoutManager(layoutManagerProducts);

        products = new ArrayList<>();
        productAtbs = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);

        try {
            while (cursor.moveToNext()) {
                try {
                    products.add(new Product(
                            cursor.getInt(0), // ProductID
                            cursor.getBlob(4), // ProductThumb
                            cursor.getString(1), // ProductName
                            cursor.getDouble(9), // SalePrice

                            cursor.getString(6), // Category
                            cursor.getString(3),
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
        } finally {
            cursor.close();
        }
        List<Product> filteredProducts = filterProductsByHot(1);
//        List<Product> filteredProducts = filterProductsByHot(0);

        // Kiểm tra nếu danh sách products hoặc cartItems là null
        if (products == null) {
            Log.e("CartManage", "Danh sách sản phẩm là null");
            return;
        }

        if (cartItems == null) {
            Log.e("CartManage", "Danh sách các mục giỏ hàng là null");
            return;
        }
        // Lọc ra các sản phẩm đã hiển thị từ danh sách cartitem
        List<Integer> shownProductIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            shownProductIds.add(cartItem.getProductId());
        }
        // Lọc ra các sản phẩm gợi ý không nằm trong danh sách các sản phẩm đã hiển thị
        List<Product> filteredSuggestedProducts = new ArrayList<>();
        for (Product product : filteredProducts) {
            if (!shownProductIds.contains(product.getProductID())) {
                filteredSuggestedProducts.add(product);
            }
        }
        // Hiển thị danh sách sản phẩm gợi ý đã lọc
        adapter1 = new ProductAdapter(this, R.layout.viewholder_category_list, filteredSuggestedProducts, email);
        binding.rcvProducts.setAdapter(adapter1);

        Log.d("ProductInfo", "Number of products retrieved: " + products.size());



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


    // Trong phương thức handleQuantityIncrease và handleQuantityDecrease của CartManage

    private void handleQuantityIncrease(int position) {
        CartItem cartItem = cartItems.get(position);
        int currentQuantity = cartItem.getProductQuantity();
        int newQuantity = currentQuantity + 1;
        cartItem.setProductQuantity(newQuantity);

        adapter.notifyDataSetChanged();

        // Cập nhật số lượng mới vào cơ sở dữ liệu thông qua ProductDao
        if (productDao != null) {
            boolean isUpdated = productDao.updateQuantity(cartItem.getLineId(), newQuantity);
            if (isUpdated) {
                Log.d("CartManage", "Update successful");
            } else {
                Log.e("CartManage", "Failed to update quantity in database.");
            }
        } else {
            Log.e("CartManage", "ProductDao is null. Cannot update quantity.");
        }
    }

    private void handleQuantityDecrease(int position) {
        CartItem cartItem = cartItems.get(position);
        int currentQuantity = cartItem.getProductQuantity();
        if (currentQuantity > 1) {
            int newQuantity = currentQuantity - 1;
            cartItem.setProductQuantity(newQuantity);
            adapter.notifyDataSetChanged();
            if (productDao != null) {
                boolean isUpdated = productDao.updateQuantity(cartItem.getLineId(), newQuantity);
                if (isUpdated) {
                    Log.d("CartManage", "Update successful");
                } else {
                    Log.e("CartManage", "Failed to update quantity in database.");
                }
            } else {
                Log.e("CartManage", "ProductDao is null. Cannot update quantity.");
            }
        }
    }

    // Phương thức để xóa một mục từ cơ sở dữ liệu

    public void deleteCartItem(int lineId) {
        // Thực hiện xóa mục từ cơ sở dữ liệu sử dụng ProductDao hoặc phương thức xóa phù hợp
        if (productDao != null) {
            boolean success = productDao.deleteCartItem(lineId);
            if (success) {
                // Nếu xóa thành công, cập nhật lại dữ liệu và hiển thị
                loadData();
            } else {
                // Xử lý lỗi khi xóa không thành công
                Log.e("CartManage", "Failed to delete item from database.");
            }
        } else {
            Log.e("CartManage", "ProductDao is null. Cannot delete item.");
        }
    }
    public void OpenDetailProduct(int productID){
        Intent intent = new Intent(CartManage.this, Product_Detail.class);
        intent.putExtra("ProductID",productID );
        intent.putExtra("key_email", email);
        startActivity(intent);
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Gọi hàm addAllCheckBoxes() khi trạng thái của chk_All thay đổi
                addAllCheckBoxes(isChecked);
            }
        });
        binding.btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một danh sách mới để chứa các sản phẩm được chọn
                selectedItems = new ArrayList<>();

                // Duyệt qua danh sách sản phẩm và kiểm tra xem sản phẩm nào được chọn
                for (CartItem item : cartItems) {
                    if (item.isSelected()) {
                        selectedItems.add(item);
                    }
                }
                totalAmount = calculateTotalAmount(selectedItems);

                // Kiểm tra xem có sản phẩm được chọn không
                if (selectedItems.isEmpty()) {
                    // Nếu không có sản phẩm nào được chọn, hiển thị Toast thông báo
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu có sản phẩm được chọn, lưu danh sách các sản phẩm đã chọn vào SharedPreferences
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
                    Intent intent = new Intent(CartManage.this, Checkout.class);
                    intent.putExtra("key_email", email);
                    intent.putExtra("voucherIdFromCartIntent", voucherId);
                    intent.putExtra("couponDiscount", couponDiscount);
                    startActivity(intent);
                }
            }
        });

        binding.voucherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItems = new ArrayList<>();

                ArrayList<Integer> selectedItemIds = new ArrayList<>();

                // Duyệt qua danh sách sản phẩm và kiểm tra xem sản phẩm nào được chọn
                for (CartItem item : cartItems) {
                    if (item.isSelected()) {
                        selectedItems.add(item);
                        selectedItemIds.add(item.getLineId());
                    }
                }
                // Kiểm tra xem selectedItems đã được khởi tạo và có dữ liệu không
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    // Nếu có sản phẩm được chọn, tính toán totalAmount và chuyển sang Cart_Voucher
                    totalAmount = calculateTotalAmount(selectedItems);
                    Intent intent = new Intent(CartManage.this, Cart_Voucher.class);
                    intent.putExtra("key_email", email);
                    intent.putIntegerArrayListExtra("selectedItemIds", selectedItemIds);
                    intent.putExtra("totalAmount", totalAmount);
                    startActivityForResult(intent, CART_VOUCHER_REQUEST_CODE);
                } else {
                    // Nếu không có sản phẩm được chọn, hiển thị thông báo
                    Toast.makeText(CartManage.this, "Vui lòng chọn sản phẩm trước khi sử dụng voucher", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        binding.lvCartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Lấy CartItem tại vị trí được nhấn
//                CartItem clickedCartItem = cartItems.get(position);
//
//                // Lấy productId của CartItem
//                int productId = clickedCartItem.getProductId();
//                // Chuyển sang trang DetailProduct với productId tương ứng
//                Intent intent = new Intent(CartManage.this, Product_Detail.class);
//                intent.putExtra("ProductID", productId);
//                startActivity(intent);
//            }
//        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CART_VOUCHER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                voucherId = data.getIntExtra("COUPON_ID", -1);
                selectedItemIdsfromIntent = data.getIntegerArrayListExtra("selectedItemIds");
                Log.d("VoucherCart", "COUPON_ID: "+ voucherId);
                Log.d("selectedItemIdsfromIntent", "selectedItemIdsfromIntent" + selectedItemIdsfromIntent);
                processVoucher(voucherId);

            }
            if (voucherId == -1) {
//                voucherIdFromIntent = voucherId;
                Log.d("VoucherCart", "COUPON_ID: "+ voucherId +"Khng lấy được");
            }

        }
    }
    private double calculateTotalAmount(ArrayList<CartItem> selectedItems) {
        double totalAmount = 0.0;
        for (CartItem item : selectedItems) {
            totalAmount += item.getProductPrice() * item.getProductQuantity();
        }
        return totalAmount;
    }
}
