package com.riss.book.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.riss.book.Adapter.Months_list_Adapter;
import com.riss.book.Local_Database.Dbhandle;
import com.riss.book.Model.Amount_model;
import com.riss.book.Network.NetworkState;
import com.riss.book.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Monthlist_Activity extends AppCompatActivity {
    String bid,uid,activity;
    ArrayList<Amount_model> ern_month=new ArrayList<>();
    ArrayList<Amount_model>exp_month=new ArrayList<>();
    ArrayList<String>ern_month_string=new ArrayList<>();
    ArrayList<String>exp_month_string=new ArrayList<>();
    ArrayList<String>month=new ArrayList<>();
    ArrayList<String>month_filltered=new ArrayList<>();
    RecyclerView recyclerView;
    View toolbar;
    ImageView back;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthlist_);
        bid=getIntent().getExtras().getString("bid");
        uid=getIntent().getExtras().getString("uid");
        toolbar=findViewById(R.id.monthlistoolbar);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("All Months");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                dbhandle.delete_er_month();
                dbhandle.delete_ex_month();
                Intent i=new Intent(getApplicationContext(),Business_list_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
        activity="monthlist_activity";
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                ern_month=dbhandle.getearn_month();
                exp_month=dbhandle.getexp_month();
                for (Amount_model model:ern_month)
                {
                    ern_month_string.add(model.getMonth());
                }
                for (Amount_model model:exp_month)
                {
                    exp_month_string.add(model.getMonth());
                }
                Log.e("allmonth","="+ern_month_string+"=="+exp_month_string);
            }
        },2000);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                month.addAll(exp_month_string);
                month.addAll(ern_month_string);
                month_filltered=arrdup(month);
                Log.e("month","="+month_filltered);
            }
        },2000);


        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
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
                else
                {
                    recyclerView=findViewById(R.id.recyclemonthlist);
                    Months_list_Adapter months_list_adapter=new Months_list_Adapter(Monthlist_Activity.this,month_filltered,uid,activity);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(Monthlist_Activity.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(months_list_adapter);
                    Log.e("reccle","=");
                }

            }
        },2000);


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
        Dbhandle dbhandle=new Dbhandle(getApplicationContext());
        dbhandle.delete_er_month();
        dbhandle.delete_ex_month();
        Intent i=new Intent(getApplicationContext(),Business_list_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}