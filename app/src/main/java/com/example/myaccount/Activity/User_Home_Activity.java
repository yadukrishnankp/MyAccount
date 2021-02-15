package com.example.myaccount.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myaccount.Local_Database.Dbhandle;
import com.example.myaccount.Model.Amount_model;
import com.example.myaccount.Model.Payment_model;
import com.example.myaccount.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class User_Home_Activity extends AppCompatActivity {

    CardView Addbusiness,Viewbusiness,Editbusiness,Other;
    TextView exptxt,erntxt;
    TextView title,full;
    View toolbar;
    SQLiteDatabase db;
    String uid,month,currentDate;
    ArrayList<Amount_model>expense=new ArrayList<>();
    ArrayList<Amount_model>earning=new ArrayList<>();
    ArrayList<Float>expense_f=new ArrayList<>();
    ArrayList<Float>earning_f=new ArrayList<>();
    ArrayList<Amount_model>ern_month=new ArrayList<>();
    ArrayList<Amount_model>exp_month=new ArrayList<>();
    ArrayList<String>ern_month_string=new ArrayList<>();
    ArrayList<String>exp_month_string=new ArrayList<>();
    Payment_model payment_model=new Payment_model();
    private float ydata[]=new float[2];
    private String xdata[]=new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home_);
        Dbhandle dbhandle=new Dbhandle(getApplicationContext());
        uid=getIntent().getExtras().getString("uid");
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        month=currentDate.substring(3);
        Log.e(getClass().getSimpleName(),"uid="+uid);
        Addbusiness=findViewById(R.id.addbusiness_uh);
        Viewbusiness=findViewById(R.id.viewbussiness_uh);
        Editbusiness=findViewById(R.id.editbusiness_uh);
        Other=findViewById(R.id.othres_uh);
        full=findViewById(R.id.full_text);
        exptxt=findViewById(R.id.exptxt);
        erntxt=findViewById(R.id.erntxt);
        toolbar=findViewById(R.id.hometoolbar_user);
        title=toolbar.findViewById(R.id.hometitle);
        title.setText("My Accounts");
        getmonthexpense(uid);
        getmonthearning(uid);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getexpense(uid);
            }
        },1000);
        new  Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getearning(uid);
            }
        },1000);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dbhandle.delete_earning();
                dbhandle.delete_expense();
            }
        },1000);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ern_month=dbhandle.getearn_month();
                exp_month=dbhandle.getexp_month();
                for (Amount_model model:ern_month)
                {
                    ern_month_string.add(model.getMonth());
                }
                for (Amount_model model:exp_month)
                {
                    exp_month_string.add(model.getMonth());
                }
                Log.e("allmonth","="+ern_month_string+"=="+exp_month_string);
            }
        },4000);
//        new Handler(getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                expense=dbhandle.getexpense();
//                for (Amount_model am:expense)
//                {
//                    expense_f.add(Float.valueOf(am.getExpense()));
//                }
//                earning=dbhandle.getearning();
//                for (Amount_model am:earning)
//                {
//                    earning_f.add(Float.valueOf(am.getEarning()));
//                }
//                float expense_total=get_total_expense(expense_f);
//                float earning_total=get_total_earning(earning_f);
//                Log.e("total","="+expense_total+earning_total);
//                ydata[0]=earning_total;
//                ydata[1]=expense_total;
//                xdata[0]="earnings";
//                xdata[1]="expense";
//            }
//        },4000);



        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Full_Payement_Details_Activity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                View view;
//                int pos1 = e.toString().indexOf("(sum): ");
//                String sales = e.toString().substring(pos1 + 2);
//
////                for(int i = 0; i < ydata.length; i++){
////                    if(ydata[i] == Float.parseFloat(sales)){
////                        pos1 = i;
////                        break;
////                    }
////                }
//
//                String employee = xdata[pos1 + 1];
//                Toast.makeText(User_Home_Activity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

        Addbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Addbusiness_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

        Viewbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), View_Business_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
        Editbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<String>month=new ArrayList<>();
               month.addAll(exp_month_string);
               month.addAll(ern_month_string);
                Intent i=new Intent(getApplicationContext(), Business_Analysis_Activity.class);
                i.putExtra("uid",uid);
                i.putExtra("month",month);
                startActivity(i);
            }
        });
        Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Others_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

    }


    private void addDataset(PieChart pieChart)
    {
        ArrayList<PieEntry>yentry=new ArrayList<>();
        ArrayList<String>xentry=new ArrayList<>();

        for (int i=0;i<ydata.length;i++)
        {
            Log.e(getClass().getSimpleName(),"dataset");
            yentry.add(new PieEntry(ydata[i],i));
        }

        for (int i = 1; i < xdata.length; i++){
            xentry.add(xdata[i]);
            PieDataSet pieDataSet=new PieDataSet(yentry,"amount");
            pieDataSet.setSliceSpace(2);
            pieDataSet.setValueTextSize(12);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setDrawValues(true);
            pieDataSet.setLabel(xdata[i]);
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }



        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.isEnabled();
        legend.isWordWrapEnabled();
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setFormSize(20F);
//        legend.setFormToTextSpace(0F);
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setTextSize(10F);
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//        legend.isWordWrapEnabled();


        //create pie data object


    }



    public float get_total_expense(ArrayList<Float>arr)
    {
        float b=0;
        for (float a:arr)
        {
            b+=a;
        }
        return b;
    }
    public float get_total_earning(ArrayList<Float>arr)
    {
        float b=0;
        for (float a:arr)
        {
            b+=a;
        }
        return b;
    }

    public void getexpense(String uid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
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
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setExpense(a);
                            float c=Float.parseFloat(exptxt.getText().toString())+b;
                            exptxt.setText(Float.toString(c));
                            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
//                            dbhandle.expense_insert(amount_model);
                            Log.e("s","="+b);

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
    public void getearning(String uid)
    {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                if (payment_model.getMonth().equals(month))
                {
                    final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                    reference1.child("Earning_tbl").orderByChild("earnid").equalTo(payment_model.getEarnid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Log.e("wrk","=");
                            payment_model=snapshot.getValue(Payment_model.class);
                            float b= Float.parseFloat(payment_model.getAmount());
                            Amount_model amount_model=new Amount_model();
                            String a= String.valueOf(b);
                            amount_model.setExpense(a);
                            float c=Float.parseFloat(erntxt.getText().toString())+b;
                            erntxt.setText(Float.toString(c));
                            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
//                            dbhandle.expense_insert(amount_model);
                            Log.e("sx","="+b);

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
    public void getmonthexpense(String uid)
    {
        ArrayList<String>arrayList=new ArrayList<>();
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("expense_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                Amount_model amount_model=new Amount_model();
                amount_model.setMonth(payment_model.getMonth());
                dbhandle.expmonth_insert(amount_model);
                Log.e("getmonthexpense","="+payment_model.getMonth());

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
    public void getmonthearning(String uid)
    {
        final String[] a = {""};
        ArrayList<String>arrayList=new ArrayList<>();
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("Earning_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                payment_model=snapshot.getValue(Payment_model.class);
                Dbhandle dbhandle=new Dbhandle(getApplicationContext());
                Amount_model amount_model=new Amount_model();
                amount_model.setMonth(payment_model.getMonth());
                dbhandle.ernmonth_insert(amount_model);
                Log.e("getmonthearning","="+payment_model.getMonth());






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