package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Edit_Business_Activity extends AppCompatActivity {
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__business_);
        uid=getIntent().getExtras().getString("uid");
    }
}