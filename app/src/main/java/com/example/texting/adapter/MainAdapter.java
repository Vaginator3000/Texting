package com.example.texting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texting.R;
import com.example.texting.db.containItem;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<containItem> mainArray;

    public MainAdapter(Context context) {
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = mainArray.get(position).getTitleList();
        String date = mainArray.get(position).getDateList();
        holder.setData(title, date);
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;
        private LinearLayout lLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lLayout = itemView.findViewById(R.id.lLayout);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void setData(String title, String date) { tvTitle.setText(title); tvDate.setText(date); }
    }

    public void updateAdapter(List<containItem> newList) {
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }
}
