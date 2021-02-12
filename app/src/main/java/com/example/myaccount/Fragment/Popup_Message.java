package com.example.myaccount.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myaccount.Activity.Login_user_Activity;
import com.example.myaccount.Model.Signup_model;
import com.example.myaccount.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Popup_Message
{
    Context context;
    ArrayList<Signup_model>arrayList=new ArrayList<>();

    public Popup_Message(Context context)
    {
        this.context=context;
    }
    EditText nametxt;

    public void show_popup_window(final View view)
    {
        LayoutInflater layoutInflater= (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popup_view=layoutInflater.inflate(R.layout.pop_up_message,null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow=new PopupWindow(popup_view,width,height,focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        SharedPreferences pref;
        pref =context.getSharedPreferences("user", MODE_PRIVATE);
        nametxt=popup_view.findViewById(R.id.namepop);
        Button ok=popup_view.findViewById(R.id.okbtnpop);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nametxt.getText().toString().length()==0)
                {
                    nametxt.setError("enetr business name");
                }
                else
                {
                    logincheck(nametxt.getText().toString());
                }

            }
        });


    }

    public void logincheck(String name)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("NewRegistration").orderByChild("companyname").equalTo(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Signup_model signup_model=new Signup_model();
                signup_model=snapshot.getValue(Signup_model.class);
                arrayList.add(signup_model);
                if (name.equals(signup_model.getCompanyname()))
                {
                    String uid=signup_model.getId();
                    Intent intent=new Intent(context, Login_user_Activity.class);
                    intent.putExtra("uid",uid);
                    context.startActivity(intent);
                }
                else
                {
                    nametxt.setError("invalid business name");
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
