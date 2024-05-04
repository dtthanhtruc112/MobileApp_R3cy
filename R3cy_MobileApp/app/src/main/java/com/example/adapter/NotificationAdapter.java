package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.r3cy_mobileapp.Notification;
import com.example.r3cy_mobileapp.R;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String[]> notifications;

//    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
//        this.context = context;
//        this.notifications = notifications;
//    }

    public NotificationAdapter(Notification context, ArrayList<String[]> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.headerTextView = convertView.findViewById(R.id.notification_header);
            viewHolder.contentTextView = convertView.findViewById(R.id.notification_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String[] notification = notifications.get(position);
        viewHolder.headerTextView.setText(notification[0]);
        viewHolder.contentTextView.setText(notification[1]);

        return convertView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.item_notification, null);
//        }
//
//        TextView messageTextView = convertView.findViewById(R.id.notification_message);
////        TextView timeTextView = convertView.findViewById(R.id.notification_time);
//
//        String notification = String.valueOf(notifications.get(position));
//
//        messageTextView.setText(notification);
////        timeTextView.setText(String.format(Locale.getDefault(), "Thời gian: %tT", System.currentTimeMillis()));
//
//        return convertView;
    }
    static class ViewHolder {
        TextView headerTextView;
        TextView contentTextView;
    }
}

