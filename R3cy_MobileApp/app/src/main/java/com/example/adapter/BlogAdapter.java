package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Blog;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class BlogAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<Blog> blogList;

    public BlogAdapter(Activity activity, int item_layout, List<Blog> blogList) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.blogList = blogList;
    }


    @Override
    public int getCount() {
        return blogList.size();
    }

    @Override
    public Object getItem(int position) {
        return blogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderBlog holder;
        if (convertView == null) {
            holder = new ViewHolderBlog();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

//            Giao diện
            holder.imvblogThumb = convertView.findViewById(R.id.imvblogThumb);
            holder.txtblogTitle = convertView.findViewById(R.id.txtblogTitle);
            holder.txtblogDate = convertView.findViewById(R.id.txtblogDate);
//            holder.txtblogShortContent = convertView.findViewById(R.id.txtblogShortContent);
            holder.txtblogContent = convertView.findViewById(R.id.txtblogContent);
//            holder.txtblogContent = convertView.findViewById(R.id.txtblogContent);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolderBlog) convertView.getTag();
        }
        //        Binding data
        Blog blog = blogList.get(position);
        holder.txtblogContent.setText(blog.getBlogContent());
        holder.txtblogTitle.setText(blog.getBlogTitle());
        holder.txtblogDate.setText((CharSequence) blog.getBlogDate());

        // Hiển thị hình ảnh từ byte[]
        byte[] imageBytes = blog.getBlogThumb();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imvblogThumb.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            holder.imvblogThumb.setImageResource(R.drawable.dtt_giangsinh1);
        }

        // Trả về convertView đã được cập nhật
        return convertView;
    }
    public static class ViewHolderBlog{
        TextView txtblogTitle, txtblogShortContent, txtblogDate, txtblogContent, txtBlogAuthor;
        ImageView imvblogThumb;
    }
}
