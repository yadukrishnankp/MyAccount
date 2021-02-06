package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myaccount.Model.Addbusiness_model;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Business_Activity extends AppCompatActivity {
    String uid,bid;
    TextInputLayout Bname_ED,Sdate_ED,Name_ED,Password_ED;
    Button Update_ED;

    private DatabaseReference mrefeReference;
    private FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__business_);
        uid=getIntent().getExtras().getString("uid");
        bid=getIntent().getExtras().getString("bid");

        Bname_ED=findViewById(R.id.businessname_ED);
        Sdate_ED=findViewById(R.id.startingdate_ED);
        Name_ED=findViewById(R.id.name_ED);
        Password_ED=findViewById(R.id.password_ED);
        Update_ED=findViewById(R.id.update_ED);
        Addbusiness_model bm=new Addbusiness_model();
        bm.setBusinessname(Bname_ED.getEditText().getText().toString());
        bm.setStartingdate(Sdate_ED.getEditText().getText().toString());
        bm.setVendorname(Name_ED.getEditText().getText().toString());
        bm.setPassword(Password_ED.getEditText().getText().toString());

//
//        Update_ED.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                business_Update();
//            }
//        });
    }
//    private void business_Update() {
//
//        mDatabase = FirebaseDatabase.getInstance();
//        mrefeReference = mDatabase.getReference();
//        mrefeReference.child("New_business").child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("User", databaseError.getMessage());
//            }
//        });
//    }

}