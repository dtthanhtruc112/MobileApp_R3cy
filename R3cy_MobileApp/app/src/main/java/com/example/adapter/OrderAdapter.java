package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Coupon;
import com.example.models.Order;
import com.example.r3cy_mobileapp.DoiDiem_ChiTiet;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.UserAccount_OrderRating;
import com.example.r3cy_mobileapp.User_account_manageOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends BaseAdapter{
    Context context;
    int item_quanlydonhang;
    List<Order> orders;

    public OrderAdapter(Context context, int item_quanlydonhang, List<Order> orders) {
        this.context = context;
        this.item_quanlydonhang = item_quanlydonhang;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_quanlydonhang, null);
            holder.orderID = view.findViewById(R.id.order_ID);
            holder.orderStatus = view.findViewById(R.id.order_status);
            holder.orderProducImg = view.findViewById(R.id.order_productimg);
            holder.orderProductName = view.findViewById(R.id.order_productname);
            holder.orderProductCount = view.findViewById(R.id.order_productcount);
            holder.orderProductPrice = view.findViewById(R.id.order_productprice);
            holder.orderTotalPrice = view.findViewById(R.id.order_totalprice);
            holder.btndanhgia = view.findViewById(R.id.btnorder_danhgia);


            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Order o = orders.get(position);
        holder.orderID.setText(String.valueOf(o.getOrderID()));
        holder.orderStatus.setText(o.getOrderStatus());
        holder.orderProductName.setText(o.getProductName());
        holder.orderProductCount.setText(String.valueOf(o.getQuantity()));
        holder.orderProductPrice.setText(String.valueOf(o.getProductPrice()));
        holder.orderTotalPrice.setText(String.format(Locale.getDefault(), "%.0f đ", o.getTotalOrderValue()));
        holder.btndanhgia.setTag(position);
        holder.btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (int) v.getTag();

                Order clickedOrder = orders.get(clickedPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER", (Serializable) clickedOrder);

                Intent intent = new Intent(context, UserAccount_OrderRating.class);
                intent.putExtra("Package", bundle);
                context.startActivity(intent);
            }
        });


        byte[] productImg = o.getProductImg();
        if (productImg != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productImg, 0, productImg.length);
            holder.orderProducImg.setImageBitmap(bitmap);
        }



        return view;
    }

    public static class ViewHolder{
        TextView orderID, orderStatus, orderProductName, orderProductCount, orderProductPrice, orderTotalPrice;
        Button btndanhgia;
        ImageView orderProducImg;
    }
}
