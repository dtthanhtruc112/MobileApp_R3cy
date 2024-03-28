package com.example.r3cy_mobileapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.adapter.ProductAdapter;
import com.example.models.Product;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.databinding.ActivityProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_List extends AppCompatActivity {
    ActivityProductListBinding binding;
    ProductAdapter adapter;

    ArrayList<Product> products;
    RecyclerView rvProducts;
    List<Product> displayedProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initData();

    }



    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.dgd_dia1,1, "Đĩa nhỏ", 100000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc  giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này.", "Đồ gia dụng", "4"));
        products.add(new Product(R.drawable.dgd_xaphong1,2, "Khay đựng xà phòng", 150000, "Khay đựng bánh xà phòng từ nhựa tái chế là sự kết hợp hoàn hảo giữa tính thực tế và cam kết với môi trường. Với nguyên liệu là nhựa tái chế, sản phẩm này không chỉ giúp giảm lượng chất thải nhựa mà còn thể hiện tinh thần chăm sóc đối với hành tinh xanh của chúng ta. Thiết kế của khay đựng bánh xà phòng không chỉ đơn giản mà còn linh hoạt, phù hợp với mọi không gian nhà tắm. Sự sáng tạo trong cách sử dụng nguyên liệu tái chế không chỉ làm cho sản phẩm trở nên độc đáo mà còn đặt ra một tiêu chí mới cho việc chọn lựa sản phẩm gia dụng có trách nhiệm với môi trường. Khay đựng bánh xà phòng từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian nhà tắm mà còn là một bước nhỏ nhưng ý nghĩa trong hành trình chung của chúng ta để bảo vệ và giữ gìn cho hành tinh xanh, trong từng lựa chọn hàng ngày của chúng ta.", "Đồ gia dụng", "5"));
        products.add(new Product(R.drawable.dgd_lotly1,3, "Lót ly", 90000, "Đồ lót ly từ nhựa tái chế là sự kết hợp độc đáo giữa sự thoải mái và cam kết với môi trường. Với việc sử dụng nguyên liệu là nhựa tái chế, sản phẩm không chỉ mang lại cảm giác mềm mại và thoải mái cho người sử dụng mà còn đóng góp vào việc giảm lượng chất thải nhựa. Thiết kế của đồ lót ly không chỉ chú trọng đến sự thoải mái và tôn lên vẻ đẹp tự nhiên, mà còn thể hiện sự chấp nhận trách nhiệm với môi trường. Việc sử dụng nhựa tái chế không chỉ là một xu hướng tiêu dùng thông minh mà còn là sự đóng góp tích cực vào việc bảo vệ nguồn tài nguyên tự nhiên và giảm thiểu ảnh hưởng tiêu cực đối với hệ sinh thái. Đồ lót ly từ nhựa tái chế không chỉ là lựa chọn thông minh cho sự thoải mái hàng ngày mà còn là một cách để chúng ta cùng nhau xây dựng một lối sống thân thiện với môi trường, đồng thời thể hiện sự quan tâm đến sức khỏe và hành tinh xanh của chúng ta. ", "Đồ gia dụng", "5"));
        products.add(new Product(R.drawable.dgd_laptop1,4, "Giá đỡ laptop", 200000, "Giá đỡ laptop từ nhựa tái chế là một phụ kiện không thể thiếu cho những người sử dụng máy tính xách tay, kết hợp giữa tính thực tế và tầm nhìn bền vững. Với sự sáng tạo trong việc sử dụng nhựa tái chế, sản phẩm không chỉ tạo ra một nơi thoải mái để đặt laptop mà còn là cách nhỏ nhưng tích cực để giảm lượng chất thải nhựa. Thiết kế nhẹ nhàng và linh hoạt của giá đỡ không chỉ giúp người dùng duy trì tư duy làm việc hiệu quả mà còn hỗ trợ vào nỗ lực chung của cộng đồng trong việc giữ gìn môi trường. Sự cam kết đối với nhựa tái chế không chỉ là một xu hướng tiêu dùng mà còn là một lối sống, và sản phẩm giá đỡ laptop này là minh chứng rõ ràng cho sự hài hòa giữa tiện ích và sự chấp nhận trách nhiệm với môi trường. Hãy lựa chọn giá đỡ laptop từ nhựa tái chế để không chỉ tận hưởng sự thuận tiện mà còn tham gia vào cuộc hành trình bảo vệ hành tinh của chúng ta.", "Đồ gia dụng", "4"));

        products.add(new Product(R.drawable.dtt_dayco1,5, "Dây cờ trang trí tiệc", 100000, "Dây cờ là một sản phẩm trang trí không thể thiếu cho bất kỳ buổi tiệc nào, và đặc biệt, chúng tôi tự hào giới thiệu dòng sản phẩm dây cờ được làm từ nhựa tái chế. Sự sáng tạo trong thiết kế không chỉ tạo ra không khí vui tươi và phấn khích cho bất kỳ dịp lễ nào mà còn góp phần tích cực vào nỗ lực bảo vệ môi trường. Với việc sử dụng nhựa tái chế, chúng tôi cam kết giảm lượng chất thải nhựa và tái sử dụng nguyên liệu, giữ cho không gian tiệc tùng trở nên thú vị hơn mà không ảnh hưởng đến môi trường. Dây cờ từ nhựa tái chế không chỉ đẹp mắt mà còn là một cách thúc đẩy ý thức về trách nhiệm xã hội và bảo vệ hành tinh xanh chúng ta. Hãy tận hưởng những khoảnh khắc vui vẻ và đồng thời hỗ trợ vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn.", "Đồ trang trí", "4"));
        products.add(new Product(R.drawable.dtt_giangsinh1,6, "Đồ trang trí giáng sinh", 70000, "Đồ trang trí Giáng Sinh 3D không chỉ làm mới không khí của mùa lễ hội mà còn là biểu tượng của sự sang trọng và ý thức về môi trường. Với việc sử dụng nhựa tái chế, sản phẩm này không chỉ tạo ra một không gian lễ hội ấm cúng mà còn đóng góp tích cực vào việc giảm lượng chất thải nhựa. Mỗi chiếc đồ trang trí được chế tạo với kỹ thuật 3D độc đáo, tạo nên hiệu ứng thị giác đặc sắc và sống động, làm tôn lên vẻ đẹp của mùa Giáng Sinh. Việc tái chế nhựa không chỉ giúp giảm tác động tiêu cực đối với môi trường mà còn thúc đẩy ý thức về việc sử dụng tài nguyên tái chế trong sản xuất. Đồ trang trí Giáng Sinh 3D là sự kết hợp hoàn hảo giữa sự sang trọng, sáng tạo và ý thức môi trường. Bằng cách chọn lựa sản phẩm này, chúng ta không chỉ tận hưởng không khí lễ hội phấn khích mà còn thể hiện sự quan tâm đến bảo vệ môi trường và chọn lựa bền vững trong mọi hoạt động.", "Đồ trang trí", "5"));
        products.add(new Product(R.drawable.dtt_dongho1,7, "Đồng hồ treo tường", 350000, "Đồng hồ treo tường từ nhựa tái chế không chỉ là một sản phẩm thời gian mà còn là biểu tượng của sự sáng tạo và tôn trọng đối với môi trường. Với thiết kế độc đáo, sản phẩm này là sự kết hợp hoàn hảo giữa vẻ ngoại hình tinh tế và cam kết với lối sống bền vững. Bằng cách sử dụng nhựa tái chế, đồng hồ treo tường không chỉ giảm lượng rác thải nhựa mà còn giúp tái chế nguyên liệu, đóng góp vào việc bảo vệ môi trường. Sự linh hoạt trong việc tạo hình và màu sắc của sản phẩm này không chỉ làm mới không gian sống mà còn thể hiện tầm quan trọng của việc chọn lựa sản phẩm có trách nhiệm với môi trường. Đồng hồ treo tường từ nhựa tái chế không chỉ đơn thuần là một phụ kiện trang trí, mà còn là biểu tượng của lối sống ý thức về môi trường. Việc đặt mình vào ngôi nhà của bạn không chỉ làm tăng thêm vẻ đẹp mà còn là bước nhỏ nhưng ý nghĩa để góp phần vào việc bảo vệ hành tinh của chúng ta.", "Đồ trang trí", "4"));

        products.add(new Product(R.drawable.pk_bongtai_hcn1,8, "Bông tai hình chữ nhật", 140000, "Bông tai hình chữ nhật không chỉ là một biểu tượng của sự đơn giản và hiện đại trong thế giới thời trang mà còn là một minh chứng cho sự sáng tạo và chăm sóc đối với môi trường. Sản phẩm này được tạo ra từ nhựa tái chế, một lựa chọn thông minh và đảm bảo, giúp giảm lượng chất thải nhựa và bảo vệ nguồn tài nguyên tự nhiên. Thiết kế hình chữ nhật đơn giản nhưng tinh tế của bông tai mang lại sự linh hoạt trong việc kết hợp với nhiều phong cách thời trang khác nhau. Đồng thời, việc sử dụng nhựa tái chế không chỉ giúp giảm áp lực đặt ra cho môi trường mà còn thể hiện cam kết đối với phong cách sống bền vững và tiêu thụ có trách nhiệm. Bông tai hình chữ nhật không chỉ là một chiếc phụ kiện thời trang độc đáo mà còn là biểu tượng của ý thức môi trường. Sử dụng sản phẩm này không chỉ là một cách để thể hiện cái tôi cá nhân mà còn là bước nhỏ nhưng ý nghĩa trong việc góp phần vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn.", "Phụ kiện", "4"));
        products.add(new Product(R.drawable.pk_bongtai_hoa1,9, "Bông tai hình bông hoa", 140000, "Bông tai hình bông hoa là một biểu tượng của sự thanh lịch và sáng tạo, mang đến cho người đeo không chỉ vẻ đẹp tinh tế mà còn là niềm tự hào về việc chọn lựa có trách nhiệm với môi trường. Sản phẩm này được chế tạo từ nhựa tái chế, đó là một bước tiến quan trọng trong việc giảm lượng chất thải nhựa và giữ cho tài nguyên tự nhiên được bảo vệ. Bông hoa tinh tế được tái tạo từ nhựa tái chế không chỉ là một tuyên ngôn về sự đẹp đẽ mà còn là một cam kết vững chắc đối với bảo vệ môi trường. Việc sử dụng nguyên liệu tái chế giúp giảm áp lực đặt ra cho hệ sinh thái và hỗ trợ trong việc xây dựng một tương lai bền vững. Bông tai hình bông hoa không chỉ là một phụ kiện thời trang, mà còn là biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc duy trì sự cân bằng giữa thời trang và bảo vệ môi trường.", "Phụ kiện", "5"));
        products.add(new Product(R.drawable.pk_mockhoacuoi,10, "Móc khóa hình mặt cười", 90000, "Móc khóa hình mặt cười là một phụ kiện vui nhộn và thân thiện với môi trường, được sáng tạo từ nhựa tái chế. Thiết kế này không chỉ mang lại sự hứng khởi với hình ảnh mặt cười thân quen mà còn góp phần giảm lượng chất thải nhựa đối với môi trường. Sử dụng nhựa tái chế là một cam kết đối với bảo vệ hành tinh của chúng ta, tạo ra một sản phẩm không chỉ phản ánh tính cách lạc quan mà còn thể hiện tinh thần chăm sóc môi trường. Mỗi chiếc móc khóa không chỉ là một biểu tượng vui nhộn mà còn là một bước tiến tích cực trong việc hướng tới một lối sống bền vững và ý thức môi trường. Sở hữu một chiếc móc khóa hình mặt cười không chỉ là thêm vào bộ sưu tập phụ kiện cá nhân của bạn mà còn là cách để bạn thể hiện sự quan tâm đến môi trường.", "Phụ kiện", "4"));
        products.add(new Product(R.drawable.pk_mockhoarua1,11, "Móc khóa rùa biển", 90000, "Móc khóa hình rùa biển là một sáng tạo độc đáo kết hợp giữa thiết kế đáng yêu và tôn trọng môi trường. Sản phẩm này làm từ nhựa tái chế, chú trọng đến việc giảm lượng chất thải nhựa và ảnh hưởng tích cực đến bảo vệ hệ sinh thái biển cả. Hình rùa biển được chọn làm điểm nhấn cho móc khóa không chỉ vì sự đáng yêu mà còn vì ý nghĩa mà chúng mang lại trong việc góp phần bảo vệ động vật biển. Sự kết hợp giữa ý thức môi trường và thiết kế sáng tạo khiến cho sản phẩm này trở thành một cách tuyệt vời để thể hiện phong cách cá nhân của bạn trong khi đồng thời chung tay bảo vệ môi trường xanh - nơi rùa biển và nhiều loài động vật khác gọi là nhà.", "Phụ kiện", "5"));

        displayedProductList = new ArrayList<>(products);
        loadData();
        addEvents();
    }


    private void loadData() {
        rvProducts = binding.rvProducts;
        adapter = new ProductAdapter(displayedProductList);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rvProducts.setAdapter(adapter);

    }


    private void addEvents() {
        binding.txtDogiadung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterByCategory("Đồ gia dụng");
            }
        });

        binding.txtDotrangtri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Product> filteredProducts = getProductsByCategory("Đồ trang trí");
//                updateDisplayedProductList(filteredProducts);
                adapter.filterByCategory("Đồ trang trí");
            }
        });

        binding.txtPhukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Product> filteredProducts = getProductsByCategory("Phụ kiện");
//                updateDisplayedProductList(filteredProducts);
                adapter.filterByCategory("Phụ kiện");
            }
        });
    }
    private List<Product> getProductsByCategory(String productCategory) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductCategory().equals(productCategory)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private void updateDisplayedProductList(List<Product> productList) {
        if (displayedProductList == null) {
            displayedProductList = new ArrayList<>();
        } else {
            displayedProductList.clear();
        }
        displayedProductList.addAll(productList);
        adapter.notifyDataSetChanged();
    }



}