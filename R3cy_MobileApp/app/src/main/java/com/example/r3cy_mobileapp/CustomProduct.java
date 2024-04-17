package com.example.r3cy_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.r3cy_mobileapp.Signin.Signin_Main;

import java.io.IOException;

public class CustomProduct extends AppCompatActivity {

    private R3cyDB db;
    private EditText edtCustomerName, edtEmail, edtPhone, edtProductCusName;
    private Button btnSummitCustom;
    private TextView btnProductFile;
    private Uri imageUri;
    private ImageView imvTrangChu;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_product);

        // Khởi tạo đối tượng cơ sở dữ liệu
        db = new R3cyDB(this);

        // Ánh xạ các thành phần trong layout
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtProductCusName = findViewById(R.id.edtProductCusName);
        btnSummitCustom = findViewById(R.id.btnSummitCustom);
        btnProductFile = findViewById(R.id.btnProductFile);

        imvTrangChu = findViewById(R.id.imvTrangChu);

        imvTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomProduct.this, TrangChu.class);
                startActivity(intent);
                finish();
            }
        });

        // Thiết lập sự kiện cho TextView để chọn ảnh từ điện thoại
        btnProductFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để chọn ảnh từ điện thoại
                chooseImage();
            }
        });

        // Thiết lập sự kiện cho nút SummitCustom
        btnSummitCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đã nhập
                String customerName = edtCustomerName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String productCusName = edtProductCusName.getText().toString();

                // Kiểm tra xem các trường thông tin đã được điền đầy đủ chưa
                if (TextUtils.isEmpty(customerName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(productCusName)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có đúng định dạng không
                if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem số điện thoại có đúng định dạng không
                if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem đã chọn hình ảnh chưa
                if (imageUri != null) {
                    // Nếu đã chọn hình ảnh, lưu thông tin vào cơ sở dữ liệu
                    saveToDatabase(customerName, email, phone, productCusName, imageUri);
                } else {
                    // Nếu chưa chọn hình ảnh, hiển thị thông báo cho người dùng
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Phương thức kiểm tra định dạng số điện thoại
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^\\d{10,}$");
    }

    // Phương thức để chọn ảnh từ điện thoại
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }


    // Hiển thị hình ảnh đã chọn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra nếu requestCode là PICK_IMAGE_REQUEST và resultCode là RESULT_OK
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy URI của hình ảnh đã chọn
            imageUri = data.getData();

            try {
                // Đặt hình ảnh đã chọn vào ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Phương thức để lưu thông tin vào cơ sở dữ liệu
    private void saveToDatabase(String customerName, String email, String phone, String productCusName, Uri imageUri) {
        // Khởi tạo một đối tượng ContentValues để lưu các cặp key-value
        ContentValues values = new ContentValues();
        values.put(R3cyDB.CUSTOMPRODUCT_NAME, customerName);
        values.put(R3cyDB.CUSTOMPRODUCT_EMAIL, email);
        values.put(R3cyDB.CUSTOMPRODUCT_PHONE, phone);
        values.put(R3cyDB.CUSTOMPRODUCT_TITLE, productCusName);
        values.put(R3cyDB.CUSTOMPRODUCT_DESFILE, imageUri.toString());

        // Thực hiện lưu dữ liệu vào cơ sở dữ liệu
        long newRowId = db.insertData(customerName, email, phone, productCusName, imageUri.toString());

        // Kiểm tra xem dữ liệu đã được lưu thành công hay không
        if (newRowId != -1) {
            // Nếu lưu thành công, hiển thị thông báo
            Toast.makeText(getApplicationContext(), "Bạn đã gửi yêu cầu custom thành công.", Toast.LENGTH_SHORT).show();

            // Reload lại trang
            Intent intent = getIntent();
            finish(); // Đóng Activity hiện tại
            startActivity(intent); // Mở lại Activity
        } else {
            // Nếu lưu không thành công, hiển thị thông báo
            Toast.makeText(getApplicationContext(), "Lỗi! Yêu cầu custom chưa được gửi.", Toast.LENGTH_SHORT).show();
        }

    }

}