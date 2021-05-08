package com.example.myapplication.View;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ConnectReceiver;
import com.example.myapplication.MyApp;
import com.example.myapplication.R;
import com.example.myapplication.View.Fragment.FragmentFavorite;
import com.example.myapplication.View.Fragment.FragmentHome;
import com.example.myapplication.View.Fragment.FragmentSetting;
import com.example.myapplication.View.Fragment.FragmentTravel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements ConnectReceiver.ConnectReceiverListener{
    private static final String TAG = "MainActivity";
    ChipNavigationBar navigationBar;
    RelativeLayout layoutNoInternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        getFragment(FragmentHome.newInstance());
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.homeFragment:{
                        getFragment(FragmentHome.newInstance());
                        break;
                    }
                    case R.id.search:{
                        getFragment(FragmentTravel.newInstance());
                        break;
                    }
                    case R.id.love:{
                        getFragment(FragmentFavorite.newInstance());
                        break;
                    }
                    case R.id.profile:{
                        getFragment(FragmentSetting.newInstance());
                        break;
                    }
                }
            }
        });

    }
    public void getFragment(Fragment fragment){
        try{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragment_container, fragment)
                    .commit();


        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: "+e.getMessage());
        }
    }

    private void anhXa(){
        navigationBar= findViewById(R.id.navigationBar);
        layoutNoInternet= findViewById(R.id.layoutNoInternet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectReceiver connectReceiver=  new ConnectReceiver();
        registerReceiver(connectReceiver,intentFilter);
        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            layoutNoInternet.setVisibility(View.GONE);
        }
        else layoutNoInternet.setVisibility(View.VISIBLE);
    }
}