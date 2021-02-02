package com.example.myaccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Model.Signup_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class View_Business_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String uid;
    viewbusiness viewbusiness=new viewbusiness();
    ArrayList<Addbusiness_model>arr=new ArrayList<>();
    Addbusiness_model addbusiness_model=new Addbusiness_model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__business_);
        uid=getIntent().getExtras().getString("uid");
        recyclerView=findViewById(R.id.buislist);
        progressBar=findViewById(R.id.progressbusiloist);
        viewbusiness.execute(uid);
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
                    Business_list_adapter business_list_adapter=new Business_list_adapter(View_Business_Activity.this,arr);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(View_Business_Activity.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(business_list_adapter);
                    progressBar.setVisibility(View.GONE);

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


}