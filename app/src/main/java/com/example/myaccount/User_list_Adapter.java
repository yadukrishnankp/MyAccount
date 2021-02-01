package com.example.myaccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class User_list_Adapter extends RecyclerView.Adapter<User_list_Adapter.Myrecycleholder> {
    LayoutInflater  layoutInflater;
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

    }

    @Override
    public int getItemCount() {
        return 0;
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
