package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myaccount.Fragment.Popup_Message;
import com.example.myaccount.R;

public class Login_choose_Activity extends AppCompatActivity {
    Button admin,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose_);
        admin=findViewById(R.id.adminbtn);
        user=findViewById(R.id.userbtn);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}