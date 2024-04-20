package com.example.r3cy_mobileapp;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R3cyDB;
import com.example.models.Address;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.Fragment.OrderManage_cholayhang_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_choxuly_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_danggiao_Fragment;
import com.example.r3cy_mobileapp.Fragment.OrderManage_hoanthanh_Fragment;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountMainBinding;

import java.io.IOException;
import java.util.ArrayList;

public class UserAccount_Main extends AppCompatActivity {
    ActivityUserAccountMainBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;

    boolean openCam;
    TextView name;
    String email;
    R3cyDB db;
    ViewPager2 viewPager2;
    User_account_manageOrder activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                if(openCam) {

                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
//
                    binding.imvUservatar.setImageBitmap(photo);
                }else{
//
                    Uri selectedPhotoUri = result.getData().getData();
                    binding.imvUservatar.setImageURI(selectedPhotoUri);
                    }
                }

        });
        name = binding.txtTen;
        viewPager2 = findViewById(R.id.view_pager);


        // Nhận giá trị email từ Intent
        email = getIntent().getStringExtra("key_email");

        // Gọi phương thức để tải thông tin người dùng
        getUserDetails();
        addEvents();
    }

    private void getUserDetails() {
        db = new R3cyDB(this);
        // Lấy email từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
        String email = preferences.getString("string", "");

//        // Nếu không có email từ SharedPreferences, không thực hiện gì cả
//        if (email.isEmpty()) {
//            return;
//        }
//        ArrayList<UserInfo> customer = db.getLoggedinUserDetailsMain(email);
//        // Kiểm tra xem email có null không
        if (email != null) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            ArrayList<UserInfo> customer = db.getLoggedinUserDetailsMain(email);

            // Kiểm tra xem danh sách khách hàng có trống không
            if (customer != null && customer.size() > 0) {
                UserInfo userInfo = customer.get(0);
                name.setText(userInfo.getFullName());
            } else {
                // Xử lý trường hợp không tìm thấy thông tin người dùng
                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp không nhận được email từ Intent
            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
        }

    }


    private void addEvents() {

        binding.iconpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        binding.usInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_Main.this, UserAccount_Info.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.usPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_Main.this, UserAccount_Policy.class);
                startActivity(intent);
            }
        });
        binding.usManageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_Main.this, User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.usVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_Main.this, User_account_voucher.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.usSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Signin_Main.class);
                    startActivity(intent);
                    Toast.makeText(UserAccount_Main.this, "Đăng xuất thành công !", Toast.LENGTH_SHORT).show();
                // Xóa SharedPreferences
                SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("key_name");
                editor.apply();

            }
        });
        binding.usScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TichDiem.class);
                startActivity(intent);
            }
        });
        binding.usAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Checkout_AddressList.class);
                startActivity(intent);
            }
        });
        binding.txtchoxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.btnchoxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.txtdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.txtdanggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.btndanggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });
        binding.txtcholayhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                startActivity(intent);
            }
        });

//        binding.btncholayhang.setOnClickListener(new CholayhangOnClickListener(this));

    }
//    public class CholayhangOnClickListener implements View.OnClickListener {
//        private User_account_manageOrder activity;
//
//        public CholayhangOnClickListener(User_account_manageOrder activity) {
//            this.activity = activity;
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (activity != null) {
//                // Chuyển đến TabLayout và hiển thị Fragment tương ứng
//                activity.showFragment(2); // Vị trí 2 là vị trí của Fragment OrderManage_cholayhang_Fragment()
//            }
//        }
//    }




    private void showBottomSheet() {
        Dialog dialog = new Dialog(UserAccount_Main.this);
        dialog.setContentView(R.layout.useraccount_avatar_dialog);

        LinearLayout bsCam = dialog.findViewById(R.id.btnCamera);
        bsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open camera
                openCam = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
    }
