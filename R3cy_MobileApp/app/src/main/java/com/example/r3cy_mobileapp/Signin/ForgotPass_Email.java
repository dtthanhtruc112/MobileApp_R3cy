package com.example.r3cy_mobileapp.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.r3cy_mobileapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass_Email extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonConfirmEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_email);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail); // ID của EditText trong layout của bạn

        buttonConfirmEmail = findViewById(R.id.buttonConfirmEmail); // ID của Button trong layout của bạn
        buttonConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPass_Email.this, "Email đã được gửi. Vui lòng kiểm tra hộp thư của bạn.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ForgotPass_Email.this, ForgotPass_Newpass.class)); // Chuyển đến activity nhập mật khẩu mới
                                    } else {
                                        Toast.makeText(ForgotPass_Email.this, "Gửi email thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ForgotPass_Email.this, "Vui lòng nhập địa chỉ email của bạn.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}