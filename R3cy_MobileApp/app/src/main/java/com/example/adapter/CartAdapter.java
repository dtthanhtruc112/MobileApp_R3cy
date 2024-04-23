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

import com.example.dao.ProductDao;
import com.example.models.CartItem;
import com.example.models.Coupon;
//import com.example.r3cy_mobileapp.CartManage;
import com.example.r3cy_mobileapp.CartManage;
import com.example.r3cy_mobileapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<CartItem> cartItemList;
    private ProductDao productDao;

    TextView txtTotalAmount;
    private OnQuantityDecreaseListener quantityDecreaseListener;
    private OnQuantityIncreaseListener quantityIncreaseListener;

    public CartAdapter(Activity activity, int item_layout, List<CartItem> cartItemList) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.cartItemList = cartItemList;

    }
    // Phương thức setter cho ProductDao
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
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
    public void setOnQuantityDecreaseListener(OnQuantityDecreaseListener listener) {
        this.quantityDecreaseListener = listener;
    }
    public interface OnQuantityDecreaseListener {
        void onDecrease(int position);
    }
    // Phương thức setter cho sự kiện tăng số lượng
    public void setOnQuantityIncreaseListener(OnQuantityIncreaseListener listener) {
        this.quantityIncreaseListener = listener;
    }
    public interface OnQuantityIncreaseListener {
        void onIncrease(int position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderCart holder;


        if (convertView == null) {
            holder = new ViewHolderCart();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

//            Giao diện
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
        // Định dạng giá sản phẩm thành tiền tệ
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedPrice = numberFormat.format(cartItem.getProductPrice());
        holder.txt_ProductPrice.setText(formattedPrice);


        holder.txtDeleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lineId = cartItem.getLineId(); // Lấy lineId của mục cần xóa
                ((CartManage) v.getContext()).deleteCartItem(lineId);
            }
        });

        // Hiển thị hình ảnh từ byte[]
        byte[] imageBytes = cartItem.getProductThumb();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imv_ProductImage.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            holder.imv_ProductImage.setImageResource(R.drawable.dtt_giangsinh1);
        }


        holder.imvDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityDecreaseListener != null) {
                    quantityDecreaseListener.onDecrease(position);
                    // Call the method to calculate total amount every time checkbox state changes
                    calculateSelectedTotalAmount();
                }
            }
        });

        holder.imvIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityIncreaseListener != null) {
                    quantityIncreaseListener.onIncrease(position);
                    // Call the method to calculate total amount every time checkbox state changes
                    calculateSelectedTotalAmount();
                }
            }
        });

        // Listen to the checkbox state changes
        holder.chk_ProductBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the checkbox state in CartItem
                cartItem.setSelected(isChecked);
                // Call the method to calculate total amount every time checkbox state changes
                calculateSelectedTotalAmount();
            }
        });




        // Trả về convertView đã được cập nhật
        return convertView;
    }
    public double calculateSelectedTotalAmount() {
        double totalAmount = 0;
        for (CartItem item : cartItemList) {
            if (item.isSelected()) {
                totalAmount += item.getProductPrice() * item.getProductQuantity();
            }
        }

        // Cập nhật txtTotalAmount nếu đã được đặt
        if (txtTotalAmount != null) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            txtTotalAmount.setText(numberFormat.format(totalAmount));
        }
        return totalAmount;
    }



    public static class ViewHolderCart{
        TextView txt_ProductName, txt_ProductCategory, txtQuantity, txt_ProductPrice, txtDeleteCartItem;
        ImageView imv_ProductImage,imvDecreaseQuantity, imvIncreaseQuantity;
        CheckBox chk_ProductBuy;
    }
}
