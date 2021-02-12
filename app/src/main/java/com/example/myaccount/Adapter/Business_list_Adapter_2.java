package com.example.myaccount.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Activity.Monthlist_Activity;
import com.example.myaccount.Local_Database.Dbhandle;
import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Model.Amount_model;
import com.example.myaccount.Model.Payment_model;
import com.example.myaccount.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Business_list_Adapter_2 extends RecyclerView.Adapter<Business_list_Adapter_2.Myrecycleholder> {
    LayoutInflater layoutInflater;
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    Payment_model payment_model=new Payment_model();
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

    public void getmonthexpense(String bid)
    {
        ArrayList<String>arrayList=new ArrayList<>();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("uid").equalTo(bid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                Dbhandle dbhandle=new Dbhandle(context);
                Amount_model amount_model=new Amount_model();
                amount_model.setMonth(payment_model.getMonth());
                dbhandle.expmonth_insert(amount_model);
                Log.e("getmonthexpense","="+payment_model.getMonth());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getmonthearning(String bid)
    {
        final String[] a = {""};
        ArrayList<String>arrayList=new ArrayList<>();
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("uid").equalTo(bid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                Dbhandle dbhandle=new Dbhandle(context);
                Amount_model amount_model=new Amount_model();
                amount_model.setMonth(payment_model.getMonth());
                dbhandle.ernmonth_insert(amount_model);
                Log.e("getmonthearning","="+payment_model.getMonth());






            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }


            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                Intent i=new Intent(context, Monthlist_Activity.class);
                i.putExtra("bid",arr.get(position).getBusinessid());
                i.putExtra("uid",arr.get(position).getUid());
                context.startActivity(i);
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
