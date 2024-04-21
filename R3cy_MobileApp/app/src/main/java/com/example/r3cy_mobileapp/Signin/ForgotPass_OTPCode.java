package com.example.r3cy_mobileapp.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.r3cy_mobileapp.R;

public class ForgotPass_OTPCode extends AppCompatActivity {
    private EditText[] editTexts = new EditText[4];
    private String code;
    private String email;
    private String otp;
    private Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_otpcode);

        sendButton = findViewById(R.id.sendBtn);
        email = getEmail();
        otp = getOTP();
        editTexts[0] = findViewById(R.id.inputNumber1EditText);
        editTexts[1] = findViewById(R.id.inputNumber2EditText);
        editTexts[2] = findViewById(R.id.inputNumber3EditText);
        editTexts[3] = findViewById(R.id.inputNumber4EditText);

        // Thiết lập lắng nghe sự kiện cho từng ô
        setEditTextListeners();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandleVerificationCode();
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
    private String getOTP() {
        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            return (String) bundle.getSerializable("otp");
        }

        return null;
    }
    private void setEditTextListeners() {
        editTexts[0].requestFocus();

        for (int i = 0; i < editTexts.length; i++) {
            final int currentIndex = i;
            final int nextIndex = (i + 1) % editTexts.length;

            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Trước khi thay đổi
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Trong quá trình thay đổi
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    boolean isPreviousEmpty = currentIndex > 0 && editTexts[currentIndex - 1].getText().toString().isEmpty();
                    boolean isNextEmpty = currentIndex < editTexts.length - 1 && editTexts[currentIndex + 1].getText().toString().isEmpty();
                    boolean isCurrentEmpty = editable.toString().isEmpty();

                    // Sau khi thay đổi
                    if (editable.length() > 0 && currentIndex <= 2 && !isPreviousEmpty) {
                        // Chuyển sang ô kế tiếp
                        editTexts[nextIndex].requestFocus();
                    }
                    else if(editable.length() > 0 && isNextEmpty && !isCurrentEmpty && !isPreviousEmpty) {
                        // Nếu ô hiện tại đã có số, chuyển sang ô kế tiếp
                        editTexts[nextIndex].requestFocus();
                    }
                    else if (isPreviousEmpty){
                        // Nếu ô trước trống thì chuyển về ô trước
                        editTexts[currentIndex - 1 >= 0 ? currentIndex - 1 : 0].requestFocus();
                    }
                    String value1 = editTexts[0].getText().toString();
                    String value2 = editTexts[1].getText().toString();
                    String value3 = editTexts[2].getText().toString();
                    String value4 = editTexts[3].getText().toString();
                    code = value1 + value2 + value3 + value4;
                }
            });

            // Nếu người dùng xoá hết số trong ô, chuyển về ô trước đó
            int finalI = i;
            editTexts[i].setOnKeyListener((view, keyCode, event) -> {
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && editTexts[finalI].getText().toString().equals("")) {
                    editTexts[currentIndex - 1 >= 0 ? currentIndex - 1 : 0].requestFocus();
                    return true;
                } // Nếu ô hiện tại đã có số, chuyển sang ô kế tiếp
                else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9 && event.getAction() == KeyEvent.ACTION_DOWN && !editTexts[finalI].getText().toString().equals("") && currentIndex <= 2) {
                    editTexts[nextIndex].requestFocus();
                    return true;
                }
                return false;
            });
        }
    }
    private void HandleVerificationCode() {
        code = String.valueOf(code);
        // Kiểm tra xem mã OTP nhập vào có trùng khớp với mã OTP đã gửi đi hay không
        if (code.equals(otp)) {
            // Mã OTP trùng khớp, chuyển sang trang nhập mật khẩu mới
            Intent intent = new Intent(ForgotPass_OTPCode.this, ForgotPass_Newpass.class);
            // Truyền email cho activity nhập mật khẩu mới
            intent.putExtra("email", email);
            startActivity(intent);
            finish(); // Kết thúc activity hiện tại
        } else {
            // Mã OTP không trùng khớp, hiển thị thông báo lỗi
            Toast.makeText(this, "Mã OTP không chính xác. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}