package com.riss.book.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.riss.book.Local_Database.Dbhandle;
import com.riss.book.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Admin_home_Activity extends AppCompatActivity {

    Button addnewuser,viewuser;
    Button log;
    SharedPreferences pref;
    private boolean doubleBackToExitPressedOnce = false;
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
            dbhandle.removeall();
            Log.e("a","=");
            //return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(android.R.id.content),"Please click BACK again to exit", BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                .show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                Log.e("b","=");
            }

        }, 2000);
    }
}