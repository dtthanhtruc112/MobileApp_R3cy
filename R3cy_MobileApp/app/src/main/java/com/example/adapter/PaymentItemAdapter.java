package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.models.CartItem;
import com.example.r3cy_mobileapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentItemAdapter extends ArrayAdapter<CartItem> {
    private Context mContext;
    private int mResource;

    public PaymentItemAdapter(Context context, int resource, List<CartItem> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderPayment holder;

        if (convertView == null) {
            holder = new ViewHolderPayment();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);


//            Giao diện
            holder.imv_ProductImage = convertView.findViewById(R.id.imv_ProductImage);
            holder.txtproductCategory = convertView.findViewById(R.id.txtproductCategory);
            holder.txtproductName = convertView.findViewById(R.id.txtproductName);
            holder.txtproductQuantity = convertView.findViewById(R.id.txtproductQuantity);
            holder.txtproductPrice = convertView.findViewById(R.id.txtproductPrice);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderPayment) convertView.getTag();
        }

        //        Binding data
        CartItem item = getItem(position);

        // Gán dữ liệu từ CartItem vào các thành phần giao diện người dùng trong ViewHolder
        holder.txtproductName.setText(item.getProductName());
        holder.txtproductCategory.setText(item.getProductCategory());
        holder.txtproductQuantity.setText(String.valueOf(item.getProductQuantity()));

        // Định dạng giá sản phẩm thành tiền tệ
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedPrice = numberFormat.format(item.getProductPrice());
        holder.txtproductPrice.setText(formattedPrice);


        // Hiển thị ảnh sản phẩm từ byte array
        byte[] imageBytes = item.getProductThumb();

        if (imageBytes != null) {
            // Decode byte array into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imv_ProductImage.setImageBitmap(bitmap);
        } else {
            // Handle the case where the byte array is null (e.g., set a default image)
            holder.imv_ProductImage.setImageResource(R.drawable.dtt_giangsinh3); // Replace with your default image resource ID
        }
        // Trả về convertView đã được cập nhật
        return convertView;
    }



    public static class ViewHolderPayment{
        TextView txtproductName, txtproductCategory,txtproductPrice, txtproductQuantity;
        ImageView imv_ProductImage;
    }




}
