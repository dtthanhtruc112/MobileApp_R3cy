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

public class Faqs_chamsoc_Fragment extends Fragment {
    ListView lvchamsoc;
    FaqsAdapter adapter;
    ArrayList<Faqs> faqsModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faqs_chamsoc, container, false);

        lvchamsoc = rootView.findViewById(R.id.lvchamsoc);
        initData();
        loadData();

        return rootView;
    }

    private void initData() {
        faqsModel = new ArrayList<>();
        faqsModel.add(new Faqs(R.drawable.icon_right, "R3cy nhận sửa chữa có tính phí đối với các sản phẩm bị hư hỏng quá thời hạn bảo hành. Chi phí sửa chữa sẽ tùy thuộc vào tình trạng lỗi của sản phẩm.\n" +
                "Ngoài ra, bất cứ lúc nào trong quá trình sử dụng, nếu bạn có vấn đề với sản phẩm của R3cy, xin liên hệ:\n" +
                " • Điện thoại: 0123-456-789\n" +
                " • Email: info@r3cy.com\n" +
                " • Địa chỉ:  Số 669 QL 1, KP. 3, P. Linh Xuân,  TP. Thủ Đức, TP. Hồ Chí Minh", "Phải làm gì khi sản phẩm bị hư hỏng quá thời hạn bảo hành?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "Quy trình nhận và trả sản phẩm bảo hành được thực hiện như sau:\n" +
                " • Bước 1: Khách hàng gửi sản phẩm về lại cho R3cy\n" +
                "   - Khách mua qua website: liên hệ số điện thoại 0123-456-789\n" +
                "   - Khách mua tại đại lý bán hàng: liên hệ gửi trả lại cho đại lý\n" +
                " • Bước 2: R3cy tiếp nhận sản phẩm và thực hiện bảo hành\n" +
                " • Bước 3: Sau khi bảo hành xong, R3cy/ đại lý bán hàng liên hệ với khách để hẹn thời gian và địa điểm giao trả sản phẩm.\n" +
                "Thời gian bảo hành kéo dài từ 7 – 14 ngày làm việc tùy thuộc vào tình trạng sản phẩm.\n" +
                "Lưu ý: Khách hàng vui lòng chi trả chi phí vận chuyển sản phẩm (nếu có phát sinh) trong trường hợp cần giao nhận sản phẩm khi bảo hành.", "Làm thế nào để gửi sản phẩm cần bảo hành cho R3cy?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "Đối với các sản phẩm mới chưa qua sử dụng, trong vòng 10 ngày từ khi nhận được hàng, R3cy áp dụng chính sách đổi sản phẩm 1 đổi 1, hoặc trả sản phẩm để nhận hoàn tiền. Trường hợp sản phẩm không còn hàng để thay thế, R3cy sẽ thay bằng một sản phẩm khác có giá trị và chất\n" +
                "Lưu ý: Khách hàng vui lòng chi trả chi phí vận chuyển sản phẩm (nếu có phát sinh) trong trường hợp cần giao nhận sản phẩm khi đổi trả.", "Sản phẩm sau mua có được đổi trả không?"));
        faqsModel.add(new Faqs(R.drawable.icon_right, "Tất cả sản phẩm của R3cy đều được bảo hành trong vòng 1 năm kể từ ngày mua. Trong thời gian bảo hành, nếu sản phẩm bị lỗi do nhà sản xuất, bạn có thể mang sản phẩm đến cửa hàng của chúng tôi để được đổi trả hoặc sửa chữa miễn phí.\n" +
                "Lưu ý: Khách hàng vui lòng chi trả chi phí vận chuyển sản phẩm (nếu có phát sinh) trong trường hợp cần giao nhận sản phẩm khi bảo hành.", "Sản phẩm của R3cy được bảo hành như thế nào?"));

    }

    private void loadData() {
        adapter = new FaqsAdapter(getActivity(), R.layout.item_faqs, faqsModel); // Thay R.layout.food_layout bằng layout của mỗi item trong ListView của bạn
        lvchamsoc.setAdapter(adapter);
    }

}
