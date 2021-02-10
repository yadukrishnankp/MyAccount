package com.example.myaccount.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Model.Signup_model;
import com.example.myaccount.R;

import java.util.ArrayList;

public class User_list_Adapter extends RecyclerView.Adapter<User_list_Adapter.Myrecycleholder> {
    LayoutInflater  layoutInflater;
    Context context;
    ArrayList<Signup_model>arr=new ArrayList<>();

    public User_list_Adapter(Context context, ArrayList<Signup_model> arr) {
        this.context=context;
        this.arr=arr;
    }

    @NonNull
    @Override
    public Myrecycleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.users_list_adapter,parent,false);
        Myrecycleholder myrecycleholder=new Myrecycleholder(view);
        return myrecycleholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycleholder holder, int position) {
        Signup_model sm=arr.get(position);
        holder.name.setText(arr.get(position).getName());
        holder.company.setText(arr.get(position).getCompanyname());
        Log.e("cm","="+arr.get(position).getCompanyname());
        holder.address.setText(arr.get(position).getAddress());
        holder.phone.setText(arr.get(position).getPhone());
        holder.email.setText(arr.get(position).getEmail());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+arr.get(position).getPhone()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class Myrecycleholder extends RecyclerView.ViewHolder {
        TextView company,name,address,phone,email;
        ImageView call;
        public Myrecycleholder(@NonNull View itemView) {
            super(itemView);
            company=itemView.findViewById(R.id.companyusa);
            name=itemView.findViewById(R.id.nameusa);
            address=itemView.findViewById(R.id.addressusa);
            phone=itemView.findViewById(R.id.phoneusa);
            email=itemView.findViewById(R.id.emailusa);
            call=itemView.findViewById(R.id.callusa);

        }
    }
}
