package com.example.texting.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texting.EditActivity;
import com.example.texting.R;
import com.example.texting.db.MyConstants;
import com.example.texting.db.containItem;
import com.example.texting.db.dbManager;

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
        return new MyViewHolder(view, context, mainArray);
    }

//    //Сравниваем, дата в заметке позже или раньше сегодняшней
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public boolean CompareDate() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate localDate = LocalDate.now();
//    //    System.out.println(dtf.format(localDate));
//        Log.d("MyLog", dtf.format(localDate));
//        return true;
//    }

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

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvDate;
    //    private LinearLayout lLayout;
        Context context;
        private List<containItem> mainArray;
        private SharedPreferences pref;


        public MyViewHolder(@NonNull View itemView, Context context,List<containItem> mainArray) {
            super(itemView);
            this.context = context;
            this.mainArray = mainArray;
     //       lLayout = itemView.findViewById(R.id.lLayout);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);

            setTextColor();
        }

        public void setTextColor() {
            //Устанавливаем цвет текста
            pref = PreferenceManager.getDefaultSharedPreferences(context);
            try {
                int color = pref.getInt("color_picker2", Color.BLACK);
                tvDate.setTextColor(color);
                tvTitle.setTextColor(color);
            } catch (Exception e) {
                Log.d("MyLog", "MainAdapter - setTextColor");
                Log.d("MyLog", e.toString());
            }

        }

        public void setData(String title, String date) { tvTitle.setText(title); tvDate.setText(date); }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra(MyConstants.CONTAIN_ITEM_INTENT, mainArray.get(getAdapterPosition()));
            i.putExtra(MyConstants.EDIT_STATE, false);
            context.startActivity(i);

        }
    }

    public void updateAdapter(List<containItem> newList) {
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void delItem(int position, dbManager dbM) {
        dbM.deleteFromDb(String.valueOf(mainArray.get(position).getID()));
        mainArray.remove(position);
        notifyItemRangeChanged(0,mainArray.size());
        notifyItemRemoved(position);
    }
}
