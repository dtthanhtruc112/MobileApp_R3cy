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
import com.example.models.ProductVer2;
import com.example.r3cy_mobileapp.Product.Product_Detail;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class ProductAdapterVer2 extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductVer2> products;

    public ProductAdapterVer2(Context context, List<ProductVer2> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder_category_list, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        ProductVer2 product = products.get(position);



        Bitmap bmProductThumb = BitmapFactory.decodeByteArray(product.getProductThumb(), 0, product.getProductThumb().length);
        holder.imvProductThumb.setImageBitmap(bmProductThumb);
        holder.txtProductName.setText(product.getProductName());
        holder.txtCategory.setText(product.getCategory());
//        txtProductDescription.setText(product.getProductDescription());
        holder.txtSalePrice.setText(String.format("%.0f", product.getSalePrice()));
        holder.txtProductRate.setText(String.format("%.0f", product.getProductRate()));


//        Listener khi click vào item sp
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Product_Detail.class);
//                intent.putExtra("productID", product.getProductID());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
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
    }

}
