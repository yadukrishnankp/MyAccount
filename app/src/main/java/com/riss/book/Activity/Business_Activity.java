package com.riss.book.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riss.book.Adapter.Business_page_adapter;
import com.riss.book.Fragment.Choose_Bottomsheet_Fragment;
import com.riss.book.Local_Database.Dbhandle;
import com.riss.book.Model.Amount_model;
import com.riss.book.Model.Payment_model;
import com.riss.book.Network.NetworkState;
import com.riss.book.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Business_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    String uid,bid,currentDate,month,activity,smonth;
    PieChart pieChart;
    TextView exp,ern;
    String type;
    Toolbar toolbar;
    ArrayList<Float>expense_f=new ArrayList<>();
    ArrayList<Float>earning_f=new ArrayList<>();
    ArrayList<Amount_model>expense=new ArrayList<>();
    ArrayList<Amount_model>earning=new ArrayList<>();
    float expense_total;
    float earning_total;
    private float ydata[]=new float[2];
    private String xdata[]=new String[2];
    float a;
    float totalex=0;
    float totalen=0;
    Payment_model payment_model=new Payment_model();
    ArrayList<Float>e=new ArrayList<>();
    public Business_Activity()
    {

    }



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_2);
        exp=findViewById(R.id.exxp);
        ern=findViewById(R.id.ernn);
        viewPager=findViewById(R.id.viewpagerbusiness);
        bottomNavigationView=findViewById(R.id.bottombusi);
        tabLayout=findViewById(R.id.tabLayoutbusinesshome);
        toolbar=findViewById(R.id.toolbarb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Dbhandle dbhandle=new Dbhandle(getApplicationContext());
        NetworkState state=new NetworkState();
        pieChart=findViewById(R.id.piechartbusiness);
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        month=currentDate.substring(3);
        try {
            uid=getIntent().getExtras().getString("uid");
            bid=getIntent().getExtras().getString("bid");
            smonth=getIntent().getExtras().getString("month");
            activity=getIntent().getExtras().getString("activity");
            Log.e("act","="+activity);
            type="admin";
            if (activity.equals("business_analysis_activity") || activity.equals("monthlist_activity"))
            {
                bottomNavigationView.setVisibility(View.GONE);
                viewPager.setPadding(0,0,0,1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


      new Handler(getMainLooper()).postDelayed(new Runnable() {
          @Override
          public void run() {
              expense=dbhandle.getexpense();
              for (Amount_model am:expense)
              {
                  expense_f.add(Float.valueOf(am.getExpense()));
                  Log.e("expenesef","="+expense_f);
              }
              earning=dbhandle.getearning();
              for (Amount_model am:earning)
              {
                  earning_f.add(Float.valueOf(am.getEarning()));
                  Log.e("earningf","="+earning_f);

              }

              expense_total=get_total_expense(expense_f);
              earning_total=get_total_earning(earning_f);
              Log.e("total","="+expense_total+earning_total);

//
              ydata[0]=earning_total;
              ydata[1]=expense_total;
              xdata[0]="earnings";
              xdata[1]="expense";
          }
      },2000);





        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
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
            Business_page_adapter business_page_adapter=new Business_page_adapter(this,getSupportFragmentManager(),tabLayout.getTabCount(),uid,bid,smonth);
            viewPager.setAdapter(business_page_adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    pieChart.setHoleRadius(40f);
                    pieChart.setTransparentCircleAlpha(0);
                    pieChart.setCenterText("RISBOOK");

                    pieChart.setCenterTextSize(10);
                    pieChart.getDescription().setText("monthly income and expense");
                    pieChart.setDrawEntryLabels(false);


                    AddDataset(pieChart);
                }
            },2000);
        }

        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose_Bottomsheet_Fragment choose_bottomsheet_fragment=new Choose_Bottomsheet_Fragment(uid,bid,type,activity);
                choose_bottomsheet_fragment.show(getSupportFragmentManager(),"bottomsheet");
            }
        });

        AddDataset(pieChart);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.equals("view_business_activity"))
                {
                    Dbhandle dbhandle = new Dbhandle(getApplicationContext());
                    dbhandle.delete_earning();
                    dbhandle.delete_expense();
                    Intent i = new Intent(getApplicationContext(), View_Business_Activity.class);
                    i.putExtra("uid", uid);
                    startActivity(i);
                }
                else if (activity.equals("business_analysis_activity"))
                {
                    Dbhandle dbhandle = new Dbhandle(getApplicationContext());
                    dbhandle.delete_earning();
                    dbhandle.delete_expense();
//            Intent i = new Intent(getApplicationContext(),Business_Analysis_Activity.class);
//            i.putExtra("uid", uid);
//            startActivity(i);
                    finish();
                }
                else if (activity.equals("monthlist_activity"))
                {
                    Dbhandle dbhandle = new Dbhandle(getApplicationContext());
                    dbhandle.delete_earning();
                    dbhandle.delete_expense();
                    Intent i = new Intent(getApplicationContext(),Monthlist_Activity.class);
                    i.putExtra("uid", uid);
                    i.putExtra("bid",bid);
                    startActivity(i);
                }
            }
        });

    }

    public float get_total_expense(ArrayList<Float>arr)
    {
        float b=0;
        for (float a:arr)
        {
            b=b+a;
            Log.e("b","="+b);
        }
        return b;
    }
    public float get_total_earning(ArrayList<Float>arr)
    {
        float b=0;
        for (float a:arr)
        {
            b=b+a;
        }
        return b;
    }

    public void getexpense(String bid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("bid").equalTo(bid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                if (payment_model.getMonth().equals(month))
                {
                    final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("expense_tbl").orderByChild("expenseid").equalTo(payment_model.getExpenseid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            payment_model=snapshot.getValue(Payment_model.class);
                            float b= Float.parseFloat(payment_model.getAmount());
                            totalex=totalex+b;
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setExpense(a);
                            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                            dbhandle.expense_insert(amount_model);
                            Log.e("expense","="+totalex);
                            exp.setText(Float.toString(totalex));




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



    public void getearning(String bid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("bid").equalTo(bid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                if (payment_model.getMonth().equals(month))
                {
                    final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("Earning_tbl").orderByChild("earnid").equalTo(payment_model.getEarnid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            payment_model=snapshot.getValue(Payment_model.class);
                            float b= Float.parseFloat(payment_model.getAmount());
                            totalen=totalen+0;
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setEarning(a);
                            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                            dbhandle.earn_insert(amount_model);
                            Log.e("earning","="+a);
                            ern.setText(Float.toString(totalen));






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




    private void AddDataset(PieChart pieChart)
    {
        ArrayList<PieEntry>yentry=new ArrayList<>();
        ArrayList<String>xentry=new ArrayList<>();
        yentry.add(new PieEntry(expense_total,"income"));
        yentry.add(new PieEntry(earning_total,"expense"));

//        for (int i=0;i<ydata.length;i++)
//        {
//            Log.e(getClass().getSimpleName(),"dataset");
//            yentry.add(new PieEntry(ydata[i],i));
//        }
//
//        for(int i = 1; i < xdata.length; i++){
//            xentry.add(xdata[i]);
//        }
        ArrayList arrayList=new ArrayList<>();
        arrayList.add("expense");
        arrayList.add("income");
        PieDataSet pieDataSet=new PieDataSet(yentry," ");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        //create pie data object
        pieChart.setData(pieData);
        pieChart.invalidate();
    }







    @Override
    public void onBackPressed() {
        if (activity.equals("view_business_activity"))
        {
            Dbhandle dbhandle = new Dbhandle(getApplicationContext());
            dbhandle.delete_earning();
            dbhandle.delete_expense();
            Intent i = new Intent(getApplicationContext(), View_Business_Activity.class);
            i.putExtra("uid", uid);
            startActivity(i);
        }
        else if (activity.equals("business_analysis_activity"))
        {
            Dbhandle dbhandle = new Dbhandle(getApplicationContext());
            dbhandle.delete_earning();
            dbhandle.delete_expense();
//            Intent i = new Intent(getApplicationContext(),Business_Analysis_Activity.class);
//            i.putExtra("uid", uid);
//            startActivity(i);
            finish();
        }
        else if (activity.equals("monthlist_activity"))
        {
            Dbhandle dbhandle = new Dbhandle(getApplicationContext());
            dbhandle.delete_earning();
            dbhandle.delete_expense();
            Intent i = new Intent(getApplicationContext(),Monthlist_Activity.class);
            i.putExtra("uid", uid);
            i.putExtra("bid",bid);
            startActivity(i);
        }

//        }
//        else if (activity.equals("business_analysis_activity"))
//        {
//            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
//            dbhandle.delete_earning();
//            dbhandle.delete_expense();
////            Intent i=new Intent(getApplicationContext(), Business_Analysis_Activity.class);
////            i.putExtra("uid",uid);
////            startActivity(i);
//            finish();
//        }


    }

}