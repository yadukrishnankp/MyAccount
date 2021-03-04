package com.riss.book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.riss.book.Image_view_Activity;
import com.riss.book.Model.Payment_model;
import com.riss.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Paymentlist_adapter extends RecyclerView.Adapter<Paymentlist_adapter.Myrecycleholder>
{
    Context context;
    LayoutInflater layoutInflater;
    String type;
    ArrayList<String>arr=new ArrayList<>();

    public Paymentlist_adapter(Context context, ArrayList<String> eid,String type)
    {
        this.context=context;
        this.arr=eid;
        this.type=type;
    }



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
        if (type.equals("earning"))
        {
            String eid= arr.get(position);
            Log.e("e","="+eid);
            final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            reference.child("Earning_tbl").orderByChild("earnid").equalTo(eid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Payment_model model=snapshot.getValue(Payment_model.class);
                    holder.date.setText(model.getDate());
                    holder.amount.setText(model.getAmount()+"₹");
                    holder.time.setText(model.getTime());
                    holder.description.setText(model.getDescription());
                    Glide.with(holder.itemView).
                            load(R.drawable.up_icon).fitCenter().into(holder.payicon);
                    holder.bill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(context, Image_view_Activity.class);
                            i.putExtra("eid",eid);
                            i.putExtra("type",type);
                            context.startActivity(i);
                        }
                    });
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
        if (type.equals("expense"))
        {
            String eid= arr.get(position);
            Log.e("e","="+eid);
            final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            reference.child("expense_tbl").orderByChild("expenseid").equalTo(eid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Payment_model model=snapshot.getValue(Payment_model.class);
                    holder.date.setText(model.getDate());
                    holder.amount.setText(model.getAmount()+"₹");
                    holder.time.setText(model.getTime());
                    holder.description.setText(model.getDescription());
                    Glide.with(holder.itemView).
                            load(R.drawable.down_icon).fitCenter().into(holder.payicon);
                    holder.bill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(context, Image_view_Activity.class);
                            i.putExtra("eid",eid);
                            i.putExtra("type",type);
                            context.startActivity(i);
                        }
                    });
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


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class Myrecycleholder extends RecyclerView.ViewHolder {
        TextView date,amount,description,time,bill;
        ImageView payicon;
        public Myrecycleholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datepay);
            amount=itemView.findViewById(R.id.amountpay);
            description=itemView.findViewById(R.id.descpay);
            payicon=itemView.findViewById(R.id.imgpay);
            time=itemView.findViewById(R.id.time);
            bill=itemView.findViewById(R.id.biltxt);
        }
    }
}
