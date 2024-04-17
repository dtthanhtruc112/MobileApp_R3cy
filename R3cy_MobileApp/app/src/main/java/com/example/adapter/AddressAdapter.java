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
import com.example.models.Coupon;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<Address> addresses;

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderAddress holder;

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(item_layout, parent, false);

            holder = new ViewHolderAddress();
            // Ánh xạ các thành phần của layout item vào ViewHolder
            holder.txtReceiverName = convertView.findViewById(R.id.txtReceiverName);
            holder.txtReceiverPhone = convertView.findViewById(R.id.txtReceiverPhone);
            holder.txtDetailAddress = convertView.findViewById(R.id.txtDetailAddress);
            holder.txtGeneralAddress = convertView.findViewById(R.id.txtGeneralAddress);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderAddress) convertView.getTag();
        }

        // Lấy đối tượng Address tương ứng với vị trí hiện tại
        Address address = addresses.get(position);

        // Đổ dữ liệu từ đối tượng Address vào các thành phần của layout item
        holder.txtReceiverName.setText(address.getReceiverName());
        holder.txtReceiverPhone.setText(address.getReceiverPhone());
        holder.txtDetailAddress.setText(address.getAddressDetails());
        holder.txtGeneralAddress.setText(address.getWard() + " , " + address.getDistrict() + ", " + address.getProvince());

        return convertView;
    }

    public static class ViewHolderAddress{
        RadioButton rdbSelected;
        TextView txtReceiverName, txtReceiverPhone, txtDetailAddress, txtGeneralAddress, txtDeleteAddress, txtEditAddress;
        Button btnDefaultAddress;
    }
}
