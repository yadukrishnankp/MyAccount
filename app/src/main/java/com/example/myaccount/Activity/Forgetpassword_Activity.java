package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myaccount.Model.Signup_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import com.example.myaccount.R;

public class Forgetpassword_Activity extends AppCompatActivity {

    EditText email,otp,newpassword,confirmpassword;
    Button otpsubmit,emailsubmit,changepsw;
    int otpcheck;
    CardView cotp,cpass;
    TextView err;
    public FirebaseDatabase mDatabase;
    private DatabaseReference mrefeReference;
    String otp1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_);
        email=findViewById(R.id.femail);
        otp=findViewById(R.id.fotp);
        emailsubmit=findViewById(R.id.fesubmitbtn);
        otpsubmit=findViewById(R.id.fotpbtn);
        cotp=findViewById(R.id.cotp);
        cpass=findViewById(R.id.cpasschange);
        newpassword=findViewById(R.id.newpassword);
        confirmpassword=findViewById(R.id.confirmpassword);
        changepsw=findViewById(R.id.changepswbtn);
        err=findViewById(R.id.error);
        emailsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                try
                {
                    DatabaseReference mrefeReference = FirebaseDatabase.getInstance().getReference();
                    mrefeReference.child("NewRegistration").orderByChild("email").equalTo(email.getText().toString()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                        {
                            Signup_model lm = dataSnapshot.getValue(Signup_model.class);
                            String id=lm.getId();

                            String mRecipientMail = email.getText().toString();
                            otp1=Integer.toString(OTP());
                            String mSubject = "Reset Password";
                            String mMessage = "Your otp no is :"+otp1;
                            new Forgetpasswordmail(Forgetpassword_Activity.this, mRecipientMail, mSubject, mMessage).execute();//call send mail  cunstructor asyntask by  sending perameter
                            counttimer();

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
                }
                catch (Exception e)
                {
                    Toast.makeText(Forgetpassword_Activity.this, "Sorry can't find account please check your username...!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        otpsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int txtotp=Integer.parseInt(otp.getText().toString());
                if(txtotp==otpcheck)
                {
                    Toast.makeText(Forgetpassword_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                    cotp.setVisibility(View.INVISIBLE);
                    cpass.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(Forgetpassword_Activity.this, "OTP Does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        changepsw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String npsw=newpassword.getText().toString();
                String cpsw=confirmpassword.getText().toString();
                if(npsw.equals(cpsw))
                {
                    changepassword(email.getText().toString(),cpsw);
                    Intent i=new Intent(Forgetpassword_Activity.this, Login_Activity.class);
                    startActivity(i);
                }
                else
                {
                    err.setVisibility(View.VISIBLE);

                }
            }
        });

    }
    public void counttimer()
    {
        email.setEnabled(false);
        otp.setVisibility(View.VISIBLE);
        otpsubmit.setVisibility(View.VISIBLE);
        new CountDownTimer(100000, 1000)
        {

            public void onTick(long millisUntilFinished)
            {
                emailsubmit.setTextSize(25);
                emailsubmit.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish()
            {
                emailsubmit.setBackgroundColor(Color.BLUE);
                emailsubmit.setText("Resend otp");
                otpcheck=0;
            }

        }.start();
    }
    public int OTP()
    {
        String o;
        //int len=4;
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[4];

        for (int i = 0; i < 4; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] =
                    numbers.charAt(rndm_method.nextInt(numbers.length()));
        }

        //  int number = Integer.parseInt(new String(digits));
        int number =Integer.parseInt(new String(otp));
        this.otpcheck=number;
        return number;
    }

    public void changepassword(String username, final String psw)
    {
        Log.e("dzs","="+username+psw);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("NewRegistration").orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Log.e("val1", "=" );
                Log.e("val1", "=" + dataSnapshot);
                Signup_model lm = dataSnapshot.getValue(Signup_model.class);
                Log.e("val12", "=" + lm.getEmail());
                String id=lm.getPid();
                DatabaseReference updatedata= FirebaseDatabase.getInstance().getReference("NewRegistration").child(id);
                updatedata.child("password").setValue(psw);

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
    }
}