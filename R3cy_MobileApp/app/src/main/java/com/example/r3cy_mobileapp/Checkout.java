package com.example.r3cy_mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CartAdapter;
import com.example.adapter.PaymentItemAdapter;
import com.example.dao.ProductDao;
import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.models.VoucherCheckout;
import com.example.r3cy_mobileapp.databinding.ActivityCheckoutBinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Checkout extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    R3cyDB db;
    private ProductDao productDao;
    PaymentItemAdapter adapter;

    ArrayList<CartItem> selectedItems;

    Address address;
    private int addressIdFromIntent;
    private  int voucherIdFromIntent;
    private int selectedAddress;
    private String selectedPaymentMethod = "COD";
//    address khi đổi địa chỉ nhận hàng
    private static final int ADDRESS_SELECTION_REQUEST_CODE = 1;
    private static final int PAYMENT_METHOD_REQUEST_CODE = 2;
    public static final  int CHECKOUT_VOUCHER_REQUEST_CODE = 3;

    int customerId;

    String email;
    // Tính tổng số tiền từ danh sách các mục đã chọn
    double shippingFee = 25000;
    double couponShipping = 0 ;
    double couponOrder = 0 ;
    double couponDiscount = 0;
    double totalAmount;
    double totalOrderValue;
    int couponid;
    String notes;
    Customer customer;
    int voucherId;

    VoucherCheckout voucherCheckout;
    // Định dạng số
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        email = getIntent().getStringExtra("key_email");

        Log.d("SharedPreferences", "Email ở checkout: " + email);



        createDb();
        addEvents();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadDataCart();

        // Lấy ID của địa chỉ từ Intent
        addressIdFromIntent = getIntent().getIntExtra("ADDRESS_ID", -1);
        if (addressIdFromIntent == -1) {
            Log.d("addressIdFromIntent", "Không lấy được addressId từ intent");
            displayAddress();
        } else {
            displayAddress();
        }
        voucherIdFromIntent = getIntent().getIntExtra("COUPON_ID", -1);
        if (voucherIdFromIntent == -1) {
            Log.d("voucherIdFromIntent", "Không lấy được từ intent");
        } else {
            Log.d("voucherIdFromIntent", "voucherIdFromIntent: " + voucherIdFromIntent);
            processVoucher();
        }

    }
    private void processVoucher() {
        // Kiểm tra nếu voucherIdFromIntent không phải là giá trị mặc định (-1)
        if (voucherIdFromIntent != -1) {
            voucherCheckout = db.getCouponById(voucherIdFromIntent);
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
                    couponShipping = voucherCheckout.getCOUPON_VALUE()*totalAmount;
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

            // Gán giá trị định dạng vào TextView
            binding.txtTotalOrderValue.setText(numberFormat.format(totalOrderValue));
            binding.txtCouponShipping.setText(numberFormat.format(couponShipping));
            binding.txtDiscountOrder.setText(numberFormat.format(couponOrder));

        } else {
            Log.d("processVoucher", "Không lấy được voucherId từ intent");
        }
    }


    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataAddress();
        db.createSampleDataCustomer();

        if (db != null) {
            Log.d("AddressListDB", "Database created successfully");
        } else {
            Log.e("AddressListDB", "Failed to create database");
        }
        productDao = new ProductDao(db);
    }

    private void loadDataCart() {
        // Lấy danh sách các sản phẩm đã chọn từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("selected_items", Context.MODE_PRIVATE);
        String serializedList = sharedPreferences.getString("selected_items_list", null);
        selectedItems = new ArrayList<>();
        if (serializedList != null) {
            try {
                byte[] bytes = Base64.decode(serializedList, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                selectedItems = (ArrayList<CartItem>) objectInputStream.readObject();
                // Log để kiểm tra dữ liệu đã được lấy ra chưa
                for (CartItem item : selectedItems) {
                    Log.d("CartItem", "Product Name: " + item.getProductName() + ", Quantity: " + item.getProductQuantity() + ", Price: " + item.getProductPrice());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("SharedPreferences", "Không có dữ liệu được lưu trong SharedPreferences.");
        }
        // Tính tổng số tiền từ danh sách các mục đã chọn
        totalAmount = calculateTotalAmount(selectedItems);
        shippingFee = 25000; //        cố định bằng 25000
        binding.txtShippingfee.setText(numberFormat.format(shippingFee));
        binding.txtTotalAmount.setText(numberFormat.format(totalAmount));
        // Gán giá trị định dạng vào TextView
        totalOrderValue = totalAmount + shippingFee -couponOrder - couponShipping;
        binding.txtTotalOrderValue.setText(numberFormat.format(totalOrderValue));
        binding.txtCouponShipping.setText(numberFormat.format(couponShipping));
        binding.txtDiscountOrder.setText(numberFormat.format(couponOrder));
        binding.txtDiscount.setText(numberFormat.format(couponDiscount));

        // Khởi tạo adapter và thiết lập cho ListView
        adapter = new PaymentItemAdapter(this, R.layout.payment_item, selectedItems);
        binding.lvPaymentItemList.setAdapter(adapter);

    }

    private double calculateTotalAmount(ArrayList<CartItem> selectedItems) {
        double totalAmount = 0.0;
        for (CartItem item : selectedItems) {
            totalAmount += item.getProductPrice() * item.getProductQuantity();
        }
        return totalAmount;
    }
    private void displayAddress() {
        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
        if (email == null) {
            return;
        }

        // Lấy thông tin của khách hàng dựa trên email từ SharedPreferences
        customer = db.getCustomerByEmail1(email);
        Log.d("customer", "customer ở checkout: " + customer.getFullName());
        customerId = customer.getCustomerId();
        // Nếu chưa có addressIdFromIntent (tức là chưa chọn địa chỉ từ trang danh sách địa chỉ)
        if (addressIdFromIntent == -1) {
            Address defaultAddress = db.getDefaultAddress(customerId);
            Address maxAddress = db.getMaxAddress(customerId);

            if (defaultAddress != null) {
                displayAddressOnUI(defaultAddress);
                selectedAddress = defaultAddress.getAddressId();
            } else if (maxAddress != null) {
                displayAddressOnUI(maxAddress);
                selectedAddress = maxAddress.getAddressId();
            } else {
                // Không có địa chỉ nào, hiển thị nút "Thêm địa chỉ nhận hàng"
                displayAddAddressButton();
            }
        } else {
            // Nếu đã có addressIdFromIntent, hiển thị địa chỉ tương ứng
            Address selectedAddressFromIntent = db.getAddressById(addressIdFromIntent);
            if (selectedAddressFromIntent != null) {
                displayAddressOnUI(selectedAddressFromIntent);
                selectedAddress = selectedAddressFromIntent.getAddressId();
            }
        }
    }


    // Hiển thị địa chỉ lên giao diện

    private void displayAddressOnUI(Address address) {
        binding.txtAddAddress.setVisibility(View.GONE);
        // Cập nhật TextViews với thông tin địa chỉ
        binding.txtReceiver.setText(address.getReceiverName() + " | " + address.getReceiverPhone());
        binding.txtAddress.setText(address.getAddressDetails() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince());
    }
    // Hiển thị nút "Thêm địa chỉ nhận hàng" trên giao diện

    private void displayAddAddressButton() {
        // Ẩn các TextView hiển thị địa chỉ
        binding.addressLayout.setVisibility(View.GONE);

        binding.txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, Checkout_Address.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_SELECTION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Nhận addressId được chọn từ trang danh sách địa chỉ
                int selectedAddressId = data.getIntExtra("SELECTED_ADDRESS_ID", -1);
                if (selectedAddressId != -1) {
                    // Cập nhật addressIdFromIntent với địa chỉ được chọn
                    addressIdFromIntent = selectedAddressId;
                    // Hiển thị địa chỉ đã được chọn
                    displayAddress();
                }
            }
        }
        if (requestCode == PAYMENT_METHOD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String paymentMethodFromIntent = data.getStringExtra("PAYMENT_METHOD");
                if(paymentMethodFromIntent != null){
                    selectedPaymentMethod = paymentMethodFromIntent;
                    // Gán giá trị phương thức thanh toán vào TextView
                    binding.txtPaymentMethod.setText(paymentMethodFromIntent);
                }

            }
        }
        if (requestCode == CHECKOUT_VOUCHER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                voucherId = data.getIntExtra("COUPON_ID", -1);
                Log.d("VoucherCheckout", "COUPON_ID: "+ voucherId);

            }
            if (voucherId != -1) {
                voucherIdFromIntent = voucherId;
            }

        }
    }
    private void addEvents() {
        binding.addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Checkout.this, Checkout_AddressList.class);
