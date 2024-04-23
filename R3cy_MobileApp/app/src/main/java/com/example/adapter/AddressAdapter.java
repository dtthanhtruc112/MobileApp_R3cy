package com.example.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.models.Address;
import com.example.models.Coupon;
//import com.example.r3cy_mobileapp.CartManage;
import com.example.r3cy_mobileapp.Checkout_AddressList;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<Address> addresses;



    public AddressAdapter(Activity activity, int item_layout, List<Address> addresses) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.addresses = addresses;
    }

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
            holder.txtDefaultAddress = convertView.findViewById(R.id.txtDefaultAddress);
            holder.txtDeleteAddress = convertView.findViewById(R.id.txtDeleteAddress);
            holder.txtEditAddress = convertView.findViewById(R.id.txtEditAddress);
            holder.rdbSelected = convertView.findViewById(R.id.rdbSelected);

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
        holder.txtGeneralAddress.setText(address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince());

        // Kiểm tra nếu là địa chỉ mặc định thì hiển thị txtDefaultAddress, ngược lại ẩn đi
        if (address.getIsDefault() == 1) {
            holder.txtDefaultAddress.setVisibility(View.VISIBLE);
        } else {
            holder.txtDefaultAddress.setVisibility(View.GONE);
        }

        // Xóa địa chỉ
        holder.txtDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int AddressId = address.getAddressId();
                ((Checkout_AddressList) v.getContext()).openDialogDeleteAddress(address);
                }
            });
        holder.txtEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int AddressId = address.getAddressId();
                ((Checkout_AddressList) v.getContext()).openEditAddressActivity(address);
            }
        });
        holder.rdbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int AddressId = address.getAddressId();
                ((Checkout_AddressList) v.getContext()).openCheckoutActivity(address);
            }
        });




        return convertView;
    }

    public static class ViewHolderAddress{
        RadioButton rdbSelected;

        TextView txtReceiverName, txtReceiverPhone, txtDetailAddress, txtGeneralAddress, txtDefaultAddress, txtDeleteAddress, txtEditAddress;
    }
}
