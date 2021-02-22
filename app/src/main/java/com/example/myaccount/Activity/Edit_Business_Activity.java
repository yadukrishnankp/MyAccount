package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myaccount.Model.Addbusiness_model;
import com.example.myaccount.Network.NetworkState;
import com.example.myaccount.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.DialogInterface.*;

public class Edit_Business_Activity extends AppCompatActivity {
    String uid,bid;
    TextInputLayout Bname_ED,Sdate_ED,Name_ED,Password_ED;
    Button Update_ED;
    View toolbar;
    ImageView back;
    TextView title;
    Button delete;
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
        toolbar=findViewById(R.id.toolbareditbusiness);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        delete=findViewById(R.id.buttondelete);
        title.setText("Edit Business");
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
            getvalue(bid);
        }
        Update_ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Bname_ED.getEditText().getText().toString().length()==0)
                {
                    Bname_ED.getEditText().setError("enter Business name");
                }
                else if (Name_ED.getEditText().getText().toString().length()==0)
                {
                    Name_ED.getEditText().setError("enter name");
                }
                else if (Sdate_ED.getEditText().getText().toString().length()==0)
                {
                    Sdate_ED.getEditText().setError("enter date");
                }
                else if (Password_ED.getEditText().getText().toString().length()==0)
                {
                    Password_ED.getEditText().setError("enter password");
                }
                else
                {
                    update();
                }
            }


        });
      delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              deleteb();
          }
      });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getvalue(String bid)
    {
        final DatabaseReference  reference=FirebaseDatabase.getInstance().getReference();
        reference.child("New_business").orderByChild("businessid").equalTo(bid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Addbusiness_model addbusiness_model=new Addbusiness_model();
                addbusiness_model=snapshot.getValue(Addbusiness_model.class);
                Bname_ED.getEditText().setText(addbusiness_model.getBusinessname());
                Sdate_ED.getEditText().setText(addbusiness_model.getStartingdate());
                Name_ED.getEditText().setText(addbusiness_model.getVendorname());
                Password_ED.getEditText().setText(addbusiness_model.getPassword());
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

    public void  update()
    {
        NetworkState state=new NetworkState();
        if (state.getConnectivityStatusString(getApplicationContext()).equals("Not connected to Internet"))
        {
            Snackbar.make(findViewById(android.R.id.content),"No Internet Connection", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                    .show();
        }
        else
        {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("New_business").child(bid);
            reference.child("businessname").setValue(Bname_ED.getEditText().getText().toString());
            reference.child("startingdate").setValue(Sdate_ED.getEditText().getText().toString());
            reference.child("vendorname").setValue(Name_ED.getEditText().getText().toString());
            reference.child("password").setValue(Password_ED.getEditText().getText().toString());
            Intent i=new Intent(getApplicationContext(),View_Business_Activity.class);
            i.putExtra("uid",uid);
            startActivity(i);
        }

    }

    public void deleteb()
    {
        NetworkState state=new NetworkState();
        if (state.getConnectivityStatusString(getApplicationContext()).equals("Not connected to Internet"))
        {
            Snackbar.make(findViewById(android.R.id.content),"No Internet Connection", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                    .show();
        }
        else
        {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
            Query query=reference.child("New_business").orderByChild("businessid").equalTo(bid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot appleSnapshot: snapshot.getChildren())
                    {
                        appleSnapshot.getRef().removeValue();
                    }
                    Intent i =new Intent(getApplicationContext(),View_Business_Activity.class);
                    i.putExtra("uid",uid);
                    startActivity(i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }



}