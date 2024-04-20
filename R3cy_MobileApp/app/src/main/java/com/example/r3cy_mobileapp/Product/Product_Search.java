package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.ProductAdapter;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_Search extends AppCompatActivity {
    ActivityProductSearchBinding binding;
    RecyclerView rvProducts;
    ProductAdapter adapter;
    List<Product> products;
    List<Product> productsFiltered;
    private Dialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvProducts = binding.rvProducts;
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
//        adapter = new ProductAdapter(this, initData());
        rvProducts.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.search_view);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Thực hiện tìm kiếm khi người dùng gửi truy vấn
                performSearch(query);
                return true;
            }

            private void performSearch(String query) {
                // Lọc dữ liệu dựa trên truy vấn tìm kiếm
                List<Product> filteredProducts = filterProducts(query);

                // Cập nhật RecyclerView với kết quả đã lọc
                updateRecyclerView(filteredProducts);
            }

            // Lọc sản phẩm dựa trên đối sánh văn bản
            private List<Product> filterProducts(String query) {
                List<Product> filteredProducts = new ArrayList<>();
                for (Product product : products) {
                    if (product.getProductName().toLowerCase().contains(query.toLowerCase()) ||
                            product.getCategory().toLowerCase().contains(query.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
                return filteredProducts;
            }

            // Cập nhật RecyclerView với kết quả đã lọc
            private void updateRecyclerView(List<Product> filteredProducts) {
//                adapter.setProducts(filteredProducts);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                // Thực hiện tìm kiếm từng phần khi người dùng nhập
                performSearch(newQuery);
                return false;
            }
        });

        // Khởi tạo dialog
        filterDialog = new Dialog(this);
        filterDialog.setContentView(R.layout.dialog_filter_search);

        // Lấy view của button filter
        LinearLayout btnFilter = findViewById(R.id.btn_filter);

        // Sự kiện onClickListener cho button filter
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = filterDialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawableResource(R.drawable.rounded_dialog); //
                    window.setGravity(Gravity.BOTTOM); // Đặt dialog ở dưới màn hình
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // Đặt chiều ngang full màn hình
                }
                filterDialog.show();
            }
        });

        // Ánh xạ các TextView trong dialog
        TextView filterPhobien = filterDialog.findViewById(R.id.filter_phobien);
        TextView filterMoinhat = filterDialog.findViewById(R.id.filter_moinhat);
        TextView filterMinPrice = filterDialog.findViewById(R.id.filter_minprice);
        TextView filterMaxPrice = filterDialog.findViewById(R.id.filter_maxprice);

        // Xử lý các sự kiện khi người dùng chọn các tùy chọn trong dialog
        filterPhobien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng chọn tùy chọn "Phổ biến"
            }
        });

        filterMoinhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng chọn tùy chọn "Mới nhất"
            }
        });

        filterMinPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng chọn tùy chọn "Giá: Từ thấp đến cao"
            }
        });

        filterMaxPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng chọn tùy chọn "Giá: Từ cao đến thấp"
            }
        });


    }



    private List<Product> initData() {
        products = new ArrayList<>();
//        products.add(new Product(R.drawable.dgd_dia1, R.drawable.dgd_dia2, R.drawable.dgd_dia3, 1, "Đĩa nhỏ", 100000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.dgd_xaphong1, R.drawable.dgd_xaphong2, R.drawable.dgd_xaphong3, 2, "Khay đựng xà phòng", 150000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.dgd_lotly1, R.drawable.dgd_lotly2, R.drawable.dgd_lotly3, 3, "Lót ly", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.dgd_laptop1, R.drawable.dgd_laptop2, R.drawable.dgd_latop3, 4, "Giá đỡ laptop", 200000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ gia dụng", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//
//        products.add(new Product(R.drawable.dtt_dayco1, R.drawable.dtt_dayco2, R.drawable.dtt_dayco3, 1, "Dây cờ trang trí tiệc", 100000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.dtt_giangsinh1, R.drawable.dtt_giangsinh2, R.drawable.dtt_giangsinh3, 1, "Đồ trang trí giáng sinh", 70000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.dtt_dongho1, R.drawable.dtt_dongho2, R.drawable.dtt_dongho3, 1, "Đồng hồ treo tường", 350000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Đồ trang trí", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//
//        products.add(new Product(R.drawable.pk_bongtai_hcn1, R.drawable.pk_bongtai_hcn2, R.drawable.pk_bongtai_hcn3, 1, "Bông tai hình chữ nhật", 140000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.pk_bongtai_hoa1, R.drawable.pk_bongtai_hoa2, R.drawable.pk_bongtai_hoa3, 1, "Bông tai hình bông hoa", 140000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.pk_mockhoacuoi, R.drawable.pk_mockhoacuoi, R.drawable.pk_mockhoacuoi, 1, "Móc khóa hình mặt cười", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "4", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));
//        products.add(new Product(R.drawable.pk_mockhoarua1, R.drawable.pk_mockhoarua2, R.drawable.pk_mockhoarua3, 1, "Móc khóa rùa biển", 90000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này" , "Phụ kiện", "5", "Sản phẩm này có thể được custom lại theo ý tôi không, tôi muốn đổi màu sản phẩm", "Truc Ho"));

        return products;
    }
}