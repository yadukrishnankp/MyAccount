package com.example.myaccount;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Model.Signup_model;

import java.util.ArrayList;

public class Business_list_adapter extends RecyclerView.Adapter<Business_list_adapter.Myrecycleview> {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    String uid;
    String bid;

    public Business_list_adapter(Context context, ArrayList<Addbusiness_model> arr)
    {
        this.arr=arr;
        this.context=context;
        for (Addbusiness_model am:arr)
        {
            Log.e(getClass().getSimpleName(),"a"+am.getBusinessname());
        }
    }

    @NonNull
    @Override
    public Myrecycleview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.business_list_adapter,parent,false);
        Myrecycleview myrecycleview=new Myrecycleview(view);
        return myrecycleview;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycleview holder, int position) {
       Addbusiness_model am=arr.get(position);
       holder.business_name.setText(arr.get(position).getBusinessname());
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(context,Business_Activity.class);
               i.putExtra("uid",arr.get(position).getUid());
               i.putExtra("bid",arr.get(position).getBusinessid());
               context.startActivity(i);
           }
       });


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }



    public class Myrecycleview extends RecyclerView.ViewHolder {
        TextView business_name;
        CardView cardView;
        public Myrecycleview(@NonNull View itemView) {
            super(itemView);
            business_name=itemView.findViewById(R.id.bnamelist);
            cardView=itemView.findViewById(R.id.businesslisthome);



        }
    }
}
