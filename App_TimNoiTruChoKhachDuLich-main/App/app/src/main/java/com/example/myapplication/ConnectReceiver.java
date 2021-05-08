package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectReceiver extends BroadcastReceiver {
    public static ConnectReceiverListener connectReceiverListener;

    public ConnectReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork= cm.getActiveNetworkInfo();
        boolean isConnected= activeNetWork!=null&&activeNetWork.isConnectedOrConnecting();
        if(connectReceiverListener!=null){
            connectReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }
    public static boolean isConnected(){
        ConnectivityManager cm= (ConnectivityManager) MyApp
                .getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork= cm.getActiveNetworkInfo();
        return activeNetWork!=null&&activeNetWork.isConnectedOrConnecting();
    }
    public interface ConnectReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
