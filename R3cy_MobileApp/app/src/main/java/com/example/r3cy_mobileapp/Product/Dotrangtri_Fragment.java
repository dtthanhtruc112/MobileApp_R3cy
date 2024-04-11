package com.example.r3cy_mobileapp.Product;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.ProductAdapter;
import com.example.interfaces.ProductInterface;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.FragmentDotrangtriBinding;

import java.util.ArrayList;
import java.util.List;


    public class Dotrangtri_Fragment extends Fragment {

        private ProductAdapter adapter;
        private RecyclerView rvProducts;
        private FragmentDotrangtriBinding binding;
        private ArrayList<Product> products;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentDotrangtriBinding.inflate(inflater, container, false);

            loadData();
            addEvents();

            return binding.getRoot();
        }

        private void loadData() {
//            rvProducts = binding.rvProducts;
            rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            adapter = new ProductAdapter(getContext(), R.layout.viewholder_category_list, initData());
            rvProducts.setAdapter(adapter);
        }

        private void addEvents() {
////            binding.rvProducts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                @Override
//                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                    // Xử lý sự kiện chạm vào item trong RecyclerView
////                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
////                    if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
////                        int position = rv.getChildAdapterPosition(childView);
////                        ProductInterface productInterface = (ProductInterface) requireActivity();
////                        Product selectedProduct = (Product) adapter.getItem(position);
//
//                        // Tạo Intent và gửi thông tin sản phẩm sang Product_Detail Activity
//                        Intent intent = new Intent(requireContext(), Product_Detail.class);
////                        intent.putExtra("product", selectedProduct);
//                        startActivity(intent);
//                    }
//                    return false;
//                }
//
//                @Override
//                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                }
//
//                @Override
//                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                }
//            });
        }

        private List<Product> initData() {
//        products = new ArrayList<>();
            List<Product> products = new ArrayList<>();
            List<Product> filteredProducts = new ArrayList<>();
//            products.add(new Product(R.drawable.dgd_dia1, R.drawable.dgd_dia2, R.drawable.dgd_dia3, 1, "Đĩa nhỏ", 100000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.dgd_xaphong1, R.drawable.dgd_xaphong2, R.drawable.dgd_xaphong3, 2, "Khay đựng xà phòng", 150000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.dgd_lotly1, R.drawable.dgd_lotly2, R.drawable.dgd_lotly3, 3, "Lót ly", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.dgd_laptop1, R.drawable.dgd_laptop2, R.drawable.dgd_latop3, 4, "Giá đỡ laptop", 200000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//
//            products.add(new Product(R.drawable.dtt_dayco1, R.drawable.dtt_dayco2, R.drawable.dtt_dayco3, 1, "Dây cờ trang trí tiệc", 100000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.dtt_giangsinh1, R.drawable.dtt_giangsinh2, R.drawable.dtt_giangsinh3, 1, "Đồ trang trí giáng sinh", 70000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.dtt_dongho1, R.drawable.dtt_dongho2, R.drawable.dtt_dongho3, 1, "Đồng hồ treo tường", 350000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//
//            products.add(new Product(R.drawable.pk_bongtai_hcn1, R.drawable.pk_bongtai_hcn2, R.drawable.pk_bongtai_hcn3, 1, "Bông tai hình chữ nhật", 140000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.pk_bongtai_hoa1, R.drawable.pk_bongtai_hoa2, R.drawable.pk_bongtai_hoa3, 1, "Bông tai hình bông hoa", 140000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.pk_mockhoacuoi, R.drawable.pk_mockhoacuoi, R.drawable.pk_mockhoacuoi, 1, "Móc khóa hình mặt cười", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//            products.add(new Product(R.drawable.pk_mockhoarua1, R.drawable.pk_mockhoarua2, R.drawable.pk_mockhoarua3, 1, "Móc khóa rùa biển", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));

            for (Product product : products) {
                if (product.getCategory().equals("Đồ trang trí")) {
                    filteredProducts.add(product);
                }
            }
            return filteredProducts;
        }
    }