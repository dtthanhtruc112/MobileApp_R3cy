package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.adapter.VoucherCheckoutAdapter;
import com.example.adapter.VoucherCheckoutAdapter2;
import com.example.databases.R3cyDB;
import com.example.models.Customer;
import com.example.models.VoucherCheckout;
import com.example.r3cy_mobileapp.databinding.ActivityCartVoucherBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Cart_Voucher extends AppCompatActivity {
    ActivityCartVoucherBinding binding;
    R3cyDB db;
    VoucherCheckoutAdapter2 adapter;
    Customer customer;
    String email;
    ArrayList<VoucherCheckout> vouchers;
    double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        email = getIntent().getStringExtra("key_email");
        totalAmount = getIntent().getDoubleExtra("totalAmount",totalAmount);
        Log.d("totalAmount", "totalAmount ở voucher acc: " + totalAmount);
        Log.d("SharedPreferences", "Email ở voucher acc: " + email);

        createDb();
        addEvents();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadData();
    }

    private void addEvents() {
    }

    private void createDb() {
        db = new R3cyDB(this);
        db.createSampleDataCoupon();
    }

    private void loadData() {
        int customerId = db.getCustomerIdFromCustomer(email);
        if (customerId != -1) {
            vouchers = new ArrayList<>();
            Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.CUSTOMER_IDS + " LIKE '%" + customerId + "%'");

            while (c.moveToNext()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date validDate = dateFormat.parse(c.getString(6));
                    Date expireDate = dateFormat.parse(c.getString(7));

                    // Lấy ngày hiện tại
                    Date currentDate = new Date();

                    // Kiểm tra nếu voucher thỏa mãn các điều kiện
                    boolean isValidVoucher = currentDate.after(validDate) && currentDate.before(expireDate) && totalAmount >= c.getDouble(8);

                    ArrayList<Integer> customerIds = new ArrayList<>();
                    String customerIdsString = c.getString(12);
                    if (customerIdsString != null && !customerIdsString.isEmpty()) {
                        String[] ids = customerIdsString.replaceAll("\\[|\\]", "").split(",\\s*");
                        for (String id : ids) {
                            customerIds.add(Integer.parseInt(id.trim()));
                        }
                    }

                    // Tạo voucher và đánh dấu nó là có hiệu lực hoặc không
                    VoucherCheckout voucher = new VoucherCheckout(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4),
                            c.getString(5),
                            validDate,
                            expireDate,
                            c.getDouble(8),
                            c.getDouble(9),
                            c.getDouble(10),
                            c.getInt(11),
                            customerIds,
                            false,
                            isValidVoucher
                    );

                    vouchers.add(voucher);
                } catch (ParseException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            c.close();

            // Khởi tạo và thiết lập adapter cho ListView
            adapter = new VoucherCheckoutAdapter2(Cart_Voucher.this, R.layout.item_voucher_checkout, vouchers, email);
            binding.lvVoucher.setAdapter(adapter);
        } else {
            Toast.makeText(Cart_Voucher.this, "Không tìm thấy phiếu giảm giá cho khách hàng này", Toast.LENGTH_SHORT).show();
            Log.e("Error", "Customer object is null");
        }
    }

    public void openCartActivity(VoucherCheckout c){
        Intent intent = new Intent(this, CartManage.class);
        intent.putExtra("COUPON_ID", c.getCOUPON_ID());
        intent.putExtra("key_email", email);
        startActivity(intent);

    }
}