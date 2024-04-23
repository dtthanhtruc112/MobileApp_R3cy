package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.example.models.Blog;
import com.example.r3cy_mobileapp.BlogDetail;
import com.example.r3cy_mobileapp.BlogList;
import com.example.r3cy_mobileapp.CartManage;
import com.example.r3cy_mobileapp.R;

import java.text.SimpleDateFormat;
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

            // Giao diện
            holder.imvblogThumb = convertView.findViewById(R.id.imvblogThumb);
            holder.txtblogTitle = convertView.findViewById(R.id.txtblogTitle);
            holder.txtblogDate = convertView.findViewById(R.id.txtblogDate);
            holder.txtblogContent= convertView.findViewById(R.id.txtblogContent);
            holder.txtblogShortContent = convertView.findViewById(R.id.txtblogShortContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderBlog) convertView.getTag();
        }
        // Binding data
        Blog blog = blogList.get(position);
        if(holder.txtblogContent != null){
            holder.txtblogContent.setText(blog.getBlogContent());
        }
        holder.txtblogTitle.setText(blog.getBlogTitle());

        // Lấy câu đầu tiên của nội dung blog
        String blogContentSpanned = blog.getBlogContent();
        String[] sentences = blogContentSpanned.split("[.!?]\\s*"); // Tách nội dung thành các câu

        if (sentences.length > 0) {
            // Lấy câu đầu tiên và gán vào txtblogShortContent
            holder.txtblogShortContent.setText(sentences[0]);
        } else {
            // Nếu không có câu nào, có thể gán một giá trị mặc định hoặc rỗng vào txtblogShortContent
            holder.txtblogShortContent.setText("Chào bạn đến với R3cy");
        }

        // Chuyển đổi và hiển thị ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(blog.getBlogDate());
        holder.txtblogDate.setText(formattedDate);

        // Hiển thị hình ảnh từ byte[]
        byte[] imageBytes = blog.getBlogThumb();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imvblogThumb.setImageBitmap(bitmap);
        } else {
            // Nếu không có hình ảnh, có thể hiển thị một hình ảnh mặc định hoặc ẩn ImageView
            holder.imvblogThumb.setImageResource(R.drawable.dtt_giangsinh1);
        }

        // Xử lý sự kiện click cho imvblogThumb
        holder.imvblogThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int blogId = blogList.get(position).getBlogId();
                ((BlogList) v.getContext()).AddEventForImvThumb(blogId);
            }
        });
        holder.txtblogTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int blogId = blogList.get(position).getBlogId();
                ((BlogList) v.getContext()).AddEventForTitle(blogId);
            }
        });

        // Trả về convertView đã được cập nhật
        return convertView;
    }
    public static class ViewHolderBlog{
        TextView txtblogTitle, txtblogShortContent, txtblogDate, txtblogContent, txtBlogAuthor;
        ImageView imvblogThumb;
        WebView webViewblogShortContent, webViewblogContent;
    }
}
