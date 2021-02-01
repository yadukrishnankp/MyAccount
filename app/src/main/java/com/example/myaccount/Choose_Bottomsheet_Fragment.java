package com.example.myaccount;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Choose_Bottomsheet_Fragment extends BottomSheetDialogFragment
{
    Button expense,earnings;
    String uid,bid;

    public Choose_Bottomsheet_Fragment(String uid, String bid)
    {
      this.uid=uid;
      this.bid=bid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_bottomsheet_layout,container,false);
        expense=view.findViewById(R.id.addexpbtn);
        earnings=view.findViewById(R.id.addernbtn);
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Payment_Details_Activity.class);
                i.putExtra("ptype","expense");
                i.putExtra("uid",uid);
                i.putExtra("bid",bid);
                startActivity(i);
                dismiss();
            }
        });
        earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Payment_Details_Activity.class);
                i.putExtra("ptype","earnings");
                i.putExtra("uid",uid);
                i.putExtra("bid",bid);
                startActivity(i);
                dismiss();

            }
        });
        return view;
    }
}
