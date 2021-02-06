package com.example.myaccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myaccount.Model.Payment_model;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Business_Analysis_Activity extends AppCompatActivity {
    String uid;
    Payment_model payment_model=new Payment_model();
    RecyclerView recyclerView;
    ArrayList<String>month=new ArrayList<>();
    ArrayList<String>month_filltered=new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__analysis_);
        recyclerView=findViewById(R.id.recyclemonthuser);
        bottomNavigationView=findViewById(R.id.bottombar);
        uid=getIntent().getExtras().getString("uid");
        month= (ArrayList<String>) getIntent().getSerializableExtra("month");
        Log.e("m","="+month);
        month_filltered=arrdup(month);
        Log.e("m","="+month_filltered);
        Months_list_Adapter months_list_adapter=new Months_list_Adapter(Business_Analysis_Activity.this,month_filltered,uid);
        RecyclerView.LayoutManager manager=new GridLayoutManager(Business_Analysis_Activity.this,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(months_list_adapter);
        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Business_list_Activity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });


    }
    public ArrayList<String> arrdup(ArrayList<String>arr)
    {
        ArrayList<String>arrayList=new ArrayList<>();
        for (String s:arr)
        {
            if (!arrayList.contains(s))
            {
                arrayList.add(s);
            }
        }
        return arrayList;
    }




}