package com.example.myaccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myaccount.Model.Signup_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class All_userview_Activity extends AppCompatActivity {
    View toolbar;
    RecyclerView user_lits;
    ProgressBar progress_user;
    ImageView back;
    TextView title;
    RecyclerView recyclerView;
    ArrayList<Signup_model>arr=new ArrayList<>();
    Signup_model signup_model=new Signup_model();
    allusers allusers=new allusers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_userview_);
        toolbar=findViewById(R.id.custom_toolbar);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        recyclerView=findViewById(R.id.recycleruserlist);
        title.setText("All Users");
        progress_user=findViewById(R.id.progressuser);
        user_lits=findViewById(R.id.recycleruserlist);
        allusers.execute();




    }

    private class allusers extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            reference.child("NewRegistration").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    signup_model=snapshot.getValue(Signup_model.class);
                    arr.add(signup_model);
                    User_list_Adapter user_list_adapter=new User_list_Adapter(All_userview_Activity.this,arr);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(All_userview_Activity.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(user_list_adapter);
                    progress_user.setVisibility(View.GONE);
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