package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.Signin.Signin_Main;

public class Signup extends AppCompatActivity {

    private R3cyDB db;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewSignIn;
    private ImageView imageViewIconEye1, imageViewIconEye2;
    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new R3cyDB(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewSignIn = findViewById(R.id.textViewSignIn);
        imageViewIconEye1 = findViewById(R.id.imageViewIconEye1);
        imageViewIconEye2 = findViewById(R.id.imageViewIconEye2);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Signin_Main.class);
                startActivity(intent);
                finish();
            }
        });
        imageViewIconEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility1();
            }
        });
        imageViewIconEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility2();
            }
        });
    }

    private void signUp() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra email có đúng định dạng không
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra số điện thoại có lớn hơn 10 chữ số không
        if (!isValidPhoneNumber(password)) {
            Toast.makeText(this, "Số điện thoại phải lớn hơn 10 chữ số", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra độ dài mật khẩu
        if (password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu có chứa chữ cái, số và ký tự đặc biệt
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$")) {
            Toast.makeText(this, "Mật khẩu phải chứa 1 chữ cái, 1 số và 1 ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra email tồn tại trong cơ sở dữ liệu
        if (db.checkEmailExists(email)) {
            Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện đăng ký: chèn thông tin vào cơ sở dữ liệu
        if (db.insertCustomer(fullName, email, password)) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            // Chuyển đến trang đăng nhập
            Intent intent = new Intent(Signup.this, Signin_Main.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    // Phương thức kiểm tra số điện thoại có lớn hơn 10 chữ số
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() >= 10;
    }

    // Phương thức để bật hoặc tắt xem mật khẩu
    private void togglePasswordVisibility1() {
        if (isPasswordVisible1) {
            // Nếu mật khẩu đang hiển thị, chuyển đổi về dạng ẩn mật khẩu
            editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
            isPasswordVisible1 = false;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng ẩn mật khẩu
            imageViewIconEye1.setImageResource(R.drawable.icon_eye);
        } else {
            // Nếu mật khẩu đang ẩn, chuyển đổi về dạng hiển thị mật khẩu
            editTextPassword.setTransformationMethod(null);
            isPasswordVisible1 = true;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng hiển thị mật khẩu
            imageViewIconEye1.setImageResource(R.drawable.icon_eye_hidden);
        }

        // Di chuyển con trỏ tới cuối của EditText
        editTextPassword.setSelection(editTextPassword.getText().length());
    }
    private void togglePasswordVisibility2() {
        if (isPasswordVisible2) {
            // Nếu mật khẩu đang hiển thị, chuyển đổi về dạng ẩn mật khẩu
            editTextConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            isPasswordVisible2 = false;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng ẩn mật khẩu
            imageViewIconEye2.setImageResource(R.drawable.icon_eye);
        } else {
            // Nếu mật khẩu đang ẩn, chuyển đổi về dạng hiển thị mật khẩu
            editTextConfirmPassword.setTransformationMethod(null);
            isPasswordVisible2 = true;
            // Thay đổi biểu tượng của icon_eye thành biểu tượng hiển thị mật khẩu
            imageViewIconEye2.setImageResource(R.drawable.icon_eye_hidden);
        }

        // Di chuyển con trỏ tới cuối của EditText
        editTextConfirmPassword.setSelection(editTextConfirmPassword.getText().length());
    }
}