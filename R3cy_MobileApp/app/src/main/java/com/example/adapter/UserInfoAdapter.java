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
import com.example.r3cy_mobileapp.UserAccount_Info_Edit;

import java.util.List;

public class UserInfoAdapter extends BaseAdapter {
    UserAccount_Info activity;

    public UserInfoAdapter(UserAccount_Info_Edit context) {
        this.context = context;
    }

    UserAccount_Info_Edit context;
    int item_layout;
    LayoutInflater inflater;
    List<UserInfo> userInfos;

    public UserInfoAdapter(UserAccount_Info activity, int item_layout, List<UserInfo> userInfos) {
        this.activity = activity;
        this.item_layout = item_layout;
        inflater = LayoutInflater.from(activity);
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
//            view = inflater.inflate(R.layout.user_account_info_edit, parent, false);
//            view = inflater.inflate(item_layout, null);
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
//            holder.txtInfo = view.findViewById(R.id.txtinfo);
            holder.btnchinhsua = view.findViewById(R.id.btnchinhsua);
            holder.fullname = view.findViewById(R.id.editname);
            holder.username = view.findViewById(R.id.editusername);
            holder.phone = view.findViewById(R.id.editphonenumber);
            holder.email = view.findViewById(R.id.editemail);
            holder.gender = view.findViewById(R.id.editgioitinh);
            holder.birthday = view.findViewById(R.id.ngaysinh);


            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        UserInfo userInfo = userInfos.get(position);
//        holder.txtInfo.setText(userInfo.getProductName() + " - " + p.getProductPrice());
        holder.fullname.setText(userInfo.getFullName());
        holder.username.setText(userInfo.getUserName());
        holder.phone.setText(userInfo.getPhone());
        holder.email.setText(userInfo.getEmail());
        holder.gender.setText(userInfo.getGender());
        holder.birthday.setText(userInfo.getBirthday());

//        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.openDialogEdit(p);
//            }
//        });

        holder.btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (context instanceof UserAccount_Info_Edit) {
//                    ((UserAccount_Info_Edit) context).updateData(userInfo);
//                }
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView fullname, username, phone, email, gender, birthday;
        Button btnchinhsua;
    }
}
