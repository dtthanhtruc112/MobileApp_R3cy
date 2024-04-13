package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Coupon;
import com.example.r3cy_mobileapp.DoiDiem_ChiTiet;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class CouponAdapterRecycler  extends RecyclerView.Adapter<CouponAdapterRecycler.ViewHolder> {

    Context context;
    List<Coupon> coupons;
//    private View.OnClickListener mListener;

    public CouponAdapterRecycler(Context context, List<Coupon> coupons) {
        this.context = context;
        this.coupons = coupons;
//        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_doidiem_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCode.setText(coupons.get(position).getCOUPON_CODE());
        holder.btnScore.setText(String.valueOf(coupons.get(position).getSCORE_MIN()));

        holder.btnScore.setTag(position);

        holder.btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí của item được chọn từ tag
                int position = (int) v.getTag();

                // Lấy coupon tại vị trí đó
                Coupon clickedCoupon = coupons.get(position);

                // Tạo một Bundle và đưa thông tin của coupon vào
                Bundle bundle = new Bundle();
                bundle.putSerializable("COUPON", clickedCoupon);

                // Tạo Intent để chuyển đến trang mới và đính kèm Bundle
                Intent intent = new Intent(context, DoiDiem_ChiTiet.class);
                intent.putExtra("Package", bundle);
                context.startActivity(intent);
            }
        });


//        holder.itemView.setTag(position); // Đặt tag để có thể xác định vị trí của mục khi được nhấp vào
//        holder.itemView.setOnClickListener(mListener);

    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCode, txtScore;
        Button btnScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtCode);
            btnScore = itemView.findViewById(R.id.btnScore);
        }
    }
}
