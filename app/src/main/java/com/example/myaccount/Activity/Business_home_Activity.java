package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.myaccount.Adapter.Business_page_adapter;
import com.example.myaccount.Fragment.Choose_Bottomsheet_Fragment;
import com.example.myaccount.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Business_home_Activity extends AppCompatActivity {
    String uid,bid,smonth,currentDate,type;
    TabLayout tabLayout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home_);
        viewPager=findViewById(R.id.viewp);
        tabLayout=findViewById(R.id.tabla);
        bottomNavigationView=findViewById(R.id.bottom_user);
        uid=getIntent().getExtras().getString("uid");
        bid=getIntent().getExtras().getString("bid");
        type="user";
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        smonth=currentDate.substring(3);
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
        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose_Bottomsheet_Fragment choose_bottomsheet_fragment=new Choose_Bottomsheet_Fragment(uid,bid,type);
                choose_bottomsheet_fragment.show(getSupportFragmentManager(),"bottomsheet");
            }
        });
    }

}