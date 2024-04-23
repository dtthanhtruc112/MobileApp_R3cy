package com.example.r3cy_mobileapp;

//import static com.example.r3cy_mobileapp.CustomProduct.PICK_IMAGE_REQUEST;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountInfoEditBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserAccount_Info_Edit extends AppCompatActivity {
    ActivityUserAccountInfoEditBinding binding;
    EditText name, username, phone, useremail, gender, birthday;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    Button btnedit, btnCancel;
    String email;
    R3cyDB db;
    boolean openCam;
    UserInfo userInfos;
    BottomNavigationView navigationView;
    ActivityResultLauncher<Intent> activityResultLauncher;

    ImageView thumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountInfoEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = getIntent().getStringExtra("key_email");
//
//        Log.d("SharedPreferences", "Email ở accinfoedit: " + email);

        name = binding.editname;
        username = binding.editusername;
        phone = binding.editphonenumber;
        useremail = binding.editemail;
        gender = binding.editgioitinh;
        birthday = binding.ngaysinh;
        btnedit =binding.btnSave;
        thumb = binding.imvUservatar;
//        btnCancel =binding.btnCancle;

        userInfos = (UserInfo) getIntent().getSerializableExtra("key_userinfo");
        name.setText(userInfos.getFullName());
        username.setText(userInfos.getUserName());
        phone.setText(userInfos.getPhone());
        useremail.setText(userInfos.getEmail());
        birthday.setText(userInfos.getBirthday());
        gender.setText(userInfos.getGender());
        Bitmap bitmap = BitmapFactory.decodeByteArray(userInfos.getThumb(), 0, userInfos.getThumb().length);
        thumb.setImageBitmap(bitmap);

//        imv_useravar.setImageBitmap(userInfos.getThumb());
//        String[] gender1 = {"Nam", "Nữ", "Khác"};
//        ArrayAdapter<String> adapterAddress = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender1);
//        binding.editgioitinh.setAdapter(adapterAddress);

        addEvents();
        // Gọi phương thức để tải thông tin người dùng
//        db = new R3cyDB(this);
//
//        getUserDetails();
//        addEvents();


    }
//    private byte[] getByArrayFromImageView(ImageView imv){
//        BitmapDrawable drawable = (BitmapDrawable) imv.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }
//    public void takePicture(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
//    }
//
//    private void choosePhoto(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == RESULT_OK){
//            if(requestCode == REQUEST_CHOOSE_PHOTO){
//                try {
//                    Uri imageUri = data.getData();
//                    InputStream is = getContentResolver().openInputStream(imageUri);
//
//                    byte[] imageBytes = getBytes(is);
//
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                    ima.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }else if(requestCode == REQUEST_TAKE_PHOTO){
//                Bitmap bitmap = (Bitmap) data.getExtras() .get("data");
//                edtBookImage.setImageBitmap(bitmap);
//
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private byte[] getBytes(InputStream is) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = is.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//
//        return byteBuffer.toByteArray();
//    }

    private void addEvents() {
        navigationView = findViewById(R.id.mn_home);
        navigationView.setSelectedItemId(R.id.item_account);


        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_product){
                    Intent intent1 = new Intent(getApplicationContext(),Product_List.class);
                    intent1.putExtra("key_email", email);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_blog) {
                    Intent intent2 =new Intent(getApplicationContext(),BlogDetail.class);
                    intent2.putExtra("key_email", email);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.item_store) {
                    Intent intent3 =new Intent(getApplicationContext(),AboutUs.class);
                    intent3.putExtra("key_email", email);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_account) {
                    Intent intent4 =new Intent(getApplicationContext(),UserAccount_Main.class);
                    intent4.putExtra("key_email", email);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    return true;
                } else if (item.getItemId() == R.id.item_home) {
                    Intent intent5 =new Intent(getApplicationContext(),TrangChu.class);
                    intent5.putExtra("key_email", email);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;}


        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        binding.iconpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showBottomSheet();
//            }
//        });
    }

//    private void addEvents() {
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
//                intent.putExtra("key_email", email);
//                startActivity(intent);
//            }
//        });
//    }

    public  void  upateUserProfile(View view){
//        String gender1 = (String) binding.editgioitinh.getSelectedItem();

        String name1 = name.getText().toString();
        String username1 = username.getText().toString();
        String phone1 = phone.getText().toString();
        String email = useremail.getText().toString();
        String gender1 = gender.getText().toString();
        String birthday1 = birthday.getText().toString();


        db = new R3cyDB(this);
        boolean b = db.upDateUserProfile(userInfos.getEmail(), name1, username1, phone1, gender1, birthday1);
        if (b){
            Toast.makeText(this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
            Intent intent7 = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
            intent7.putExtra("key_email", email);
            startActivity(intent7);
        }
        else {
            Toast.makeText(this, "Lưu dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAccount_Info_Edit.this, UserAccount_Info.class);
//                intent.putExtra("key_email", email);
//                startActivity(intent);
//            }
//        });

    }

    private byte[] getByteArrayFromImageView(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    private void showBottomSheet() {
        Dialog dialog = new Dialog(UserAccount_Info_Edit.this);
        dialog.setContentView(R.layout.useraccount_avatar_dialog);

        LinearLayout bsCam = dialog.findViewById(R.id.btnCamera);
        bsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open camera
                openCam = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("key_email", email);
                activityResultLauncher.launch(intent);


                dialog.dismiss();
            }
        });
        LinearLayout bsGal = dialog.findViewById(R.id.btnGallery);
        bsGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Gallery
                openCam = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra("key_email", email);
                activityResultLauncher.launch(intent);


                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // xoá background ban đầu
//        dialog.getWindow().setWindowAnimations(R.style.DialogAnimtion);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }



//    private void getUserDetails() {
//        db = new R3cyDB(this);
//        // Kiểm tra xem email có null không
//        if (email != null) {
//            // Lấy thông tin người dùng từ cơ sở dữ liệu
//            ArrayList<UserInfo> customer = db.getLoggedinUserDetails(email);
//
//            // Kiểm tra xem danh sách khách hàng có trống không
//            if (customer != null && customer.size() > 0) {
//                UserInfo userInfo = customer.get(0);
//                name.setText(userInfo.getFullName());
//                username.setText(userInfo.getUserName());
//                phone.setText(userInfo.getPhone());
//                useremail.setText(userInfo.getEmail());
//                gender.setText(userInfo.getGender());
//                birthday.setText(userInfo.getBirthday());
//            } else {
//                // Xử lý trường hợp không tìm thấy thông tin người dùng
//                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Xử lý trường hợp không nhận được email từ Intent
//            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}