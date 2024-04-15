package com.example.r3cy_mobileapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adapter.FaqsAdapter;
import com.example.models.Faqs;
import com.example.r3cy_mobileapp.R;

import java.util.ArrayList;

public class Faqs_giaohang_Fragment extends Fragment {
    ListView lvgiaohang;
    FaqsAdapter adapter;
    ArrayList<Faqs> faqsModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faqs_giaohang, container, false);

        lvgiaohang = rootView.findViewById(R.id.lvgiaohang);
        initData();
        loadData();

        return rootView;
    }

    private void initData() {
        faqsModel = new ArrayList<>();
        faqsModel.add(new Faqs(R.drawable.icon_right, "Khi đơn hàng của bạn được gửi đi, R3cy sẽ gửi cho bạn một email xác nhận vận chuyển cùng với mã theo dõi. Bạn có thể sử dụng mã này để theo dõi trạng thái vận chuyển của đơn hàng trên trang web hoặc trang web của dịch vụ vận chuyển.", "Làm thế nào để tôi theo dõi vận chuyển của đơn hàng?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "Để thay đổi địa chỉ giao hàng, vui lòng liên hệ với chúng tôi càng sớm càng tốt.\n" +
                "• Điện thoại: 0123-456-789\n" +
                "• Email: info..r3cy.com\n" +
                "• Nếu đơn hàng đã được gửi đi, chúng tôi sẽ không thể thay đổi địa chỉ giao hàng.", "Tôi có thể thay đổi địa chỉ giao hàng sau khi đã đặt đơn hàng không?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "R3cy có giao hàng đến các tỉnh thành khác ngoài TP.HCM, bao gồm các tỉnh miền Nam, miền Trung, miền Bắc.", "R3cy có giao hàng đến các tỉnh thành khác ngoài TP.HCM không?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "Khu vực TP.HCM: thời gian giao nhận hàng từ 1 – 3 ngày làm việc\n" +
                "• Khu vực các tỉnh thành miền Nam: thời gian giao nhận hàng từ 2 – 4 ngày làm việc\n" +
                "• Khu vực các tỉnh thành miền Trung, miền Bắc: thời gian giao nhận hàng từ 3 – 5 ngày làm việc.\n" +
                "• Ngoài ra, bạn cũng có thể đến nhận sản phẩm trực tiếp tại xưởng may của R3cy …., sau khi nhận được cuộc gọi xác nhận đơn hàng.", "Tôi sẽ nhận được sản phẩm trong thời gian bao lâu?"));
        faqsModel.add(new Faqs(R.drawable.icon_right,"Thời gian dự kiến nhận hàng sẽ được hiển thị trong quá trình đặt hàng. Nếu có bất kỳ thay đổi nào, R3cy sẽ thông báo cho bạn qua email hoặc điện thoại.","Làm thế nào để tôi biết thời gian dự kiến nhận hàng sau khi đặt đơn hàng?"));
    }

    private void loadData() {
        adapter = new FaqsAdapter(getActivity(), R.layout.item_faqs, faqsModel); // Thay R.layout.food_layout bằng layout của mỗi item trong ListView của bạn
        lvgiaohang.setAdapter(adapter);

    }
}
