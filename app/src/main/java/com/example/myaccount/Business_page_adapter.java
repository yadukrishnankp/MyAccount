package com.example.myaccount;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Business_page_adapter extends FragmentPagerAdapter {
    Context context;
    int tabcount;
    public Business_page_adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public Business_page_adapter(Context context, FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        this.context=context;
        this.tabcount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Earning_Fragment earning_fragment=new Earning_Fragment();
                return  earning_fragment;
            case 1:
                Expense_Fragment expense_fragment=new Expense_Fragment();
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
