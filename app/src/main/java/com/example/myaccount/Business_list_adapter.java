package com.example.myaccount;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Model.Amount_model;
import com.example.myaccount.Model.Payment_model;
import com.example.myaccount.Model.Signup_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Business_list_adapter extends RecyclerView.Adapter<Business_list_adapter.Myrecycleview> {
    LayoutInflater layoutInflater;
    Context context;
    Context context1;
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    String uid;
    String bid;
    float f=0;
    String currentDate,month;
    Payment_model payment_model=new Payment_model();


    public Business_list_adapter(Context context, ArrayList<Addbusiness_model> arr)
    {
        Log.e("aaaa","=");
        this.arr=arr;
        this.context=context;
        for (Addbusiness_model am:arr)
        {
            Log.e(getClass().getSimpleName(),"a"+am.getBusinessname());
        }
    }
    public void getexpense(String bid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("bid").equalTo(bid).addChildEventListener(new ChildEventListener() {
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

    public void getearning(String bid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("bid").equalTo(bid).addChildEventListener(new ChildEventListener() {
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
        holder.t.setText(Float.toString(f));
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
               month=currentDate.substring(3);
               getexpense(arr.get(position).getBusinessid());
               getearning(arr.get(position).getBusinessid());
               Intent i=new Intent(context,Business_Activity.class);
               i.putExtra("uid",arr.get(position).getUid());
               i.putExtra("bid",arr.get(position).getBusinessid());
               context.startActivity(i);
           }
       });
       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(context,Edit_Business_Activity.class);
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
        TextView business_name,t;
        CardView cardView;
        ImageView edit;
        public Myrecycleview(@NonNull View itemView) {
            super(itemView);
            business_name=itemView.findViewById(R.id.bnamelist);
            cardView=itemView.findViewById(R.id.businesslisthome);
            edit=itemView.findViewById(R.id.edit);
            t=itemView.findViewById(R.id.textView14);



        }
    }
}
