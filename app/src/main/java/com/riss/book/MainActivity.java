package com.riss.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.riss.book.Activity.Login_Activity;
import com.riss.book.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread myThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}