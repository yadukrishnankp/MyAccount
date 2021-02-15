package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myaccount.Adapter.Months_list_Adapter;
import com.example.myaccount.Local_Database.Dbhandle;
import com.example.myaccount.Model.Amount_model;
import com.example.myaccount.R;

import java.util.ArrayList;

public class Monthlist_Activity extends AppCompatActivity {
    String bid,uid,activity;
    ArrayList<Amount_model> ern_month=new ArrayList<>();
    ArrayList<Amount_model>exp_month=new ArrayList<>();
    ArrayList<String>ern_month_string=new ArrayList<>();
    ArrayList<String>exp_month_string=new ArrayList<>();
    ArrayList<String>month=new ArrayList<>();
    ArrayList<String>month_filltered=new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthlist_);
        bid=getIntent().getExtras().getString("bid");
        uid=getIntent().getExtras().getString("uid");
        activity="monthlist_activity";
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                ern_month=dbhandle.getearn_month();
                exp_month=dbhandle.getexp_month();
                for (Amount_model model:ern_month)
                {
                    ern_month_string.add(model.getMonth());
                }
                for (Amount_model model:exp_month)
                {
                    exp_month_string.add(model.getMonth());
                }
                Log.e("allmonth","="+ern_month_string+"=="+exp_month_string);
            }
        },2000);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                month.addAll(exp_month_string);
                month.addAll(ern_month_string);
                month_filltered=arrdup(month);
                Log.e("month","="+month_filltered);
            }
        },2000);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView=findViewById(R.id.recyclemonthlist);
                Months_list_Adapter months_list_adapter=new Months_list_Adapter(Monthlist_Activity.this,month_filltered,uid,activity);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Monthlist_Activity.this,1);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(months_list_adapter);
                Log.e("reccle","=");
            }
        },4000);


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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Business_list_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}