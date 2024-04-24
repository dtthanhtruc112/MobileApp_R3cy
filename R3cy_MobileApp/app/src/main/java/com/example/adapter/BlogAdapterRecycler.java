package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Blog;
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.BlogList;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class BlogAdapterRecycler extends RecyclerView.Adapter<BlogAdapterRecycler.ViewHolder>{
    Context context;
    List<Blog> blogList;

    private OnItemClickListener mListener;

    // Constructor
    public BlogAdapterRecycler(Context context, List<Blog> blogList, OnItemClickListener listener) {
        this.context = context;
        this.blogList = blogList;
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onImageClick(int blogId);
        void onTitleClick(int blogId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_promotion_banner, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.txtInfo.setText(blog.getBlogTitle());

        // Hiển thị hình ảnh từ byte[]
        byte[] imageBytes = blog.getBlogThumb();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imvImage.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            holder.imvImage.setImageResource(R.drawable.dtt_giangsinh1);
        }

        holder.imvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int blogId = blogList.get(position).getBlogId();
                mListener.onImageClick(blogId);
            }
        });

        holder.txtInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int blogId = blogList.get(position).getBlogId();
                mListener.onTitleClick(blogId);
            }
        });



    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtInfo;
        ImageView imvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            imvImage = itemView.findViewById(R.id.imvImage);
        }
    }
}
