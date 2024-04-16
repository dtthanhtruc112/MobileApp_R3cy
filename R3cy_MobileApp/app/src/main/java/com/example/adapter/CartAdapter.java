package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.CartItem;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<CartItem> cartItemList;
    TextView txtTotalAmount;

    public CartAdapter(Activity activity, int item_layout, List<CartItem> cartItemList) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.cartItemList = cartItemList;

    }

    // Thêm phương thức để thiết lập txtTotalAmount
    public void setTxtTotalAmount(TextView txtTotalAmount) {
        this.txtTotalAmount = txtTotalAmount;
    }

    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderCart holder;

        if (convertView == null) {
            holder = new ViewHolderCart();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            holder.txt_ProductName = convertView.findViewById(R.id.txt_ProductName);
            holder.txt_ProductCategory = convertView.findViewById(R.id.txt_ProductCategory);
            holder.txt_ProductPrice = convertView.findViewById(R.id.txt_ProductPrice);
            holder.txtQuantity = convertView.findViewById(R.id.txtQuantity);
            holder.txtDeleteCartItem = convertView.findViewById(R.id.txtDeleteCartItem);
            holder.chk_ProductBuy = convertView.findViewById(R.id.chk_ProductBuy);
            holder.imv_ProductImage = convertView.findViewById(R.id.imv_ProductImage);
            holder.imvDecreaseQuantity = convertView.findViewById(R.id.imvDecreaseQuantity);
            holder.imvIncreaseQuantity = convertView.findViewById(R.id.imvIncreaseQuantity);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderCart) convertView.getTag();
        }
        //        Binding data
        CartItem cartItem = cartItemList.get(position);

        // Gán dữ liệu từ CartItem vào các thành phần giao diện người dùng trong ViewHolder
        holder.txt_ProductName.setText(cartItem.getProductName());
        holder.txt_ProductCategory.setText(cartItem.getProductCategory());
        holder.txtQuantity.setText(String.valueOf(cartItem.getProductQuantity()));
        holder.txt_ProductPrice.setText(String.valueOf(cartItem.getProductPrice()));

        // Hiển thị hình ảnh từ byte[]
        byte[] imageBytes = cartItem.getProductThumb();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imv_ProductImage.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            holder.imv_ProductImage.setImageResource(R.drawable.dtt_giangsinh1);
        }

        // Listen to the checkbox state changes
        holder.chk_ProductBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the checkbox state in CartItem
                cartItem.setSelected(isChecked);
                // Call the method to calculate total amount every time checkbox state changes
                calculateAndDisplayTotalAmount();
            }
        });


        // Trả về convertView đã được cập nhật
        return convertView;
    }
    // Hàm tính và hiển thị tổng tiền
    // Thêm phương thức để tính toán và hiển thị tổng số tiền
    private void calculateAndDisplayTotalAmount() {
        if (txtTotalAmount == null) {
            return; // Kiểm tra nếu txtTotalAmount null thì không thực hiện gì
        }

        double totalAmount = 0;
        for (CartItem item : cartItemList) {
            // Kiểm tra nếu item được chọn (isSelected() trả về true)
            if (item.isSelected()) {
                totalAmount += item.getProductPrice() * item.getProductQuantity();
            }
        }

        // Hiển thị tổng số tiền trên TextView txtTotalAmount
        txtTotalAmount.setText(String.valueOf(totalAmount));
    }




    public static class ViewHolderCart{
        TextView txt_ProductName, txt_ProductCategory, txtQuantity, txt_ProductPrice, txtDeleteCartItem;
        ImageView imv_ProductImage,imvDecreaseQuantity, imvIncreaseQuantity;
        CheckBox chk_ProductBuy;
    }
}
