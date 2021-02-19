package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myaccount.R;

public class Admin_home_Activity extends AppCompatActivity {

    Button addnewuser,viewuser;
    Button log;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_);

        addnewuser=findViewById(R.id.adduser_ah);
        viewuser=findViewById(R.id.viewuser_ah);
        pref = getSharedPreferences("NewRegistration", MODE_PRIVATE);
        log=findViewById(R.id.button2);


        addnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Registration_Activity.class);
                startActivity(i);
            }
        });

        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), All_userview_Activity.class);
                startActivity(i);

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                startActivity(i);
            }
        });
    }
}