package com.example.myaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class Business_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    String uid,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_2);
        uid=getIntent().getExtras().getString("uid");
        bid=getIntent().getExtras().getString("bid");
        Log.e(getClass().getSimpleName(),"uid="+uid);
        Log.e(getClass().getSimpleName(),"bid="+bid);
        bottomNavigationView=findViewById(R.id.bottombusi);
        tabLayout=findViewById(R.id.tabLayoutbusinesshome);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager=findViewById(R.id.viewpagerbusiness);
        Business_page_adapter business_page_adapter=new Business_page_adapter(this,getSupportFragmentManager(),tabLayout.getTabCount());
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
                Choose_Bottomsheet_Fragment choose_bottomsheet_fragment=new Choose_Bottomsheet_Fragment(uid,bid);
                choose_bottomsheet_fragment.show(getSupportFragmentManager(),"bottomsheet");
            }
        });
    }
}