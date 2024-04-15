package com.example.r3cy_mobileapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageCholayhangBinding;
import com.example.r3cy_mobileapp.databinding.FragmentOrderManageDanggiaoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderManage_danggiao_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManage_danggiao_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentOrderManageDanggiaoBinding binding;

    public OrderManage_danggiao_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderManage_danggiao_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderManage_danggiao_Fragment newInstance(String param1, String param2) {
        OrderManage_danggiao_Fragment fragment = new OrderManage_danggiao_Fragment();
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
        binding = FragmentOrderManageDanggiaoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}