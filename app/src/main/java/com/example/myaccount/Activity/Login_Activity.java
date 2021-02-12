package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myaccount.Fragment.Popup_Message;
import com.example.myaccount.Model.Signup_model;
import com.example.myaccount.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jp.wasabeef.blurry.Blurry;

public class Login_Activity extends AppCompatActivity {
    EditText UserName,Password;
    TextView ForgetPassword,login_as;
    Button Login;
    String uid, type;
    SharedPreferences pref;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        UserName = findViewById(R.id.lausername);
        Password = findViewById(R.id.lapassword);
        Login = findViewById(R.id.lalogin);
        login_as=findViewById(R.id.loginas);
        ForgetPassword = findViewById(R.id.laforgotpassword);
        layout=findViewById(R.id.loginlay);

        pref = getSharedPreferences("NewRegistration", MODE_PRIVATE);

        if (pref.contains("username")) {
            if (pref.contains("password")) {
                String type = pref.getString("type", null);
                String uid = pref.getString("uid", null);
                if (type.equals("user")) {
                    Intent i = new Intent(getApplicationContext(), User_Home_Activity.class);
                    i.putExtra("uid", uid);
                    startActivity(i);
                } else if (type.equals("admin")) {
                    Intent i = new Intent(getApplicationContext(), Admin_home_Activity.class);
                    startActivity(i);
                }
            }
        }

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Forgetpassword_Activity.class);
                startActivity(i);

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    String mail = UserName.getText().toString();
                    String pass = Password.getText().toString();
                    if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                        UserName.setError("enter valid mail address");
                    } else if (pass.isEmpty() || pass.length() < 4 || pass.length() > 15) {
                        Password.setError("password must be between 4 and 10 alphanumeric characters");
                    } else {
                        logincheck(UserName.getText().toString(), Password.getText().toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        login_as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popup_Message popup_message=new Popup_Message(Login_Activity.this);
                popup_message.show_popup_window(v);
            }
        });
    }



    private boolean isConnected() {

        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    private void logincheck(final String un, final String psw) {


        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("NewRegistration").orderByChild("email").equalTo(un).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    int f = 0;
                    Signup_model lm = dataSnapshot.getValue(Signup_model.class);
                    String type = lm.getType();

                    if (lm.getPassword().equals(psw)) {
                        f = 1;
                        if (type.equals("user")) {
                            uid = lm.getId();
                            Log.e(getClass().getSimpleName(),"uid="+lm.getId());
                            Intent i = new Intent(Login_Activity.this, User_Home_Activity.class);
                            SharedPreferences.Editor editor = pref.edit();
                            i.putExtra("uid", uid);
                            editor.putString("username", un);
                            editor.putString("password", psw);
                            editor.putString("uid", uid);
                            editor.putString("type", lm.getType());
                            editor.commit();
                            //i.putExtra("area",lm.getArea());
                            //i.putExtra("pincode",lm.getPincode());
                            startActivity(i);

                        } else if (type.equals("admin")) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username", un);
                            editor.putString("password", psw);
                            editor.putString("uid", lm.getId());
                            editor.putString("type", lm.getType());
                            editor.commit();
                            Intent i = new Intent(Login_Activity.this, Admin_home_Activity.class);
                            startActivity(i);
                        } else {
                            // Toast.makeText(Signin.this, "You are not active..please wait for admin approval", Toast.LENGTH_SHORT).show();
                            // snabar("You are not active..please wait for admin approval",v);
                        }
                    } else {
                        //snabar("Incorrect username or password",v);
                        Toast.makeText(Login_Activity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                    if (f == 0) {
                        // snabar("Incorrect username or password",v);
                        Toast.makeText(Login_Activity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            // snabar("Something went wrong",v);
        }
    }

}