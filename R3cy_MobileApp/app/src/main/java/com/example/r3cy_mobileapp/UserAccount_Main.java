package com.example.r3cy_mobileapp;


import static android.app.PendingIntent.getActivity;

import static com.example.databases.R3cyDB.CUSTOMER_ID;
import static com.example.databases.R3cyDB.DATABASE_NAME;
import static com.example.databases.R3cyDB.TBL_CUSTOMER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.r3cy_mobileapp.Product.Product_List;
import com.example.r3cy_mobileapp.Signin.Signin_Main;
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UserAccount_Main extends AppCompatActivity {
    ActivityUserAccountMainBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
//    final String DATABASE_NAME = "R3cyDB.db";

    boolean openCam;
    TextView name;
    ImageView thumb;
    String email;
    R3cyDB db;
    ViewPager2 viewPager2;
    User_account_manageOrder activity;
    String email1;
    BottomNavigationView navigationView;

    Button btnChonhinh, btnChuphinh, btnSave;
    ImageView editavar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccountMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        name = binding.txtTen;
        thumb = binding.imvUservatar;
        viewPager2 = findViewById(R.id.view_pager);

        // Nhận giá trị email từ Intent
        email = getIntent().getStringExtra("key_email");
        Log.d("SharedPreferences", "Email ở useracc: " + email);

        // Gọi phương thức để tải thông tin người dùng
//        getUserDetails();
        createDb();
        addEvents();
        addControls();
        addEvent();
//        getUserDetails();
    }

    private void createDb() {
        db = new R3cyDB(this);

        if (db != null) {
            Log.d("Customer", "Database created successfully");
        } else {
            Log.e("Customer", "Failed to create database");
        }
        db.createSampleDataCustomer();
    }

    protected void onResume() {
        super.onResume();
        Log.i("test", "onResume");
        loadData();
//        initUI();
    }

    private void initUI() {
        editavar = findViewById(R.id.imv_uservatar);
//        Intent intent = getIntent();
        int customerId = db.getCustomerIdFromCustomer(email);
        try {
            Cursor cursor = db.getData("SELECT * FROM " + TBL_CUSTOMER + " WHERE " + CUSTOMER_ID + " LIKE '%" + customerId + "%'");

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                byte[] BookImage = cursor.getBlob(1);

                Bitmap bitmap = BitmapFactory.decodeByteArray(BookImage, 0, BookImage.length);
                editavar.setImageBitmap(bitmap);

            } else {
                Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Log.e("EditBook", "Lỗi truy vấn database: " + e.getMessage());
            Toast.makeText(this, "Lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        } finally {
            db.close(); // Đóng database
        }
    }


    private void addEvent() {
        binding.btnChonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();

            }
        });

        binding.btnChuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });



    }
    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);

                    byte[] imageBytes = getBytes(is);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    editavar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(requestCode == REQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras() .get("data");
                editavar.setImageBitmap(bitmap);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }
    private void update() {
        try {
            byte[] imageview = getByArrayFromImageView(editavar);

            db = new R3cyDB(this);

            boolean bo = db.upDateUserImg(imageview, email);
            db.close();

            if (bo) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, UserAccount_Main.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("EditBook", "Lỗi cập nhật dữ liệu: " + e.getMessage());
            Toast.makeText(this, "Lỗi cập nhật, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }



    private byte[] getByArrayFromImageView(ImageView imv){
        BitmapDrawable drawable = (BitmapDrawable) imv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void addControls() {

    }
    public void loadData(){
        if (email == null || email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserAccount_Main.this);
            builder.setMessage("Bạn chưa đăng nhập, vui lòng đăng nhập truy cập vào trang tài khoản");
            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Chuyển đến trang đăng nhập khi nhấn nút Đăng nhập
                    startActivity(new Intent(UserAccount_Main.this, Signin_Main.class));
                }
            });
            builder.setNegativeButton("Về trang chủ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(UserAccount_Main.this, TrangChu.class));

                    // Chuyển về trang chủ khi nhấn cancel
//                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                    Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                    // Set text color for "Ok" button
                    positiveButton.setTextColor(ContextCompat.getColor(UserAccount_Main.this, R.color.blue));

                    // Set text color for "Cancel" button
                    negativeButton.setTextColor(ContextCompat.getColor(UserAccount_Main.this, R.color.blue));
                }
            });

            dialog.show();
        }else{
            getUserDetails();
            initUI();

        }

    }

    private void getUserDetails() {
            db = new R3cyDB(this);

        {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            ArrayList<UserInfo> customer = db.getLoggedinUserDetailsMain(email);

            // Kiểm tra xem danh sách khách hàng có trống không
            if (customer != null && customer.size() > 0) {
                UserInfo userInfo = customer.get(0);
                name.setText(userInfo.getFullName());
//                Bitmap bitmap = BitmapFactory.decodeByteArray(userInfo.getThumb(), 0, userInfo.getThumb().length);
//                thumb.setImageBitmap(bitmap);
            } else {
                // Xử lý trường hợp không tìm thấy thông tin người dùng
                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
//        } else {
//            // Xử lý trường hợp không nhận được email từ Intent
//            Toast.makeText(this, "Không nhận được thông tin email", Toast.LENGTH_SHORT).show();
        }

    }


    private void addEvents() {

//        binding.iconpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showBottomSheet();
//            }
//        });
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
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.usManageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccount_Main.this, User_account_manageOrder.class);
                intent.putExtra("key_email", email);
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
//                // Xóa SharedPreferences
//                SharedPreferences preferences = getSharedPreferences("key_email", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.remove("key_name");
//                editor.apply();

            }
        });
        binding.usScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TichDiem.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.usAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserAccount_Address.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.txtchoxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.btnchoxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.txtdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.txtdanggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.btndanggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });
        binding.txtcholayhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), User_account_manageOrder.class);
                intent.putExtra("key_email", email);
                startActivity(intent);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                intent.putExtra("key_email", email);

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
                intent.putExtra("key_email", email);

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
