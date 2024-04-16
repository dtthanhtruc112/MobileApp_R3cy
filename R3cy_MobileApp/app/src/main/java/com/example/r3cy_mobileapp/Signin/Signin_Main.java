package com.example.r3cy_mobileapp.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Customer;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.Signup;
import com.example.r3cy_mobileapp.UserAccount_Info;
import com.example.r3cy_mobileapp.UserAccount_Main;

public class Signin_Main extends AppCompatActivity {
    private R3cyDB db;
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignUp;
    private TextView textViewForgotPassword;
    private ImageView imageViewIconEye;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_main);

        db = new R3cyDB(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        imageViewIconEye = findViewById(R.id.imageViewIconEye);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin_Main.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin_Main.this, ForgotPass_Email.class);
                startActivity(intent);
                finish();
            }
        });
        imageViewIconEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }
    private void signIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Customer customer = db.getCustomerByEmail(email);

        if (customer == null) {
            // Không tìm thấy tài khoản
            Toast.makeText(this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
        } else {
            if (customer.getPassword().equals(password)) {
                // Đăng nhập thành công
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Thực hiện các hoạt động sau khi đăng nhập thành công, ví dụ: chuyển hướng đến màn hình chính
                 Intent intent = new Intent(Signin_Main.this, UserAccount_Main.class);
                 intent.putExtra("key_email", email);
                 startActivity(intent);
                // finish();
            } else {
                // Sai mật khẩu
                Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Nếu mật khẩu đang hiển thị, chuyển đổi về dạng ẩn mật khẩu
            editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
            isPasswordVisible = false;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng ẩn mật khẩu
            imageViewIconEye.setImageResource(R.drawable.icon_eye);
        } else {
            // Nếu mật khẩu đang ẩn, chuyển đổi về dạng hiển thị mật khẩu
            editTextPassword.setTransformationMethod(null);
            isPasswordVisible = true;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng hiển thị mật khẩu
            imageViewIconEye.setImageResource(R.drawable.icon_eye_hidden);
        }

        // Di chuyển con trỏ tới cuối của EditText
        editTextPassword.setSelection(editTextPassword.getText().length());
    }
}