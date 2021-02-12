package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_user_Activity extends AppCompatActivity {
    String uid;
    EditText username,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_);
        uid=getIntent().getExtras().getString("uid");
        username=findViewById(R.id.usernamelu);
        password=findViewById(R.id.passwordlu);
        login=findViewById(R.id.loginbtnlu);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logincheck(uid,username.getText().toString(),password.getText().toString());
            }
        });
    }
    public void logincheck(String uid,String username,String password)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("New_business").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Addbusiness_model addbusiness_model=new Addbusiness_model();
                addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                if (addbusiness_model.getUid().equals(uid))
                {
                    if (addbusiness_model.getBusinessname().equals(username))
                    {
                        if (addbusiness_model.getPassword().equals(password))
                        {
                            Intent i=new Intent(getApplicationContext(),Business_home_Activity.class);
                            i.putExtra("uid",addbusiness_model.getUid());
                            i.putExtra("bid",addbusiness_model.getBusinessid());
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(Login_user_Activity.this, "invalid login details", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


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
}