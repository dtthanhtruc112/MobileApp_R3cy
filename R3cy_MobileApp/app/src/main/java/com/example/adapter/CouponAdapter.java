package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Coupon;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class CouponAdapter extends BaseAdapter {

    Activity activity;
    int item_layout;
    List<Coupon> coupons;


    public CouponAdapter(Activity activity, int item_layout, List<Coupon> coupons) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.coupons = coupons;
    }

    @Override
    public int getCount() {
        return coupons.size();
    }

    @Override
    public Object getItem(int position) {
        return coupons.get(position);
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
            view = inflater.inflate(item_layout, null);

            holder.txtCode = view.findViewById(R.id.txtCode);
            holder.txtTitle = view.findViewById(R.id.txtTitle);
            holder.txtScore = view.findViewById(R.id.txtScore);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Coupon c = coupons.get(position);
        holder.txtCode.setText(c.getCOUPON_CODE());
        holder.txtTitle.setText(c.getCOUPON_TITLE());
        holder.txtScore.setText(String.valueOf(c.getSCORE_MIN()));


        return view;
    }

    public static class ViewHolder{
        TextView txtCode, txtTitle, txtScore;
    }
}
