package com.riss.book.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.riss.book.Local_Database.Dbhandle;
import com.riss.book.Model.Payment_model;
import com.riss.book.Adapter.Months_list_Adapter;
import com.riss.book.Network.NetworkState;
import com.riss.book.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Business_Analysis_Activity extends AppCompatActivity {
    String uid;
    Payment_model payment_model=new Payment_model();
    RecyclerView recyclerView;
    ArrayList<String>month=new ArrayList<>();
    ArrayList<String>month_filltered=new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    View toolbar;
    String activity;
    ImageView back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_business__analysis_);
            recyclerView=findViewById(R.id.recyclemonthuser);
            bottomNavigationView=findViewById(R.id.bottombar);
            toolbar=findViewById(R.id.toolbaranalysis);
            back=toolbar.findViewById(R.id.backimg);
            title=toolbar.findViewById(R.id.titletext);
            title.setText("Analytics");
                uid=getIntent().getExtras().getString("uid");
                activity="business_analysis_activity";
                month= (ArrayList<String>) getIntent().getSerializableExtra("month");
                Log.e("m","="+month);
                month_filltered=arrdup(month);
                Log.e("m","="+month_filltered);
        NetworkState state=new NetworkState();
        if (state.getConnectivityStatusString(getApplicationContext()).equals("Not connected to Internet"))
        {
            Snackbar.make(findViewById(android.R.id.content),"No Internet Connection", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        }
                    })
                    .show();
        }
        else {
            Months_list_Adapter months_list_adapter=new Months_list_Adapter(Business_Analysis_Activity.this,month_filltered,uid,activity);
            RecyclerView.LayoutManager manager=new GridLayoutManager(Business_Analysis_Activity.this,1);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(months_list_adapter);
        }


            bottomNavigationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                    dbhandle.delete_er_month();
                    dbhandle.delete_ex_month();
                    Intent intent=new Intent(getApplicationContext(), Business_list_Activity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
                    i.putExtra("uid",uid);
                    startActivity(i);
                }
            });
    }
    public ArrayList<String> arrdup(ArrayList<String>arr)
    {
        ArrayList<String>arrayList=new ArrayList<>();
        for (String s:arr)
        {
            if (!arrayList.contains(s))
            {
                arrayList.add(s);
            }
        }
        return arrayList;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}