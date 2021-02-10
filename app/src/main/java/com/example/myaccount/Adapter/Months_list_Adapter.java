package com.example.myaccount.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.R;

import java.util.ArrayList;

public class Months_list_Adapter extends RecyclerView.Adapter<Months_list_Adapter.Myrecycle_holder> {
    LayoutInflater layoutInflater;
    Context context;
    String uid;
    ArrayList<String>month=new ArrayList<>();


    public Months_list_Adapter(Context context, ArrayList<String> month_filltered, String uid)
    {
        this.context=context;
        this.month=month_filltered;
        this.uid=uid;
    }


    @NonNull
    @Override
    public Myrecycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.mothlist_adapter,parent,false);
        Myrecycle_holder myrecycle_holder=new Myrecycle_holder(view);
        return myrecycle_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycle_holder holder, int position) {
        holder.month.setText(month.get(position));
    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    public class Myrecycle_holder extends RecyclerView.ViewHolder {
        TextView month;
        public Myrecycle_holder(@NonNull View itemView) {
            super(itemView);
            month=itemView.findViewById(R.id.monthtxt);
        }
    }
}
