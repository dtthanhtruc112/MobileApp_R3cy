package com.example.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.databases.R3cyDB;
import com.example.models.Coupon;
import com.example.models.Order;
import com.example.r3cy_mobileapp.DoiDiem_ChiTiet;
import com.example.r3cy_mobileapp.Order_Details;
import com.example.r3cy_mobileapp.R;
import com.example.r3cy_mobileapp.UserAccount_OrderRating;
import com.example.r3cy_mobileapp.User_account_manageOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends BaseAdapter{
    Context context;
    int item_quanlydonhang;
    List<Order> orders;
    String email;
    R3cyDB db;

    public OrderAdapter(Context context, int item_quanlydonhang, List<Order> orders) {
        this.context = context;
        this.item_quanlydonhang = item_quanlydonhang;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_quanlydonhang, null);
            holder.orderID = view.findViewById(R.id.order_ID);
            holder.orderStatus = view.findViewById(R.id.order_status);
            holder.orderProducImg = view.findViewById(R.id.order_productimg);
            holder.orderProductName = view.findViewById(R.id.order_productname);
            holder.orderProductCount = view.findViewById(R.id.order_productcount);
            holder.orderProductPrice = view.findViewById(R.id.order_productprice);
            holder.orderTotalPrice = view.findViewById(R.id.order_totalprice);
            holder.btndanhgia = view.findViewById(R.id.btnorder_danhgia);


            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Order o = orders.get(position);
        holder.orderID.setText(String.valueOf(o.getOrderID()));
        holder.orderStatus.setText(o.getOrderStatus());
        holder.orderProductName.setText(o.getProductName());
        holder.orderProductCount.setText(String.valueOf(o.getQuantity()));
        holder.orderProductPrice.setText(String.valueOf(o.getProductPrice()));
        holder.orderTotalPrice.setText(String.format(Locale.getDefault(), "%.0f đ", o.getTotalOrderValue()));

        holder.orderProductName.setTag(position);
        holder.orderProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (int) v.getTag();

                Order clickedOrder = orders.get(clickedPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER", (Serializable) clickedOrder);
                bundle.putString("key_email", email);

                Intent intent = new Intent(context, Order_Details.class);
                intent.putExtra("Package", bundle);
                intent.putExtra("ORDER_ID", o.getOrderID());
                context.startActivity(intent);
            }
        });


        holder.btndanhgia.setTag(position);

        db = new R3cyDB(context); // Khởi tạo đối tượng R3cyDB


        // Kiểm tra trạng thái đơn hàng và đặt văn bản cho nút tương ứng
        if (o.getOrderStatus().equals("Hoàn thành")) {
            holder.btndanhgia.setText("Đánh giá");
            holder.btndanhgia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = (int) v.getTag();

                    Order clickedOrder = orders.get(clickedPosition);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ORDER", (Serializable) clickedOrder);
                    bundle.putString("key_email", email);

                    Intent intent = new Intent(context, UserAccount_OrderRating.class);
                    intent.putExtra("Package", bundle);
                    context.startActivity(intent);
                }
            });
        } else if (o.getOrderStatus().equals("Chờ xử lý")) {
            holder.btndanhgia.setText("Hủy đơn");
            holder.btndanhgia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_huydon);

                    EditText edtCancle = dialog.findViewById(R.id.edtCancle);
                    Button btnSave = dialog.findViewById(R.id.btnSave);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //                Update data
                            String orderId = String.valueOf(o.getOrderID());
                            boolean updated = db.execSql("UPDATE " + db.TBl_ORDER + " SET " +
                                    db.CANCELLATION_REASON + "='" + edtCancle.getText().toString() + "', " +
                                    db.ORDER_STATUS + "='Đã hủy' WHERE " + db.ORDER_ID + "=" + orderId);


                            if (updated){
                                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();


                }
            });
        } else {
            holder.btndanhgia.setVisibility(View.GONE); // Ẩn nút trong các trạng thái khác
        }


        byte[] productImg = o.getProductImg();
        if (productImg != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productImg, 0, productImg.length);
            holder.orderProducImg.setImageBitmap(bitmap);
        }



        return view;
    }

    public static class ViewHolder{
        TextView orderID, orderStatus, orderProductName, orderProductCount, orderProductPrice, orderTotalPrice;
        Button btndanhgia;
        ImageView orderProducImg;
    }
}
