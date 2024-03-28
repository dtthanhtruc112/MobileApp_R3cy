package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Product;
import com.example.r3cy_mobileapp.Product.Product_detail;
import com.example.r3cy_mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> displayedProductList;
    private List<Product> productList;
    private OnItemClickListener itemClickListener;


    public ProductAdapter(List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.displayedProductList = new ArrayList<>(productList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = displayedProductList.get(position);
        holder.imvThumb.setImageResource(product.getProductThumb());
        if (holder.txtProductName != null) {
            holder.txtProductName.setText(product.getProductName());
        }

        if (holder.txtProductPrice != null) {
            holder.txtProductPrice.setText(String.valueOf(product.getProductPrice()));
        }

        if (holder.txtProductDescription != null) {
            holder.txtProductDescription.setText(product.getProductDescription());
        }

        if (holder.txtProductRate != null) {
            holder.txtProductRate.setText(product.getProductRate());
        }

        if (holder.txtProductCategory != null) {
            holder.txtProductCategory.setText(product.getProductCategory());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(product);
                }

//                chuyển sang ProductDetailActivity và truyền thông tin sản phẩm
                Intent intent = new Intent(v.getContext(), Product_detail.class);
                intent.putExtra("product", product);
                v.getContext().startActivity(intent);
            }

        });
    }
    public void filterByCategory(String productCategory) {
        displayedProductList.clear();
        if (productList != null) { // Kiểm tra productList khác null trước khi sử dụng
            for (Product product : productList) {
                if (product.getProductCategory().equals(productCategory)) {
                    displayedProductList.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return displayedProductList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvThumb;
        TextView txtProductName, txtProductPrice, txtProductDescription, txtProductCategory, txtProductRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvThumb = itemView.findViewById(R.id.imvThumb);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            txtProductCategory = itemView.findViewById(R.id.txtProductCategory);
            txtProductRate = itemView.findViewById(R.id.txtProductRate);
        }
    }
}
