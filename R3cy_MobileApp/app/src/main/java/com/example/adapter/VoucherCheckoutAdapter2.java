package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.models.Voucher;
import com.example.models.VoucherCheckout;
import com.example.r3cy_mobileapp.Cart_Voucher;
import com.example.r3cy_mobileapp.Checkout_AddressList;
import com.example.r3cy_mobileapp.Checkout_Voucher;
import com.example.r3cy_mobileapp.Checkout_Voucher_Detail;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.User_account_voucher_detail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VoucherCheckoutAdapter2 extends BaseAdapter {
    Activity activity;
    int item_voucher;
    List<VoucherCheckout> vouchers;
    String email;

    public interface VoucherSelectionListener {
        void onVoucherSelected(int voucherId);
    }
    private VoucherSelectionListener voucherSelectionListener;


    public VoucherCheckoutAdapter2(Activity activity, int item_layout, List<VoucherCheckout> vouchers, String email) {
        this.activity = activity;
        this.item_voucher = item_layout;
        this.vouchers = vouchers;
        this.email = email;
    }

    @Override
    public int getCount() {
        return vouchers.size();
    }

    @Override
    public Object getItem(int position) {
        return vouchers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolderVoucherCheckout2 holder = new ViewHolderVoucherCheckout2();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_voucher, null);

            holder.txtCode = view.findViewById(R.id.txtCode);
            holder.txtTitle = view.findViewById(R.id.txtTitle);
            holder.txtHSD = view.findViewById(R.id.txtHSD);
            holder.btnDieukien = view.findViewById(R.id.btnDieukien);
            holder.rdbSelected = view.findViewById(R.id.rdbSelected);


            view.setTag(holder);
        } else {
            holder = (ViewHolderVoucherCheckout2) view.getTag();
        }

        VoucherCheckout c = vouchers.get(position);
        holder.txtCode.setText(c.getCOUPON_CODE());
        holder.txtTitle.setText(c.getCOUPON_TITLE());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(c.getEXPIRE_DATE());
        holder.txtHSD.setText(formattedDate);
        holder.btnDieukien.setTag(position);
        holder.rdbSelected.setTag(position);

// RadioButton không được chọn mặc định
        holder.rdbSelected.setChecked(false);
        // Hiển thị RadioButton nếu voucher hợp lệ và đặt màu cho button
        if (vouchers.get(position).isValidVoucher()) {
            holder.rdbSelected.setVisibility(View.VISIBLE);
            holder.btnDieukien.setBackgroundColor(ContextCompat.getColor(holder.btnDieukien.getContext(), android.R.color.transparent));
        } else {
            holder.rdbSelected.setVisibility(View.GONE);
            holder.btnDieukien.setBackgroundColor(ContextCompat.getColor(holder.btnDieukien.getContext(), android.R.color.holo_red_dark));
        }

        holder.rdbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (int) v.getTag();
                VoucherCheckout clickedVoucher = vouchers.get(clickedPosition);
                if (clickedVoucher.isValidVoucher()) {
                    ((Cart_Voucher) v.getContext()).openCartActivity(clickedVoucher);
                } else {
                    // Handle invalid voucher click, if needed
                    Toast.makeText(v.getContext(), "This voucher is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Xử lý sự kiện khi click vào nút đổi điểm
        holder.btnDieukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí từ tag của nút điều kiện
                int clickedPosition = (int) v.getTag();

                // Lấy coupon tương ứng với vị trí
                VoucherCheckout clickedCoupon = vouchers.get(clickedPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("COUPON", (Serializable) clickedCoupon);


                // Tạo một Intent để chuyển đến trang chi tiết và chuyển dữ liệu coupon
                Intent intent = new Intent(activity, Checkout_Voucher_Detail.class);
                intent.putExtra("Package", bundle);
                activity.startActivity(intent);
            }
        });




        return view;
    }

    public static class ViewHolderVoucherCheckout2{
        TextView txtCode, txtTitle, txtScore, txtHSD;
        Button btnDieukien;
        RadioButton rdbSelected;
    }
}
