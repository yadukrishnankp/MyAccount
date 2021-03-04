package com.riss.book.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkReceiver extends BroadcastReceiver {
    static String  status =null;
    String net;


    @Override
    public void onReceive(Context context, Intent intent) {
//        status = NetworkState.getConnectivityStatusString(context);
//        Log.e("yy",""+status);
//        net=status;


    }
    public String network()
    {
        return net;
    }
}
