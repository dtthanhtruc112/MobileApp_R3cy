package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r3cy_mobileapp.R;

import java.util.List;

public class PhotoViewpager2Adapter extends RecyclerView.Adapter<PhotoViewpager2Adapter.PhotoViewHolder>{
    private List<Integer> photos;

    public PhotoViewpager2Adapter(List<Integer> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Integer imageResourceId = photos.get(position);
        if (imageResourceId == null) {
            return;
        }
        holder.imvThumb.setImageResource(imageResourceId);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imvThumb;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imvThumb = itemView.findViewById(R.id.imvThumb);
        }
    }
}
