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
import android.widget.TextView;

import com.example.models.Coupon;
import com.example.models.Voucher;
import com.example.r3cy_mobileapp.DoiDiem_ChiTiet;
import com.example.r3cy_mobileapp.R;

import java.io.Serializable;
import java.util.List;

public class VoucherAdapter extends BaseAdapter {
    Activity activity;
    int item_voucher;
    List<Voucher> vouchers;


    public VoucherAdapter(Activity activity, int item_layout, List<Voucher> vouchers) {
        this.activity = activity;
        this.item_voucher = item_layout;
        this.vouchers = vouchers;
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
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            //holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_voucher, null);

            holder.txtCode = view.findViewById(R.id.txtCode);
            holder.txtTitle = view.findViewById(R.id.txtTitle);
            holder.txtHSD = view.findViewById(R.id.txtHSD);
            holder.btnDoiDiem = view.findViewById(R.id.btnDoiDiem);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Voucher c = vouchers.get(position);
        holder.txtCode.setText(c.getCOUPON_CODE());
        holder.txtTitle.setText(c.getCOUPON_TITLE());
        holder.txtHSD.setText((CharSequence) c.getVALID_DATE());
        // Đặt vị trí vào tag của nút đổi điểm
        holder.btnDoiDiem.setTag(position);

        // Xử lý sự kiện khi click vào nút đổi điểm
        holder.btnDoiDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí từ tag của nút đổi điểm
                int clickedPosition = (int) v.getTag();

                // Lấy coupon tương ứng với vị trí
                Voucher clickedCoupon = vouchers.get(clickedPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("COUPON", (Serializable) clickedCoupon);



                // Tạo một Intent để chuyển đến trang chi tiết và chuyển dữ liệu coupon
                Intent intent = new Intent(activity, DoiDiem_ChiTiet.class);
                intent.putExtra("Package", bundle);
                activity.startActivity(intent);
            }
        });





        return view;
    }

    public static class ViewHolder{
        TextView txtCode, txtTitle, txtScore, txtHSD;
        Button btnDoiDiem;
    }
}
