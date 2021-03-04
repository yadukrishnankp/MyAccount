package com.riss.book.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.riss.book.Adapter.Business_list_Adapter_2;
import com.riss.book.Local_Database.Dbhandle;
import com.riss.book.Model.Addbusiness_model;
import com.riss.book.Network.NetworkState;
import com.riss.book.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Business_list_Activity extends AppCompatActivity {
    String uid;
    RecyclerView recyclerView;
    Addbusiness_model addbusiness_model=new Addbusiness_model();
    ArrayList<Addbusiness_model>arrayList=new ArrayList<>();
    View toolbar;
    ImageView back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list_);
        recyclerView=findViewById(R.id.recyclebusllist2);
        uid=getIntent().getExtras().getString("uid");
        toolbar=findViewById(R.id.toolbarbusinesslist);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("All Business");
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
            getmnothlist(uid);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                dbhandle.delete_ex_month();
                dbhandle.delete_er_month();
                Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
    }

    public void getmnothlist(String uid)
    {
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("New_business").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                arrayList.add(addbusiness_model);
                Business_list_Adapter_2 business_list_adapter_2=new Business_list_Adapter_2(Business_list_Activity.this,arrayList);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Business_list_Activity.this,1);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(business_list_adapter_2);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Dbhandle dbhandle=new Dbhandle(getApplicationContext());
        dbhandle.delete_ex_month();
        dbhandle.delete_er_month();
        Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}