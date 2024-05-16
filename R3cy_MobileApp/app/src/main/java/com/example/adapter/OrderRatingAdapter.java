package com.example.adapter;

import static com.example.adapter.OrderDetailsAdapter.decodeSampledBitmapFromByteArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.models.Address;
import com.example.models.Feedback;
import com.example.models.Order;
import com.example.models.order_rating;
import com.example.r3cy_mobileapp.Checkout_AddressList;
import com.example.r3cy_mobileapp.R;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderRatingAdapter extends BaseAdapter {
    Activity activity;
    int item_layout;
    List<order_rating> orderRatings;



    public OrderRatingAdapter(Activity activity, int item_layout, List<order_rating> orderRatings) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.orderRatings = orderRatings;
//        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orderRatings.size();
    }

    @Override
    public Object getItem(int position) {
        return orderRatings.get(position);
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
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            holder.orderID = view.findViewById(R.id.order_ID);
            holder.orderProducImg = view.findViewById(R.id.order_productimgv);
            holder.orderProductName = view.findViewById(R.id.order_productname);
            holder.orderProductCount = view.findViewById(R.id.order_productcount);
            holder.orderProductPrice = view.findViewById(R.id.order_productprice);

            holder.txtFeedbackContent = view.findViewById(R.id.txtdanhgiachitiet);
            holder.txtFeedbackRating = view.findViewById(R.id.txtproductRating);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        order_rating o = orderRatings.get(position);
        holder.orderProductName.setText(o.getProductname());
        holder.orderProductCount.setText(String.valueOf(o.getProductquantity()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        holder.orderProductPrice.setText(numberFormat.format(o.getProductprice()));
//
        byte[] productImg = o.getProductImg();
        if (productImg != null) {
            Bitmap bitmap = decodeSampledBitmapFromByteArray(productImg, 50, 50);
            holder.orderProducImg.setImageBitmap(bitmap);
        }
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.txtFeedbackContent.setText( o.getFeedbackcontent());
        holder.txtFeedbackRating.setText(String.valueOf(o.getFeedbackrating()));






        return view;
    }

    public static class ViewHolder{

        public TextView orderID ,orderProductName,orderProductCount,orderProductPrice,  txtFeedbackContent, txtFeedbackRating ;
        public ImageView orderProducImg;
//        public RatingBar getTxtFeedbackrating() {
//            return txtFeedbackRating;
//        }
//        public RatingBar getTxtFeedbackcontent() {
//            return txtFeedbackContent;
//        }
    }


}
