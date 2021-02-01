package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_home_Activity extends AppCompatActivity {

    Button addnewuser,viewuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_);

        addnewuser=findViewById(R.id.adduser_ah);
        viewuser=findViewById(R.id.viewuser_ah);

        addnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Registration_Activity.class);
                startActivity(i);
            }
        });

        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),All_userview_Activity.class);
                startActivity(i);

            }
        });
    }
}