package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.r3cy_mobileapp.databinding.ActivityPaymentMethodBinding;

public class PaymentMethod extends AppCompatActivity {

    ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.rdbSelectedBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PAYMENT_METHOD", "Bank");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        binding.rdbSelectedMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PAYMENT_METHOD", "Momo");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        binding.rdbSelectedCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PAYMENT_METHOD", "COD");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}