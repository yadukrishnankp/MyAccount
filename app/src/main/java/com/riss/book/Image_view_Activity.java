package com.riss.book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.riss.book.Model.Payment_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Image_view_Activity extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView imageView;
    String eid,type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_);
        imageView=findViewById(R.id.imgs);
        eid=getIntent().getExtras().getString("eid");
        type=getIntent().getExtras().getString("type");
        Log.e("eid","="+eid+type);
        getimg(eid,type);
        scaleGestureDetector =new ScaleGestureDetector(this,new ScaleListener());

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;

        }
    }
    public void getimg(String eid,String type)
    {
        if (type.equals("earning"))
        {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            reference.child("Earning_tbl").orderByChild("earnid").equalTo(eid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Payment_model model=new Payment_model();
                    model=snapshot.getValue(Payment_model.class);
                    Glide.with(getApplicationContext()).load(model.getImageurl()).into(imageView);

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
        else if (type.equals("expense"))
        {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            reference.child("expense_tbl").orderByChild("expenseid").equalTo(eid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Payment_model model=new Payment_model();
                    model=snapshot.getValue(Payment_model.class);
                    Glide.with(getApplicationContext()).load(model.getImageurl()).into(imageView);
                    Log.e("img","="+model.getImageurl());

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
}