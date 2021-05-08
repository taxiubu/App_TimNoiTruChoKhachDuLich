package com.example.myapplication.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.View.DetailItemAct;
import com.example.myapplication.View.Main2Act;
import com.example.myapplication.View.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentSetting extends Fragment {
    Button btRegister;
    TextView btLogout,tvWelcome,legalInformation,privacyPolicy,centerSupport;
    Intent i;
    public static FragmentSetting newInstance() {
        FragmentSetting fragment = new FragmentSetting();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        btRegister= view.findViewById(R.id.btRegister);
        tvWelcome= view.findViewById(R.id.tvWelcome);
        btLogout= view.findViewById(R.id.btLogout);
        legalInformation= view.findViewById(R.id.legalInformation);
        privacyPolicy= view.findViewById(R.id.privacyPolicy);
        centerSupport= view.findViewById(R.id.centerSupport);
        i= new Intent(getContext(), Main2Act.class);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.amin_enter_right_to_left,R.anim.amin_exit_right_to_left);
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right);
            }
        });
        legalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("IdFragment",6);
                i.putExtra("url","https://www.booking.com/content/legal.vi.html");
                startActivity(i);
            }
        });
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("IdFragment",6);
                i.putExtra("url","https://www.booking.com/content/privacy.vi.html");
                startActivity(i);
            }
        });
        centerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("IdFragment",6);
                i.putExtra("url","https://www.booking.com/customer-service.vi.html");
                startActivity(i);
            }
        });
        getCurrentUser();
        return view;
    }
    private void getCurrentUser(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            btRegister.setVisibility(View.INVISIBLE);
            btLogout.setVisibility(View.VISIBLE);
            tvWelcome.setText("Chào mừng "+user.getDisplayName()+" đến với chúng tôi!!");
        }
        else{
            btRegister.setVisibility(View.VISIBLE);
            btLogout.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getCurrentUser();
    }
}