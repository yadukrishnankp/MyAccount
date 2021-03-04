package com.riss.book.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.riss.book.Model.Signup_model;
import com.riss.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration_Activity extends AppCompatActivity {
    View toolbar;
    ImageView back;
    TextView title;
    Button Submit;
    TextInputLayout name,company,phone,address,email,password;

    //firebase
    private DatabaseReference mrefeReference;
    private FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;
    String newKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        toolbar=findViewById(R.id.custom_toolbar);
        back=toolbar.findViewById(R.id.backimg);
        title=toolbar.findViewById(R.id.titletext);
        title.setText("Registration");
        name=findViewById(R.id.name_ra);
        company=findViewById(R.id.company_ra);
        phone=findViewById(R.id.phone_ra);
        address=findViewById(R.id.address_ra);
        email=findViewById(R.id.email_ra);
        password=findViewById(R.id.password_ra);
        Submit=findViewById(R.id.submit_ra);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isConnected()) {

                    Signup_model sm = new Signup_model();
                    sm.setName(name.getEditText().getText().toString());
                    sm.setCompanyname(company.getEditText().getText().toString());
                    sm.setPhone(phone.getEditText().getText().toString());
                    sm.setEmail(email.getEditText().getText().toString().trim());
                    sm.setPassword(password.getEditText().getText().toString());

                    registrationinsert(sm);

                }
                else
                {

                    Toast.makeText(Registration_Activity.this, "Check Your Connectivity", Toast.LENGTH_SHORT).show();

                }

            }

        });
    }

    private boolean isConnected()
    {
        boolean connected = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }
        catch (Exception e)
        {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;

    }


    private void registrationinsert(final Signup_model ss)
    {
        Log.e("ep22545", "=");
        try {
            Log.e("ep22", "="+ss.getEmail()+" "+ss.getPassword());
            String email = ss.getEmail();
            String password = ss.getPassword();

            //sm = ss;
            try {
                Log.e("ep", "=" + ss.getEmail() + ss.getPassword());
                mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.e("TAG", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    Log.e("user id", "=" + user.getUid());
                                    Log.e("Msg", "Success");
                                    //////////////Signup////////////
                                    mDatabase = FirebaseDatabase.getInstance();
                                    mrefeReference = mDatabase.getReference("NewRegistration");
                                    newKey = user.getUid();
                                    DatabaseReference refs = FirebaseDatabase.getInstance().getReference("NewRegistration").child(newKey);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", newKey);
                                    hashMap.put("name", ss.getName());
                                    hashMap.put("companyname", ss.getCompanyname());
                                    hashMap.put("phone", ss.getPhone());
                                    hashMap.put("email", ss.getEmail());
                                    hashMap.put("address",ss.getAddress());
                                    hashMap.put("password", ss.getPassword());
                                    Log.e("123","="+ss.getPassword());
                                    hashMap.put("type", "admin");
                                    refs.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                                                startActivity(i);
                                                Log.e("Msg", "Success");
                                            } else {
                                                Toast.makeText(Registration_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                Log.e("binu", "Failed");
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(Registration_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    Log.e("yadu", "Failed");
                                }
                            }
                        });

            } catch (Exception e) {
                Toast.makeText(Registration_Activity.this, "Authentication failed...! Try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(Registration_Activity.this, "Authentication failed...! Try again later", Toast.LENGTH_SHORT).show();
        }

    }

}