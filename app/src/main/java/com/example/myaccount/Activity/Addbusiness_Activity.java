package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addbusiness_Activity extends AppCompatActivity {

    EditText businessname,startingdate,vendorname,password;
    Button upload;
    String uid;
    DatabaseReference mrefeReference;
    Addbusiness_model addbusiness_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbusiness_);

        businessname=findViewById(R.id.businessname_Ab);
        startingdate=findViewById(R.id.startingdate_Ab);
        vendorname=findViewById(R.id.vendorname_Ab);
        password=findViewById(R.id.password_Ab);
        upload=findViewById(R.id.upload_Ab);
        uid=getIntent().getExtras().getString("uid");
        addbusiness_model=new Addbusiness_model();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true)
                {
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    mrefeReference= mDatabase.getReference("New_business");
                    String newKey = mrefeReference.push().getKey();
                    DatabaseReference refs = FirebaseDatabase.getInstance().getReference("New_business").child(newKey);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("businessid", newKey);
                    hashMap.put("businessname", businessname.getText().toString());
                    hashMap.put("startingdate", startingdate.getText().toString());
                    hashMap.put("vendorname", vendorname.getText().toString());
                    hashMap.put("password", password.getText().toString());
                    hashMap.put("uid",uid);
                    refs.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), User_Home_Activity.class);
                                i.putExtra("uid",uid);
                                startActivity(i);
                                Log.e("Msg", "Success");
                            }
                            else {
                                Log.e("Msg", "Failed");

                            }
                        }
                    });

                }
            }

        });
    }
    private boolean validation() {

        boolean val=true;
        if (businessname.getText().toString().length()==0)
        {
            Toast.makeText(this, "enter your business name", Toast.LENGTH_SHORT).show();
            businessname.requestFocus();
            val=false;
        }
        else if (startingdate.getText().toString().length()==0)
        {
            Toast.makeText(this, "enter starting date", Toast.LENGTH_SHORT).show();
            startingdate.requestFocus();
            val=false;
        }
        else if (password.getText().toString().length()==0)
        {
            Toast.makeText(this, "enter the password ", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            val=false;
        }

        return  val;
    }
}