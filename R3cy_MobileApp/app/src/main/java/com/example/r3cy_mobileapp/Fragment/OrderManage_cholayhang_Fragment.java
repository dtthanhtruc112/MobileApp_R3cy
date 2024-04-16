package com.example.r3cy_mobileapp.Fragment;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapter.CouponAdapter;
import com.example.adapter.OrderAdapter;
import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Order;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.User_account_manageOrder;
import com.example.r3cy_mobileapp.User_account_voucher;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageCholayhangBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderManage_cholayhang_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManage_cholayhang_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentOrderManageCholayhangBinding binding;
    R3cyDB db;
    String email;
    OrderAdapter adapter;
    private List<Order> products;
    Order product;

    RecyclerView rvProducts;
    Intent intent;

    public OrderManage_cholayhang_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderManage_cholayhang_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderManage_cholayhang_Fragment newInstance(String param1, String param2) {
        OrderManage_cholayhang_Fragment fragment = new OrderManage_cholayhang_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDb();
//        addEvents();

    }
    private void createDb() {
        db = new R3cyDB(getContext());
        db.createSampleProduct();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("test", "onResume");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderManageCholayhangBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}