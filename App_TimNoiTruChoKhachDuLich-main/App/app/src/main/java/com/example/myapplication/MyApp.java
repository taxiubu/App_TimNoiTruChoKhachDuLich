package com.example.myapplication;

import android.app.Application;

public class MyApp extends Application {
    static MyApp wifiInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        wifiInstance = this;
    }
    public static synchronized MyApp getInstance() {
        return wifiInstance;
    }
    public void setConnectivityListener(ConnectReceiver.ConnectReceiverListener listener){
        ConnectReceiver.connectReceiverListener=listener;
    }
}
