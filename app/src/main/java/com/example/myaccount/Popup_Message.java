package com.example.myaccount;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class Popup_Message
{
    public void show_popup_window(final View view)
    {
        LayoutInflater layoutInflater= (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popup_view=layoutInflater.inflate(R.layout.pop_up_message,null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow=new PopupWindow(popup_view,width,height,focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }
}
