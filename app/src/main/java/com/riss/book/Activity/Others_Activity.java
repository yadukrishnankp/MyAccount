package com.riss.book.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.riss.book.R;

public class Others_Activity extends AppCompatActivity {
    Button logout;
    SharedPreferences pref;
    String uid;
    View toolbar;
    ImageView back;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        uid=getIntent().getExtras().getString("uid",uid);
        pref = getSharedPreferences("NewRegistration", MODE_PRIVATE);
        logout=findViewById(R.id.logout);
        toolbar=findViewById(R.id.otherstoolbar);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("Others");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}