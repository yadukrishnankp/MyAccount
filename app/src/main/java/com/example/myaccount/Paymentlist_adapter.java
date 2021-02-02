package com.example.myaccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Paymentlist_adapter extends RecyclerView.Adapter<Paymentlist_adapter.Myrecycleholder>
{
    LayoutInflater layoutInflater;

    @NonNull
    @Override
    public Myrecycleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.payment_list,parent,false);
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
        TextView date,amount,description;
        ImageView payicon;
        public Myrecycleholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datepay);
            amount=itemView.findViewById(R.id.amountpay);
            description=itemView.findViewById(R.id.descpay);
            payicon=itemView.findViewById(R.id.imgpay);
        }
    }
}
