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

import com.example.myaccount.Activity.Business_Activity;
import com.example.myaccount.Local_Database.Dbhandle;
import com.example.myaccount.Model.Amount_model;
import com.example.myaccount.Model.Payment_model;
import com.example.myaccount.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Months_list_Adapter extends RecyclerView.Adapter<Months_list_Adapter.Myrecycle_holder> {
    LayoutInflater layoutInflater;
    Context context;
    String uid,activity;
    ArrayList<String>month=new ArrayList<>();
    Payment_model payment_model=new Payment_model();
    float f=0;
    public Months_list_Adapter(Context context, ArrayList<String> month_filltered, String uid, String activity)
    {
        this.context=context;
        this.month=month_filltered;
        this.uid=uid;
        this.activity=activity;
        Log.e("mmmm","="+month_filltered);
    }

    public void getexpense(String uid,String month)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                if (payment_model.getMonth().equals(month))
                {
                    final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("expense_tbl").orderByChild("expenseid").equalTo(payment_model.getExpenseid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            payment_model=snapshot.getValue(Payment_model.class);
                            float b= Float.parseFloat(payment_model.getAmount());
                            f=f+b;
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setExpense(a);
                            Dbhandle dbhandle=new Dbhandle(context);
                            dbhandle.expense_insert(amount_model);






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

    public void getearning(String uid,String month)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                if (payment_model.getMonth().equals(month))
                {
                    final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("Earning_tbl").orderByChild("earnid").equalTo(payment_model.getEarnid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            payment_model=snapshot.getValue(Payment_model.class);
                            float b= Float.parseFloat(payment_model.getAmount());
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setEarning(a);
                            Dbhandle dbhandle=new Dbhandle(context);
                            dbhandle.earn_insert(amount_model);
                            Log.e("earning","="+a);







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
    public Myrecycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.mothlist_adapter,parent,false);
        Myrecycle_holder myrecycle_holder=new Myrecycle_holder(view);
        return myrecycle_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycle_holder holder, int position) {
        holder.month.setText(month.get(position));
        Log.e("hiii","=");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getexpense(uid,holder.month.getText().toString());
                getearning(uid,holder.month.getText().toString());
                Intent i=new Intent(context, Business_Activity.class);
                i.putExtra("uid",uid);
                i.putExtra("bid","no1");
                i.putExtra("activity",activity);
                i.putExtra("month",holder.month.getText().toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    public class Myrecycle_holder extends RecyclerView.ViewHolder {
        TextView month;
        CardView cardView;
        public Myrecycle_holder(@NonNull View itemView) {
            super(itemView);
            month=itemView.findViewById(R.id.monthtxt);
            cardView=itemView.findViewById(R.id.cardmonth);
        }
    }
}
