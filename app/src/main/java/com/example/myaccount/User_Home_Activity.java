package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class User_Home_Activity extends AppCompatActivity {

    CardView Addbusiness,Viewbusiness,Editbusiness,Other;

    private float[] ydata={25f,50f};
    private String[] xdata={"earnings","expense"};
    PieChart pieChart;
    TextView title;
    View toolbar;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home_);
        uid=getIntent().getExtras().getString("uid");
        Log.e(getClass().getSimpleName(),"uid="+uid);
        Addbusiness=findViewById(R.id.addbusiness_uh);
        Viewbusiness=findViewById(R.id.viewbussiness_uh);
        Editbusiness=findViewById(R.id.editbusiness_uh);
        Other=findViewById(R.id.othres_uh);



        toolbar=findViewById(R.id.hometoolbar_user);
        title=toolbar.findViewById(R.id.hometitle);
        title.setText("My Accounts");
        pieChart=findViewById(R.id.charthome);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("My Accounts");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        pieChart.getDescription().setText("monthly earning and expense");
        pieChart.setEntryLabelTextSize(18f);


        addDataset(pieChart);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 2);

//                for(int i = 0; i < ydata.length; i++){
//                    if(ydata[i] == Float.parseFloat(sales)){
//                        pos1 = i;
//                        break;
//                    }
//                }
                String employee = xdata[pos1 + 1];
                Toast.makeText(User_Home_Activity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Addbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Addbusiness_Activity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

        Viewbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),View_Business_Activity.class);
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

        for(int i = 1; i < xdata.length; i++){
            xentry.add(xdata[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yentry,"amount");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}