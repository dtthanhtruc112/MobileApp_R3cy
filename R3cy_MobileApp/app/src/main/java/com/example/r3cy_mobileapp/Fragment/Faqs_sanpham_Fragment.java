package com.example.r3cy_mobileapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.faqs_adapter;
import com.example.r3cy_mobileapp.faqs_model;

import java.util.ArrayList;

public class Faqs_sanpham_Fragment extends Fragment {
    ListView lvsanpham;
    faqs_adapter adapter;
    ArrayList<faqs_model> faqsModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faqs_sanpham, container, false);

        lvsanpham = rootView.findViewById(R.id.lvsp);
        initData();
        loadData();

        return rootView;
    }

    private void initData() {
        faqsModel = new ArrayList<>();
        faqsModel.add(new faqs_model(R.drawable.icon_right, "R3cy cung cấp thông tin chi tiết về nguồn gốc của từng sản phẩm tái chế trên trang web. Bạn có thể xem thông tin này trong mô tả sản phẩm hoặc liên hệ trực tiếp với R3cy để biết thêm chi tiết.",
                "Làm thế nào để tôi biết nguồn gốc của sản phẩm tái chế ?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Cách thức vệ sinh & bảo quản sản phẩm tái chế từ R3cy:\n" +
                "\n" +
                " • Làm sạch: vệ sinh bằng khăn ướt thấm với nước xà phòng & lau lại bằng vải khô\n" +
                " • Giữ dáng túi: hạn chế gập, bẻ túi dẫn đến mất dáng, hư hỏng lớp bạt\n" +
                " • Tránh tiếp xúc với lửa: Lửa không tốt cho túi, không tốt cho laptop. Xin đừng để túi gần lửa.",
                "Làm thế nào để vệ sinh và bảo quản sản phẩm ?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Sản phẩm tái chế được kiểm soát chất lượng và đảm bảo đạt chuẩn cao. Tuy nhiên, do nguyên liệu đã được sử dụng trước đó, có thể có những dấu vết nhỏ hoặc khác biệt nhỏ về màu sắc so với sản phẩm mới.",
        "Sản phẩm tái chế có chất lượng như sản phẩm mới không ?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Số lượng được thiết kế & sản xuất của từng mẫu sản phẩm ở R3cy sẽ thay đổi tùy thuộc vào màu sắc, kích thước cho phép của những tấm bạt vụn, bạt cũ thu về.\n" +
                "Bạn vẫn có thể đặt mua số lượng nhiều hơn 1 (những) sản phẩm mình yêu thích. Trong trường hợp chưa thể cung cấp mẫu sản phẩm đó với số lượng bạn yêu cầu, R3cy sẽ liên hệ để trao đổi chi tiết với bạn.",
                "Có phải mỗi thiết kế của R3cy chỉ có 1 mẫu duy nhất?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Sản phẩm tái chế là sản phẩm được chế tạo từ nguyên liệu tái chế, giúp giảm lượng rác thải và bảo vệ môi trường. Điều này giúp giữ lại giá trị của nguyên liệu và giảm tiêu thụ tài nguyên tự nhiên.",
                "Sản phẩm tái chế là gì và tại sao nó quan trọng ?"));
    }

    private void loadData() {
        adapter = new faqs_adapter(getActivity(), R.layout.item_faqs, faqsModel); // Thay R.layout.food_layout bằng layout của mỗi item trong ListView của bạn
        lvsanpham.setAdapter(adapter);
    }
}
