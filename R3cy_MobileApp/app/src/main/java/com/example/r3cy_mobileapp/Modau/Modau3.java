package com.example.r3cy_mobileapp.Modau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityModau3Binding;

public class Modau3 extends AppCompatActivity {

    ActivityModau3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModau3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Modau3.this, Modau4.class);

                startActivity(intent);
            }
        });
    }
}