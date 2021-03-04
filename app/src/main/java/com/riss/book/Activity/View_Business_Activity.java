package com.riss.book.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.riss.book.Adapter.Business_list_adapter;
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

public class View_Business_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String uid,activity;
    View toolbar;
    ImageView img;
    TextView title;
    viewbusiness viewbusiness=new viewbusiness();
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    Addbusiness_model addbusiness_model=new Addbusiness_model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__business_);
        uid=getIntent().getExtras().getString("uid");
        NetworkState state=new NetworkState();
        activity="view_business_activity";
        recyclerView=findViewById(R.id.buislist);
        progressBar=findViewById(R.id.progressbusiloist);
        toolbar=findViewById(R.id.toolabrbusilist);
        img=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("All Business");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), User_Home_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
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
            viewbusiness.execute(uid);
            progressBar.setVisibility(View.GONE);
        }
    }
    private  class viewbusiness extends AsyncTask<String,String,String>
    {
        String id;

        @Override
        protected String doInBackground(String... strings) {
            this.publishProgress(String.valueOf(progressBar));
            id=strings[0];
            Log.e("uid","async="+id);
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
            ref.child("New_business").orderByChild("uid").equalTo(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                    arr.add(addbusiness_model);
                    Business_list_adapter business_list_adapter=new Business_list_adapter(View_Business_Activity.this,arr,activity);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(View_Business_Activity.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(business_list_adapter);

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
            return null;
        }


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), User_Home_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}