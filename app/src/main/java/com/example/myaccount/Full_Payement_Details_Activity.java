package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Full_Payement_Details_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String uid,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__payement__details_);
        tabLayout=findViewById(R.id.tabloutfull);
        viewPager=findViewById(R.id.viewpagerfull);
        uid=getIntent().getExtras().getString("uid");
        bid="no";
        Business_page_adapter business_page_adapter=new Business_page_adapter(this,getSupportFragmentManager(),tabLayout.getTabCount(),uid,bid);
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
    }
}