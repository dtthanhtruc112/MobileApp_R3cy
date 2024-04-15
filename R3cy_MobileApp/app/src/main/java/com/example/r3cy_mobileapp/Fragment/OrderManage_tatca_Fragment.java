package com.example.r3cy_mobileapp.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapter.OrderAdapter;
import com.example.adapter.ProductAdapter;
import com.example.databases.R3cyDB;
import com.example.models.Order;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.FragmentDogiadungBinding;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageDanggiaoBinding;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageTatcaBinding;

import java.util.ArrayList;
import java.util.List;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        createDb();
        loadData();

    }


    private void createDb() {
        db = new R3cyDB(getContext());
        db.createSampleDataOrder();
    }

    private void loadData() {
//        orders = new ArrayList<>();
//        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_ORDER);
//
//        while (cursor.moveToNext()){
//            orders.add(new Product(cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getDouble(2),
//                    cursor.getString(3),
//                    cursor.getString(4),
//                    ));
//        }
//        cursor.close();
//
//        adapter = new OrderAdapter(getContext(), R.layout.item_quanlydonhang, orders);
//        binding.lvOrderTatca.setAdapter(adapter);
    }

}