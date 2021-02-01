package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Payment_Details_Activity extends AppCompatActivity {
    String ptype,uid,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__details_);
        ptype=getIntent().getExtras().getString("ptype");
        uid=getIntent().getExtras().getString("uid");
        bid=getIntent().getExtras().getString("bid");
        Log.e(getClass().getSimpleName(),"type="+ptype);
        Log.e(getClass().getSimpleName(),"uid="+uid);
        Log.e(getClass().getSimpleName(),"bid="+bid);


    }
}