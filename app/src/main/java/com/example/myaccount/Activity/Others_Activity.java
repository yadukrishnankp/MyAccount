package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myaccount.Activity.Login_Activity;
import com.example.myaccount.R;

public class Others_Activity extends AppCompatActivity {
    Button logout;
    SharedPreferences pref;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        uid=getIntent().getExtras().getString("uid",uid);
        pref = getSharedPreferences("NewRegistration", MODE_PRIVATE);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                startActivity(i);

            }
        });
    }
}