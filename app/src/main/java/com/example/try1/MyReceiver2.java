package com.example.try1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        boolean isConnected = (((wifi != null) && wifi.isConnectedOrConnecting()) ||
                (mobile != null) && (mobile.isConnectedOrConnecting()));
        if (isConnected) {
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
        }
            else{
                Toast.makeText(context, "No Internet Connected", Toast.LENGTH_LONG).show();

            }
        }
    }
