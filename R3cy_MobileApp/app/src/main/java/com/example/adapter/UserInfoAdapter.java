package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.UserAccount_Info;

import java.util.List;

public class UserInfoAdapter extends BaseAdapter {
    UserAccount_Info activity;
    int item_layout;
    List<UserInfo> userInfos;

    public UserInfoAdapter(UserAccount_Info activity, int item_layout, List<UserInfo> userInfos) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.userInfos = userInfos;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
//            holder.txtInfo = view.findViewById(R.id.txtinfo);
            holder.btnchinhsua = view.findViewById(R.id.btnchinhsua);


            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        UserInfo userInfo = userInfos.get(position);
//        holder.txtInfo.setText(userInfo.getProductName() + " - " + p.getProductPrice());

//        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.openDialogEdit(p);
//            }
//        });

        holder.btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openDialogEdit(userInfo);
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView txtInfo;
        Button btnchinhsua;
    }
}
