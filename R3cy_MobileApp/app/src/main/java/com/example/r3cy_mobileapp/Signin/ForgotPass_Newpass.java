package com.example.r3cy_mobileapp.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.R;

public class ForgotPass_Newpass extends AppCompatActivity {
    private String email;
    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button completeButton;
    private R3cyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_newpass);

        db = new R3cyDB(this);
        email = getEmail();

        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        completeButton = findViewById(R.id.completeButton);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ForgotPass_Newpass.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ForgotPass_Newpass.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu mật khẩu mới vào cơ sở dữ liệu
                boolean isUpdated = db.updatePasswordByEmail(email, newPassword);
                if (isUpdated) {
                    Toast.makeText(ForgotPass_Newpass.this, "Mật khẩu đã được cập nhật", Toast.LENGTH_SHORT).show();
                    // Chuyển về trang đăng nhập sau khi cập nhật mật khẩu thành công
                    Intent intent = new Intent(ForgotPass_Newpass.this, Signin_Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ForgotPass_Newpass.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getEmail() {
        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            return (String) bundle.getSerializable("email");
        }

        return null;
    }
}