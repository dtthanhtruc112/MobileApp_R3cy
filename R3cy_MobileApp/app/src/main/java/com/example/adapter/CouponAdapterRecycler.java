package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Coupon;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class CouponAdapterRecycler  extends RecyclerView.Adapter<CouponAdapterRecycler.ViewHolder> {

    Context context;
    List<Coupon> coupons;
    private View.OnClickListener mListener;

    public CouponAdapterRecycler(Context context, List<Coupon> coupons, View.OnClickListener mListener) {
        this.context = context;
        this.coupons = coupons;
        this.mListener = mListener;
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
        holder.txtScore.setText(coupons.get(position).getSCORE_MIN());

        holder.itemView.setTag(position); // Đặt tag để có thể xác định vị trí của mục khi được nhấp vào
        holder.itemView.setOnClickListener(mListener);

    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCode, txtScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtCode);
            txtScore = itemView.findViewById(R.id.txtScore);
        }
    }
}