//                startActivity(intent);
                Intent intent = new Intent(Checkout.this, Checkout_AddressList.class);
                intent.putExtra("key_email", email);
                startActivityForResult(intent, ADDRESS_SELECTION_REQUEST_CODE);
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.paymentMethodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, PaymentMethod.class);
                intent.putExtra("key_email", email);
                startActivityForResult(intent, PAYMENT_METHOD_REQUEST_CODE);
            }
        });
        binding.btnMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tạo đơn hàng, lưu xuống DB
                makeOrder();
            }
        });
        binding.voucherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, Checkout_Voucher.class);
                intent.putExtra("key_email", email);
                intent.putExtra("totalAmount", totalAmount);
                startActivityForResult(intent, CHECKOUT_VOUCHER_REQUEST_CODE);
            }
        });
    }

    private void makeOrder() {
        // Kiểm tra xem đã chọn địa chỉ và phương thức thanh toán chưa
        if (selectedAddress == -1) {
            Toast.makeText(Checkout.this, "Vui lòng chọn địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedPaymentMethod == null) {
            Toast.makeText(Checkout.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        notes = binding.edtNotes.getText().toString();
        boolean orderCreated = productDao.createOrder(customerId, totalOrderValue, shippingFee, couponOrder, couponShipping, totalAmount, couponid, notes,  selectedItems, selectedPaymentMethod, "Chờ xử lý", selectedAddress);
        if (orderCreated) {
            // Sau khi đặt hàng thành công trong phương thức makeOrder()
            Intent successIntent = new Intent("com.example.r3cy_mobileapp.ACTION_ORDER_SUCCESS");
            sendBroadcast(successIntent);

            // Xóa các mục đã chọn khỏi giỏ hàng sau khi đặt hàng thành công
            for (CartItem item : selectedItems) {
                productDao.deleteCartItem(item.getLineId());
            }

            // Cập nhật số lượng sản phẩm tồn kho và số lượng đã bán
            for (CartItem item : selectedItems) {
                productDao.updateProductQuantity(item.getProductId(), item.getProductQuantity());
            }

            // Tích lũy điểm cho khách hàng
            productDao.accumulateMembershipScore(customerId, totalOrderValue);

            Toast.makeText(Checkout.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            // Đặt các thao tác hoặc chuyển hướng sau khi đặt hàng thành công
            Intent intent = new Intent(Checkout.this, TrangChu.class);
            intent.putExtra("key_email", email);
            startActivity(intent);
            
        } else {
            Toast.makeText(Checkout.this, "Đặt hàng thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }


    }




}