package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Coupon;
import com.example.models.PromotionBanner;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class PromotionBannerAdapter extends RecyclerView.Adapter<PromotionBannerAdapter.ViewHolder>{

    Context context;
    List<PromotionBanner> banners;

    public PromotionBannerAdapter(Context context, List<PromotionBanner> banners) {
        this.context = context;
        this.banners = banners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_promotion_banner, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtInfo.setText(banners.get(position).getPromotionName());
        holder.imvImage.setImageResource(banners.get(position).getPromotionThumb());
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtInfo;
        ImageView imvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            imvImage = itemView.findViewById(R.id.imvImage);
        }
    }
}
