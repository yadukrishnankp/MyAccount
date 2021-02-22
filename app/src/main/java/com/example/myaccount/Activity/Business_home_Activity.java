package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myaccount.Adapter.Business_page_adapter;
import com.example.myaccount.Fragment.Choose_Bottomsheet_Fragment;
import com.example.myaccount.Local_Database.Dbhandle;
import com.example.myaccount.Network.NetworkState;
import com.example.myaccount.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Business_home_Activity extends AppCompatActivity {
    String uid,bid,smonth,currentDate,type,activity;
    TabLayout tabLayout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    View toolbar;
    ImageView logout;
    SharedPreferences pref;
    private boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home_);
        viewPager=findViewById(R.id.viewp);
        tabLayout=findViewById(R.id.tabla);
        toolbar=findViewById(R.id.uder_toolbar);
        logout=toolbar.findViewById(R.id.logoutimg);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        bottomNavigationView=findViewById(R.id.bottom_user);
        uid=getIntent().getExtras().getString("uid");
        bid=getIntent().getExtras().getString("bid");
        type="user";
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        smonth=currentDate.substring(3);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login_Activity.class);
                SharedPreferences.Editor editor=pref.edit();
                editor.clear();
                editor.commit();
                startActivity(i);

            }
        });
        NetworkState state=new NetworkState();
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
        }

        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose_Bottomsheet_Fragment choose_bottomsheet_fragment=new Choose_Bottomsheet_Fragment(uid,bid,type,activity);
                choose_bottomsheet_fragment.show(getSupportFragmentManager(),"bottomsheet");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            Dbhandle dbhandle=new Dbhandle(getApplicationContext());
            dbhandle.removeall();
            Log.e("a","=");
            //return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(android.R.id.content),"Please click BACK again to exit", BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.green))
                .show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                Log.e("b","=");
            }

        }, 2000);
    }
}