package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class All_userview_Activity extends AppCompatActivity {
    View toolbar;
    RecyclerView user_lits;
    ProgressBar progress_user;
    ImageView back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_userview_);
        toolbar=findViewById(R.id.custom_toolbar);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("All Users");
        progress_user=findViewById(R.id.progressuser);
        user_lits=findViewById(R.id.recycleruserlist);

    }
}