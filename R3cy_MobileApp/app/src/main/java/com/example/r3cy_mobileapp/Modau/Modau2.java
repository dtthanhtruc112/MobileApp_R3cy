package com.example.r3cy_mobileapp.Modau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityModau2Binding;

public class Modau2 extends AppCompatActivity {

    ActivityModau2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModau2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Modau2.this, Modau3.class);

                startActivity(intent);
            }
        });
    }
}