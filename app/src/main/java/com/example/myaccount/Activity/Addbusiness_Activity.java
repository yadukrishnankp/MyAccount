package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Network.NetworkState;
import com.example.myaccount.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addbusiness_Activity extends AppCompatActivity {

    EditText businessname,startingdate,vendorname,password;
    TextInputLayout b,s,v,p;
    Button upload;
    String uid;
    DatabaseReference mrefeReference;
    Addbusiness_model addbusiness_model;
    View  toolbar;
    ImageView back;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbusiness_);
        NetworkState state=new NetworkState();
        businessname=findViewById(R.id.businessname_Ab);
        startingdate=findViewById(R.id.startingdate_Ab);
        vendorname=findViewById(R.id.vendorname_Ab);
        password=findViewById(R.id.password_Ab);
        upload=findViewById(R.id.upload_Ab);
        toolbar=findViewById(R.id.toolabraddbusi);
        b=findViewById(R.id.b);
        s=findViewById(R.id.s);
        v=findViewById(R.id.n);
        p=findViewById(R.id.p);
        uid=getIntent().getExtras().getString("uid");
        addbusiness_model=new Addbusiness_model();
        title=toolbar.findViewById(R.id.titletext);
        back=toolbar.findViewById(R.id.backimg);
        title.setText("Add Business");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true)
                {
                    if (state.getConnectivityStatusString(getApplicationContext()).equals("Not connected to Internet"))
                    {
                        Snackbar.make(findViewById(android.R.id.content),"No Internet Connection", BaseTransientBottomBar.LENGTH_LONG)
                                .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                                .show();
                    }
                    else
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
            }

        });
    }
    private boolean validation() {

        boolean val=true;
        if (businessname.getText().toString().length()==0)
        {
            businessname.setError("enter your business name");
            businessname.requestFocus();
            val=false;
        }
        else if (startingdate.getText().toString().length()==0)
        {
            startingdate.setError("enter starting date");
            startingdate.requestFocus();
            val=false;
        }
        else if (vendorname.getText().toString().length()==0)
        {
            vendorname.setError("enter vendor name");
            vendorname.requestFocus();
            val=false;
        }
        else if (password.getText().toString().length()==0)
        {
            password.setError("enter the password");
            password.requestFocus();
            val=false;
        }

        return  val;
    }

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),User_Home_Activity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}