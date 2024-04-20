package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    int viewholder_category_list;
    private List<Product> products;

    public ProductAdapter(Context context, int viewholder_category_list, List<Product> products) {
        this.context = context;
        this.viewholder_category_list = viewholder_category_list;
        this.products = products;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.viewholder_category_list, null);

        ImageView imvProductThumb = recyclerView.findViewById(R.id.imvProductThumb);
        TextView txtProductName = recyclerView.findViewById(R.id.txtProductName);
        TextView txtCategory = recyclerView.findViewById(R.id.txtCategory);
        TextView txtProductDescription = recyclerView.findViewById(R.id.txtProductDescription);
        TextView txtSalePrice = recyclerView.findViewById(R.id.txtSalePrice);
        TextView txtProductRate = recyclerView.findViewById(R.id.txtProductRate);

        Product product = products.get(position);

        Bitmap bmProductThumb = BitmapFactory.decodeByteArray(product.getProductThumb(), 0, product.getProductThumb().length);
        imvProductThumb.setImageBitmap(bmProductThumb);
        txtProductName.setText(product.getProductName());
        txtCategory.setText(product.getCategory());
        txtProductDescription.setText(product.getProductDescription());
        txtSalePrice.setText(String.format("%.0f", product.getSalePrice()));
        txtProductRate.setText(String.format("%.0f", product.getProductRate()));


//        Listener khi click vào item sp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product_Detail.class);
                intent.putExtra("productID", product.getProductID());
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
            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtProductRate = itemView.findViewById(R.id.txtProductRate);

        }
    }}




