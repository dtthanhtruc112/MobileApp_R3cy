package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Product;
import com.example.r3cy_mobileapp.Product.Product_Detail;
import com.example.r3cy_mobileapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    int viewholder_category_list;

    public ProductAdapter(Context context, int viewholder_category_list, List<Product> products) {
        this.context = context;
        this.viewholder_category_list = viewholder_category_list;
        this.products = products;
    }

    public void setData(List<Product> productList) {
        this.products = productList;
        notifyDataSetChanged(); // Cập nhật lại giao diện người dùng khi dữ liệu thay đổi
    }

    public void setProducts(List<Product> products) {
        this.products = products;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category_list, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

//        holder.imvProductThumb.setImageResource(product.getProductThumb());
        holder.txtProductName.setText(product.getProductName());
        holder.txtSalePrice.setText(String.format(Locale.getDefault(), "%.0f đ", product.getProductPrice()));
        if (holder.txtProductDescription != null) {
            holder.txtProductDescription.setText(product.getProductDescription());
        }
        holder.txtProductRate.setText((int) product.getProductRate());
        holder.txtCategory.setText(product.getCategory());

        // Hiển thị ảnh sản phẩm
        byte[] productThumb = product.getProductThumb();
        if (productThumb != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productThumb, 0, productThumb.length);
            holder.imvProductThumb.setImageBitmap(bitmap);
        }

//        Listener khi click vào item sp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product_Detail.class);
                intent.putExtra("productId", product.getProductId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }
    public Object getItem(int position) {
        return products.get(position);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProductThumb;
        TextView txtProductName, txtSalePrice, txtProductDescription, txtCategory, txtProductRate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imvProductThumb = itemView.findViewById(R.id.imvProductThumb);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtSalePrice = itemView.findViewById(R.id.txtSalePrice);
            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtProductRate = itemView.findViewById(R.id.txtProductRate);

        }
    }}
