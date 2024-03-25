package com.example.r3cy_mobileapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.faqs_adapter;
import com.example.r3cy_mobileapp.faqs_model;

import java.util.ArrayList;

public class Fags_dathang_thanhtoan_Fragment extends Fragment {
    ListView lvdathang_thanhtoan;
    faqs_adapter adapter;
    ArrayList<faqs_model> faqsModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faqs_dathang_thanhtoan, container, false);

        lvdathang_thanhtoan = rootView.findViewById(R.id.lvdathang_thanhtoan);
        initData();
        loadData();

        return rootView;
    }

    private void initData() {
        faqsModel = new ArrayList<>();
        faqsModel.add(new faqs_model(R.drawable.icon_right, "Trong trường hợp bạn cần thay đổi đơn hàng sau khi đặt, vui lòng liên hệ trực tiếp với R3cy:\n" +
                " • Điện thoại: 0123-456-789\n" +
                " • Email: info..r3cy.com", "Phải làm gì nếu tôi muốn thay đổi đơn hàng?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right, "Để đặt hàng online trên website của R3cy, bạn vui lòng thực hiện theo các bước sau:\n" +
                "\n" +
                " • Truy cập website của R3cy và chọn sản phẩm muốn mua.\n" +
                " • Điền thông tin cá nhân và địa chỉ giao hàng.\n" +
                " • Chọn phương thức thanh toán.\n" +
                " • Xác nhận đơn hàng.\n" +
                " • Sau khi hoàn tất các bước trên, đơn hàng của bạn sẽ được R3cy tiếp nhận và xử lý. Chúng tôi sẽ liên hệ với bạn để xác nhận đơn hàng và giao hàng cho bạn trong thời gian sớm nhất.",
                "Cách thức mua sản phẩm ở R3cy?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Nếu sản phẩm bạn muốn đặt đã hết hàng, bạn có thể thực hiện theo các bước sau:\n" +
                "\n" +
                " • Liên hệ với bộ phận chăm sóc khách hàng của R3cy qua hotline, email hoặc chat trực tiếp trên website.\n" +
                "    Điện thoại: 0123-456-789\n" +
                "    Email: info..r3cy.com\n" +
                " • Thông báo cho bộ phận chăm sóc khách hàng về sản phẩm bạn muốn đặt đã hết hàng.\n" +
                "Bộ phận chăm sóc khách hàng sẽ kiểm tra tình trạng hàng hóa và thông báo cho bạn biết khi nào sản phẩm sẽ có hàng trở lại.\n" +
                "Trong thời gian chờ đợi, bạn có thể lựa chọn các sản phẩm khác của R3cy để đặt mua.","Làm thế nào nếu sản phẩm tôi muốn đặt đã hết hàng?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"Các hình thức thanh toán của R3cy (bao gồm chuyển khoản và tiền mặt) không yêu cầu bạn cung cấp các thông tin thanh toán cá nhân có tính bảo mật.",
                "Thông tin thanh toán cá nhân của tôi có an toàn không?"));
        faqsModel.add(new faqs_model(R.drawable.icon_right,"R3cy hiện đang hỗ trợ các hình thức thanh toán sau:\n" +
                "\n" +
                " • Thanh toán bằng tiền mặt khi nhận hàng\n" +
                " • Thanh toán qua chuyển khoản ngân hàng.\n" +
                " • Thanh toán qua ví điện tử","Cách thức thanh toán khi mua sản phẩm từ R3cy?"));
    }

    private void loadData() {
        adapter = new faqs_adapter(getActivity(), R.layout.item_faqs, faqsModel); // Thay R.layout.food_layout bằng layout của mỗi item trong ListView của bạn
        lvdathang_thanhtoan.setAdapter(adapter);
    }
}
