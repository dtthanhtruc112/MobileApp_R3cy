package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Faqs;
import com.example.r3cy_mobileapp.R;

import java.util.List;

public class FaqsAdapter extends BaseAdapter {
    Activity context;
    int item_faqs;
    List<Faqs> faqsModels;
    ImageView imageView1;
    TextView txta1;
    boolean isHidden = false;
    boolean[] itemVisibility;



    public FaqsAdapter(Activity context, int item_faqs, List<Faqs> faqsModels) {
        this.context = context;
        this.item_faqs = item_faqs;
        this.faqsModels = faqsModels;
        itemVisibility = new boolean[faqsModels.size()];

        itemVisibility[0] = true;//thiết lập hiển thị item đầu tiên
        // Ẩn các item còn lại
        for (int i = 1; i < faqsModels.size(); i++) {
            itemVisibility[i] = false;
        }

    }

    @Override
    public int getCount() {
        return faqsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return faqsModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FaqsAdapter.ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_faqs, null);
            holder.btnhide= view.findViewById(R.id.btnhide);
            holder.txta1 = view.findViewById(R.id.txta1);
            holder.txtq1 = view.findViewById(R.id.txtq1);
            view.setTag(holder);
        }else{
            holder = (FaqsAdapter.ViewHolder) view.getTag();
        }

        Faqs m = faqsModels.get(position);
        holder.btnhide.setImageResource(m.getBtnhide());
        holder.txta1.setText(m.getTxta1());
        holder.txtq1.setText(m.getTxtq1());

        if (itemVisibility[position]) {
            holder.txta1.setVisibility(View.VISIBLE);
            holder.btnhide.setImageResource(R.drawable.icon_right);
        } else {
            holder.txta1.setVisibility(View.GONE);
            holder.btnhide.setImageResource(R.drawable.icon_up);
        }

        holder.btnhide.setOnClickListener(v -> {

            itemVisibility[position] = !itemVisibility[position];
            notifyDataSetChanged();

        });

        return view;
    }
    public static class ViewHolder {
        ImageView btnhide;
        TextView txta1, txtq1;
    }
}
