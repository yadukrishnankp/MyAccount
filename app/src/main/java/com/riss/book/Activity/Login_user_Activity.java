package com.riss.book.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.riss.book.Model.Addbusiness_model;
import com.riss.book.Model.Signup_model;
import com.riss.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_user_Activity extends AppCompatActivity {
    String uid;
    EditText username,password;
    Button login;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_);
        uid=getIntent().getExtras().getString("uid");
        username=findViewById(R.id.usernamelu);
        password=findViewById(R.id.passwordlu);
        login=findViewById(R.id.loginbtnlu);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        if (pref.contains("u")&&pref.contains("b"))
        {
            String uid=pref.getString("u",null);
            String bid=pref.getString("b",null);
            Intent i=new Intent(getApplicationContext(),Business_home_Activity.class);
            i.putExtra("uid",uid);
            i.putExtra("bid",bid);
            startActivity(i);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logincheck(uid,username.getText().toString(),password.getText().toString());
//                loin("Risbuzz",username.getText().toString(),password.getText().toString());

            }
        });
    }
    public void loin(String bname,String username,String password)
    {
       final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("NewRegistration").orderByChild("companyname").equalTo(bname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Signup_model signup_model=new Signup_model();
                signup_model=snapshot.getValue(Signup_model.class);
                if (bname.equals(signup_model.getCompanyname()))
                {
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("New_business").orderByChild("uid").equalTo(signup_model.getId()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Addbusiness_model addbusiness_model=new Addbusiness_model();
                            addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                            if (username.equals(addbusiness_model.getBusinessname()))
                            {
                                if (password.equals(addbusiness_model.getPassword()))
                                {
                                    Intent i=new Intent(getApplicationContext(),Business_home_Activity.class);
                                    i.putExtra("uid",addbusiness_model.getUid());
                                    i.putExtra("bid",addbusiness_model.getBusinessid());
                                    startActivity(i);
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
    public void logincheck(String uid,String username,String password)
    {
        SharedPreferences.Editor editor=pref.edit();
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
                            editor.putString("u",addbusiness_model.getUid());
                            editor.putString("b",addbusiness_model.getBusinessid());
                            editor.commit();
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