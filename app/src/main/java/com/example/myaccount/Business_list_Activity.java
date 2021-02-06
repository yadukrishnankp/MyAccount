package com.example.myaccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myaccount.Model.Addbusiness_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Business_list_Activity extends AppCompatActivity {
    String uid;
    RecyclerView recyclerView;
    Addbusiness_model addbusiness_model=new Addbusiness_model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list_);
        recyclerView=findViewById(R.id.recyclebusllist2);
        uid=getIntent().getExtras().getString("uid");
        getmnothlist(uid);
    }

    public void getmnothlist(String uid)
    {
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("New_business").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                ArrayList<Addbusiness_model>arrayList=new ArrayList<>();
                arrayList.add(addbusiness_model);
                Business_list_Adapter_2 business_list_adapter_2=new Business_list_Adapter_2(Business_list_Activity.this,arrayList);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Business_list_Activity.this,1);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(business_list_adapter_2);
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