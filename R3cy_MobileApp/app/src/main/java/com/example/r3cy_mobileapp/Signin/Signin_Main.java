package com.example.r3cy_mobileapp.Signin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.r3cy_mobileapp.TrangChu;
import com.example.r3cy_mobileapp.UserAccount_Info;
import com.example.r3cy_mobileapp.UserAccount_Main;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class Signin_Main extends AppCompatActivity {
    private R3cyDB db;
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignUp, textViewGoogleSignIn;
    private TextView textViewForgotPassword;
    private ImageView imageViewIconEye;
    private boolean isPasswordVisible = false;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_main);

        db = new R3cyDB(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewGoogleSignIn = findViewById(R.id.textViewGoogleSignIn);
        imageViewIconEye = findViewById(R.id.imageViewIconEye);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Cấu hình Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1099003817418-4jnf7k3nlfdft8g8dc9u4f6ij0epfiev.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Khởi tạo GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> Toast.makeText(Signin_Main.this, "Kết nối với Google thất bại", Toast.LENGTH_SHORT).show())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Xử lý sự kiện khi nhấn vào nút đăng ký với Google
        textViewGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
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

    // Phương thức xử lý đăng ký với Google
    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Xử lý kết quả trả về từ việc đăng ký với Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kết quả trả về từ Intent đăng ký với Google
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Đăng nhập thành công, thực hiện đăng nhập vào Firebase Authentication
                Toast.makeText(this, "Đăng nhập với Google thành công", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = result.getSignInAccount();
                Log.v("SignUpActivity", "success: " + account.getEmail());
                Log.v("SignUpActivity", "success: " + account.getDisplayName());
                // Kiểm tra email tồn tại trong cơ sở dữ liệu
                if (!db.checkEmailExists(account.getEmail())) {
                    db.insertCustomer(account.getDisplayName(), account.getEmail(), "");
                }
                // Chuyển đến trang chủ
                Intent intent = new Intent(Signin_Main.this, TrangChu.class);
                intent.putExtra("key_email", account.getEmail());
                startActivity(intent);
                finish();
            } else {
                // Đăng nhập thất bại
                Toast.makeText(this, "Đăng nhập với Google thất bại", Toast.LENGTH_SHORT).show();
                Log.v("SignUpActivity", "error: " + result.getStatus());
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Xóa SharedPreferences
        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Xóa tất cả các giá trị trong SharedPreferences
        editor.apply();
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

                SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("string", email);
                editor.apply();

                String email1 = preferences.getString("string", "");

                Log.d("SharedPreferences", "Email: " + email1);

                Intent intent = new Intent(Signin_Main.this, TrangChu.class);
                startActivity(intent);



                Log.d("email", "email: " + email);

                finish();
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