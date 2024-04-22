package com.example.r3cy_mobileapp.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapter.CartAdapter;
import com.example.adapter.OrderAdapter;
import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.models.Order;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.User_account_manageOrder;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageTatcaBinding;

import java.lang.reflect.Field;
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

    List<Order> orders;
    Order order;

    R3cyDB dbR3cy;
    OrderAdapter adapter;
    String email;
    Customer customer;



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
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createDb();
        loadData();

    }


    private void createDb() {
        dbR3cy = new R3cyDB(getContext());
        dbR3cy.createSampleDataOrder();
        dbR3cy.createSampleDataOrderLine();
        dbR3cy.createSampleProduct();
    }
    public List<Order> getOrder() {

        String orderStatus = dbR3cy.getOrderStatus("Chờ xử lý");
//         customer = dbR3cy.getCustomerByEmail1(email);
         int customerId = dbR3cy.getCustomerIdFromCustomer(email);
//        Cursor c1 = db.getData("SELECT * FROM " + R3cyDB.TBL_CUSTOMER + " WHERE " + R3cyDB.CUSTOMER_IDS + " LIKE '%" + customerId + "%'");
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = dbR3cy.getReadableDatabase();
        Cursor cursor = null;
//

        try {


                String query = "SELECT " +
                        "o." + R3cyDB.ORDER_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_ORDER_ID + ", " +
                        "ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + ", " +
                        "ol." + R3cyDB.ORDER_SALE_PRICE + ", " +
                        "ol." + R3cyDB.QUANTITY + ", " +
                        "o." + R3cyDB.ORDER_CUSTOMER_ID + ", " +
                        "o." + R3cyDB.TOTAL_ORDER_VALUE + ", " +
                        "o." + R3cyDB.ORDER_STATUS + ", " +
                        "o." + R3cyDB.TOTAL_AMOUNT + ", " +
                        "p." + R3cyDB.PRODUCT_THUMB + ", " +
                        "p." + R3cyDB.PRODUCT_NAME + ", " +
                        "p." + R3cyDB.PRODUCT_PRICE + ", " +
                        "p." + R3cyDB.PRODUCT_ID +
                        " FROM " + R3cyDB.TBl_ORDER + " o " + " " +
                        " INNER JOIN " + R3cyDB.TBl_ORDER_LINE + " ol" +
                        " ON o." + R3cyDB.ORDER_ID + " = ol." + R3cyDB.ORDER_LINE_ORDER_ID + " " +
                        " INNER JOIN " + R3cyDB.TBl_PRODUCT + " p" +
                        " ON ol." + R3cyDB.ORDER_LINE_PRODUCT_ID + " = p." + R3cyDB.PRODUCT_ID +
                        " WHERE o. " + R3cyDB.ORDER_STATUS + " LIKE '%" + orderStatus + "%'AND " +
                        "o." + R3cyDB.ORDER_CUSTOMER_ID + " LIKE '%" + customerId + "%'";


            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int OrderId = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_ID));
                    @SuppressLint("Range") int OrderLineID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_ID));
                    @SuppressLint("Range") int OrderLineProductID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_PRODUCT_ID));
                    @SuppressLint("Range") double OrderSalePrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.ORDER_SALE_PRICE));
                    @SuppressLint("Range") double ProductPrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE));
                    @SuppressLint("Range") String Quantity = cursor.getString(cursor.getColumnIndex(R3cyDB.QUANTITY));
                    @SuppressLint("Range") int OrderCustomerID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_CUSTOMER_ID));
                    @SuppressLint("Range") double TotalOrderValue = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_ORDER_VALUE));
                    @SuppressLint("Range") String OrderStatus = cursor.getString(cursor.getColumnIndex(R3cyDB.ORDER_STATUS));
                    @SuppressLint("Range") double TotalAmount = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_AMOUNT));
                    @SuppressLint("Range") byte[] ProductImg = cursor.getBlob(cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB));
                    @SuppressLint("Range") String ProductName = cursor.getString(cursor.getColumnIndex(R3cyDB.PRODUCT_NAME));

                    Order order = new Order(OrderId, OrderLineID, OrderLineProductID, OrderSalePrice, Quantity, OrderCustomerID, ProductPrice, TotalOrderValue, OrderStatus, TotalAmount, ProductImg, ProductName);
                    orders.add(order);
                } while (cursor.moveToNext());
            } else {
                Log.d("DATABASE", "No data retrieved.");
            }
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error retrieving data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return orders;

    }

    private void loadData() {
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Order> Order;
        orders = (List<Order>) getOrder();
        adapter = new OrderAdapter(getContext(), R.layout.item_quanlydonhang, orders);
        binding.lvOrderTatca.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}