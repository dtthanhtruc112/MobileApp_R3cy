package com.example.r3cy_mobileapp.Fragment;

import static android.content.Intent.getIntent;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.r3cy_mobileapp.databinding.ActivityUserAccountVoucherBinding;
import com.example.r3cy_mobileapp.databinding.FragmentDogiadungBinding;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageDanggiaoBinding;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageTatcaBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderManage_tatca_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManage_tatca_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentOrderManageTatcaBinding binding;

    private List<Order> orders;
    Order order;

    R3cyDB db;
    private OrderAdapter adapter;
    ListView lvProduct;
    String email;

    public OrderManage_tatca_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderManage_tatca_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderManage_tatca_Fragment newInstance(String param1, String param2) {
        OrderManage_tatca_Fragment fragment = new OrderManage_tatca_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderManageTatcaBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        loadData();
//        onResume();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        createDb();
        loadData();

    }


//    private void createDb() {
//        db = new R3cyDB(getContext());
//        db.createSampleDataOrder();
//    }

    private void loadData() {
        orders = new ArrayList<>();
        int customerId = db.getCustomerIdFromCustomer(email);
        if (customerId != -1) {
            ArrayList<Order> orders = new ArrayList<>();
            Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_ORDER);

            while (cursor.moveToNext()) {
                orders.add(new Order(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getBlob(9),
                        cursor.getString(10)
                ));
            }
            cursor.close();


        }


        User_account_manageOrder userAccountManageOrder = new User_account_manageOrder(); // Create an instance of the correct type
        OrderAdapter adapter = new OrderAdapter(userAccountManageOrder, R.layout.item_quanlydonhang, orders);
        binding.lvOrderTatca.setAdapter(adapter);
    }

}