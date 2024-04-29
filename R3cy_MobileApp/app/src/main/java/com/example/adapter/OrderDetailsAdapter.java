package com.example.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Order;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.UserAccount_OrderRating;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderDetailsAdapter extends BaseAdapter {
    Context context;
    int item_quanlydonhang2;
    List<Order> orders;
    String email;
    R3cyDB db;

    public OrderDetailsAdapter(Context context, int item_quanlydonhang2, List<Order> orders) {
        this.context = context;
        this.item_quanlydonhang2 = item_quanlydonhang2;
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
            view = inflater.inflate(item_quanlydonhang2, null);
            holder.orderID = view.findViewById(R.id.order_ID);
            holder.orderProducImg = view.findViewById(R.id.order_productimg);
            holder.orderProductName = view.findViewById(R.id.order_productname);
            holder.orderProductCount = view.findViewById(R.id.order_productcount);
            holder.orderProductPrice = view.findViewById(R.id.order_productprice);


            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Order o = orders.get(position);
        holder.orderProductName.setText(o.getProductName());
        holder.orderProductCount.setText(String.valueOf(o.getQuantity()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        holder.orderProductPrice.setText(numberFormat.format(o.getProductPrice()));

        byte[] productImg = o.getProductImg();
        if (productImg != null) {
            Bitmap bitmap = decodeSampledBitmapFromByteArray(productImg, 50, 50);
            holder.orderProducImg.setImageBitmap(bitmap);
        }
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }




        return view;
    }

    public static Bitmap decodeSampledBitmapFromByteArray ( byte[] byteArray, int reqWidth,
                                                            int reqHeight){
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }

    public static int calculateInSampleSize (
            BitmapFactory.Options options,int reqWidth, int reqHeight){
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static class ViewHolder{
        TextView orderID, orderStatus, orderProductName, orderProductCount, orderProductPrice, orderTotalPrice;
        Button btndanhgia;
        ImageView orderProducImg;
    }
}
