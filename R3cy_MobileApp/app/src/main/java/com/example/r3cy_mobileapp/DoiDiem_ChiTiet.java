package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.r3cy_mobileapp.databinding.ActivityDoiDiemChiTietBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DoiDiem_ChiTiet extends AppCompatActivity {
    
    ActivityDoiDiemChiTietBinding binding;
    Coupon coupon;
    R3cyDB db;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoiDiemChiTietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        
        db = new R3cyDB(this);
//        db.createSampleDataCustomer();

        addEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataCustomer();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Package");
        if (bundle != null && bundle.containsKey("COUPON")) {
            coupon = (Coupon) bundle.getSerializable("COUPON");
            if (coupon != null) {
                binding.txtCode.setText(coupon.getCOUPON_CODE());
                binding.txtTitle.setText(coupon.getCOUPON_TITLE());
                binding.txtScore.setText(String.valueOf(coupon.getSCORE_MIN()));

                // Lấy ngày hiện tại
                Date currentDate = new Date();



                // Lấy ngày hết hạn của coupon
                Date expireDate = coupon.getEXPIRE_DATE();

                Log.d("CouponDetails", "Expire Date: " + expireDate); // Log thông tin của ngày hết hạn

                if (expireDate != null) {
                    // Tính số ngày còn lại giữa ngày hết hạn và ngày hiện tại
                    long diffInMillies = expireDate.getTime() - currentDate.getTime();
                    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

                    Log.d("CouponDetails", "Days Left: " + diffInDays); // Log số ngày còn lại

                    // Hiển thị số ngày còn lại trên giao diện của bạn
                    binding.txtDayLeft.setText(String.valueOf(diffInDays));
                }
            }
        }
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc hoạt động hiện tại và quay lại trang trước đó
            }
        });

        binding.btnDoiDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doiDiem();
            }
        });
    }

    private void doiDiem() {
        int customerMembershipScore = customer.getMembershipScore();
        int couponScoreMin = coupon.getSCORE_MIN();
        int couponId = coupon.getCOUPON_ID();
        int customerId = customer.getCustomerId();

        // Kiểm tra xem Membership Score của khách hàng có lớn hơn hoặc bằng Score Min của coupon không
        if (customerMembershipScore < couponScoreMin) {
            // Hiển thị cảnh báo "Bạn không đủ điểm để đổi"
//            Toast.makeText(DoiDiem_ChiTiet.this, "Bạn không đủ điểm để đổi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(DoiDiem_ChiTiet.this);
            builder.setTitle("Lỗi đổi điểm nhận Coupon!");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setMessage("Bạn vẫn chưa đủ điểm để đổi " + coupon.getCOUPON_CODE() + "!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }

            });


            Dialog dialog = builder.create();
            dialog.show();
            return;
        }

        // Kiểm tra xem customer_id của customer có trong customer_ids của coupon không
        boolean isCustomerEligible = isCustomerEligibleForCoupon(customerId, couponId);
        if (isCustomerEligible) {
            // Hiển thị cảnh báo "Bạn đã sử dụng rồi"
//            Toast.makeText(DoiDiem_ChiTiet.this, "Bạn đã sử dụng rồi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(DoiDiem_ChiTiet.this);
            builder.setTitle("Lỗi đổi điểm nhận Coupon!");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setMessage("Bạn đã đổi điểm Coupon '" + coupon.getCOUPON_CODE() + "' này rồi nhé!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }

            });


            Dialog dialog = builder.create();
            dialog.show();
            return;
        }

        // Trừ điểm của khách hàng
        int newCustomerScore = customerMembershipScore - couponScoreMin;

        // Cập nhật điểm và hạng thành viên của khách hàng
        db.updateCustomerMembership(customerId, newCustomerScore);

        // Cập nhật customer_id đã sử dụng vào customer_ids của coupon
        ArrayList<Integer> customerIds = coupon.getCustomerIds();
        customerIds.add(customerId);
//        String sql = "UPDATE " + R3cyDB.TBl_COUPON +
//                " SET " + "CUSTOMER_IDS" + " = '" + customerIds + "' " +
//                "WHERE " + "COUPON_ID" + " = " + couponId;
//        db.execSql(sql);

        boolean updated = db.execSql("UPDATE " + R3cyDB.TBl_COUPON + " SET " + "CUSTOMER_IDS" + " = '" + customerIds + "' " + " WHERE " + R3cyDB.COUPON_ID +  " = " + couponId);
        Log.d("CustomerIds", "CustomerIds: " + customerIds);

        if (updated){
            AlertDialog.Builder builder = new AlertDialog.Builder(DoiDiem_ChiTiet.this);
            builder.setTitle("Đổi điểm thành công!");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setMessage("Bạn đã đổi điểm Coupon " + coupon.getCOUPON_CODE() + " thành công. Hãy kiểm tra trong trang tài khoản nhé!");

            Intent intent = new Intent("com.example.r3cy_mobileapp.ACTION_POINTS_ACCUMULATED");
            intent.putExtra("message", "Đổi điểm thành công" + coupon.getCOUPON_CODE());
            // Gửi broadcast
            sendBroadcast(intent);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }

            });


            Dialog dialog = builder.create();
            dialog.show();


        }else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
        }

        // Hiển thị thông báo thành công
//        Toast.makeText(DoiDiem_ChiTiet.this, "Đổi điểm thành công", Toast.LENGTH_SHORT).show();

        reloadData();

    }

    private void reloadData() {
        // Load lại dữ liệu coupon
        loadDataCoupon();

        // Load lại dữ liệu customer
        getDataCustomer();
    }

    private boolean isCustomerEligibleForCoupon(int customerId, int couponId) {
        Cursor cursor = db.getData("SELECT " + R3cyDB.CUSTOMER_IDS + " FROM " + R3cyDB.TBl_COUPON + " WHERE " + R3cyDB.COUPON_ID + " = " + couponId);
        if (cursor != null && cursor.moveToFirst()) {
            String customerIdsString = cursor.getString(0);
            cursor.close();
            if (customerIdsString != null && !customerIdsString.isEmpty()) {
                ArrayList<Integer> customerIds = db.parseCustomerIdsFromString(customerIdsString);
                return customerIds.contains(customerId);
            }
        }
        return false;
    }






    private void getDataCustomer() {
        Cursor c1 = db.getData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER);
        if (c1.moveToFirst()) {
            customer = new Customer(c1.getInt(0),
                    c1.getString(1),
                    c1.getString(2),
                    c1.getInt(3),
                    c1.getString(4),
                    c1.getString(5),
                    c1.getString(6),
                    c1.getInt(7),
                    c1.getString(8),
                    c1.getBlob(9),
                    c1.getString(10)
            );
        }
        c1.close();
    }

    private void loadDataCoupon() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Cursor c = db.getData("SELECT * FROM " + R3cyDB.TBl_COUPON);

        while (c.moveToNext()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date validDate = dateFormat.parse(c.getString(6));
                Date expireDate = dateFormat.parse(c.getString(7));

                ArrayList<Integer> customerIds = new ArrayList<>();
                String customerIdsString = c.getString(12);
                if (customerIdsString != null && !customerIdsString.isEmpty()) {
                    String[] ids = customerIdsString.replaceAll("\\[|\\]", "").split(",\\s*");
                    for (String id : ids) {
                        customerIds.add(Integer.parseInt(id.trim()));
                    }
                }

                coupons.add(new Coupon(
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
                        customerIds
                ));
            } catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        c.close();}



}