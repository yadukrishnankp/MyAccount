package com.example.myaccount.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.R;

import java.util.ArrayList;

public class Business_list_Adapter_2 extends RecyclerView.Adapter<Business_list_Adapter_2.Myrecycleholder> {
    LayoutInflater layoutInflater;
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    Context context;
    Addbusiness_model addbusiness_model=new Addbusiness_model();
    public Business_list_Adapter_2(Context context, ArrayList<Addbusiness_model> arrayList) {
        this.context=context;
        this.arr=arrayList;
        for (Addbusiness_model  addbusiness_model:arr)
        {
            String s=addbusiness_model.getBusinessname();
            Log.e("as","="+s);
        }
    }








    @NonNull
    @Override
    public Myrecycleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.businesslit_2_adapter,parent,false);
        Myrecycleholder myrecycleholder=new Myrecycleholder(view);
        return myrecycleholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycleholder holder, int position) {
        addbusiness_model=arr.get(position);
        holder.business.setText(arr.get(position).getBusinessname());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class Myrecycleholder extends RecyclerView.ViewHolder {
        TextView business;
        CardView cardView;
        public Myrecycleholder(@NonNull View itemView) {
            super(itemView);
            business=itemView.findViewById(R.id.busitxt);
            cardView=itemView.findViewById(R.id.cardb2);
        }
    }
}
