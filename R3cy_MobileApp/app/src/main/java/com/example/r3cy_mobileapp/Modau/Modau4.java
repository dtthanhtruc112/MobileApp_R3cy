package com.example.r3cy_mobileapp.Modau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.databinding.ActivityModau4Binding;

public class Modau4 extends AppCompatActivity {

    ActivityModau4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModau4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Modau4.this, TrangChu.class);

                startActivity(intent);
            }
        });

        binding.btnOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Modau4.this, TrangChu.class);

                startActivity(intent);
            }
        });

        binding.btnOpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Modau4.this, TrangChu.class);

                startActivity(intent);
            }
        });
    }
}