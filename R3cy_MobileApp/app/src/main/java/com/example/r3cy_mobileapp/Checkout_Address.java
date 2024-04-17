package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.r3cy_mobileapp.databinding.ActivityCheckoutAddressBinding;

public class Checkout_Address extends AppCompatActivity {

    ActivityCheckoutAddressBinding binding ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvent();
    }

    private void addEvent() {
// Khởi tạo Adapter và gắn dữ liệu vào Spinner
        // Khai báo mảng chứa các lựa chọn
        String[] addressTypes = {"nhà riêng", "văn phòng"};
        ArrayAdapter<String> adapterAddress = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, addressTypes);
        binding.addressTypeSpinner.setAdapter(adapterAddress);
    }
}