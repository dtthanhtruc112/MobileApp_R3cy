package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Product;
import com.example.r3cy_mobileapp.Product.Product_Detail;
import com.example.r3cy_mobileapp.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    int viewholder_category_list;
    private List<Product> products;
    String email;


    public ProductAdapter(Context context, int viewholder_category_list, List<Product> products, String email) {
        this.context = context;
        this.viewholder_category_list = viewholder_category_list;
        this.products = products;
        this.email = email;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category_list, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(holder.getAdapterPosition());

        Bitmap bmProductThumb = BitmapFactory.decodeByteArray(product.getProductThumb(), 0, product.getProductThumb().length);
        holder.imvProductThumb.setImageBitmap(bmProductThumb);
        holder.txtProductName.setText(product.getProductName());
        holder.txtCategory.setText(product.getCategory());
        holder.txtProductRate.setText(String.format(Locale.getDefault(), "%.1f", product.getProductRate()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        holder.txtSalePrice.setText(numberFormat.format(product.getSalePrice()));


        // Listener khi click vào item sp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem danh sách sản phẩm có phần tử không
                if (products != null && !products.isEmpty()) {
                    // Lấy sản phẩm được click
                    Product clickedProduct = products.get(holder.getAdapterPosition());

                    // Tạo Intent để chuyển sang màn hình chi tiết sản phẩm
                    Intent intent = new Intent(v.getContext(), Product_Detail.class);
                    // Chuyển dữ liệu của sản phẩm qua Intent
                    intent.putExtra("ProductID", product.getProductID());
                    Log.d("SharedPreferences", "Email productadapter: " + email);
                    intent.putExtra("key_email", email);


                    // Khởi chạy Activity mới
                    v.getContext().startActivity(intent);
                }
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

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProductThumb;
        TextView txtProductName, txtSalePrice, txtProductDescription, txtCategory, txtProductRate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);


            imvProductThumb = itemView.findViewById(R.id.imvProductThumb);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtSalePrice = itemView.findViewById(R.id.txtSalePrice);
//            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtProductRate = itemView.findViewById(R.id.txtProductRate);

        }
    }}




