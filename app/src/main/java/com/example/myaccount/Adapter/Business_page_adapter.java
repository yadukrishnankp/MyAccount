package com.example.myaccount.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myaccount.Fragment.Earning_Fragment;
import com.example.myaccount.Fragment.Expense_Fragment;

public class Business_page_adapter extends FragmentPagerAdapter {
    Context context;
    int tabcount;
    String bid,uid;
    public Business_page_adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public Business_page_adapter(Context context, FragmentManager supportFragmentManager, int tabCount,String uid,String bid) {
        super(supportFragmentManager);
        this.context=context;
        this.tabcount=tabCount;
        this.bid=bid;
        this.uid=uid;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Earning_Fragment earning_fragment=new Earning_Fragment(uid,bid);
                return  earning_fragment;
            case 1:
                Expense_Fragment expense_fragment=new Expense_Fragment(uid,bid);
                return expense_fragment;
            default:
                 return null;

        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
