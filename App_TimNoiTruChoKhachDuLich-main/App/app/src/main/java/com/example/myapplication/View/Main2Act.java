package com.example.myapplication.View;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.View.Fragment.FragmentDatePicker;
import com.example.myapplication.View.Fragment.FragmentFeedback;
import com.example.myapplication.View.Fragment.FragmentLogin;
import com.example.myapplication.View.Fragment.FragmentMap;
import com.example.myapplication.View.Fragment.FragmentRoomAndTourists;
import com.example.myapplication.View.Fragment.FragmentWebView;

public class Main2Act extends AppCompatActivity {
    private static final String TAG = "LoginRegisterAct";
    int idFragment=1;
    String nameResidence=null,url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        idFragment= getIntent().getIntExtra("IdFragment",1);
        nameResidence= getIntent().getStringExtra("nameResidence");
        url= getIntent().getStringExtra("url");
        switch (idFragment){
            case 1:{
                getFragment(FragmentLogin.newInstance());
                break;
            }
            case 2:{
                getFragment(FragmentMap.newInstance(nameResidence));
                break;
            }
            case 3:{
                getFragment(FragmentDatePicker.newInstance());
                break;
            }
            case 4:{
                getFragment(FragmentRoomAndTourists.newInstance());
                break;
            }
            case 5:{
                getFragment(FragmentFeedback.newInstance(nameResidence));
                break;
            }
            case 6:{
                getFragment(FragmentWebView.newInstance(url));
                break;
            }
        }

    }
    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_login_register, fragment)
                    .addToBackStack(null).commit();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: "+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_enter_left_to_right, R.anim.anim_exit_left_to_right);
    }
}