package com.example.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.models.Address;
import com.example.models.Feedback;
import com.example.r3cy_mobileapp.Checkout_AddressList;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class OrderRatingAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<Feedback> feedbacks;



    public OrderRatingAdapter(Activity activity, int item_layout, List<Feedback> feedbacks) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.feedbacks = feedbacks;
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public Object getItem(int position) {
        return feedbacks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderFeedback holder;

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(item_layout, parent, false);

            holder = new ViewHolderFeedback();
            // Ánh xạ các thành phần của layout item vào ViewHolder
            holder.txtFeedbackContent = convertView.findViewById(R.id.txtdanhgiachitiet);
            holder.txtFeedbackRating = convertView.findViewById(R.id.txtproductRating);

            holder.rdbSelected = convertView.findViewById(R.id.rdbSelected);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderFeedback) convertView.getTag();
        }

        // Lấy đối tượng Address tương ứng với vị trí hiện tại
        Feedback feedback = feedbacks.get(position);

        // Đổ dữ liệu từ đối tượng Address vào các thành phần của layout item
        holder.txtFeedbackRating.setText(String.valueOf(feedback.getFeedbackRating()));
        holder.txtFeedbackContent.setText(feedback.getFeedbackContent());




        return convertView;
    }

    public static class ViewHolderFeedback{
        Button rdbSelected;

        TextView txtFeedbackContent, txtFeedbackRating ;
    }

}
