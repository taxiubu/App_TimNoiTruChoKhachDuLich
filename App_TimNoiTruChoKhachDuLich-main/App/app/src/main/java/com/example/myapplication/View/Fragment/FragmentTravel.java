package com.example.myapplication.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.AItemResidence;
import com.example.myapplication.ConnectReceiver;
import com.example.myapplication.Model.Define;
import com.example.myapplication.IOnClickItemPlace;
import com.example.myapplication.Model.GetJsonData;
import com.example.myapplication.Model.Residence;
import com.example.myapplication.R;
import com.example.myapplication.View.DetailItemAct;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentTravel extends Fragment {
    RecyclerView rcvPlaces;
    AItemResidence adapter;
    ArrayList<Residence> residences;
    int countRoom=1;
    ImageView ivCategory1,ivCategory2,ivCategory3;
    RelativeLayout layoutCategory1,layoutCategory2,layoutCategory3;
    ProgressBar progressBar;
    public static FragmentTravel newInstance() {
        FragmentTravel fragment = new FragmentTravel();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_travel, container, false);
        ivCategory1= view.findViewById(R.id.ivCategory1);
        ivCategory2= view.findViewById(R.id.ivCategory2);
        ivCategory3= view.findViewById(R.id.ivCategory3);
        layoutCategory1= view.findViewById(R.id.layoutCategory1);
        layoutCategory2= view.findViewById(R.id.layoutCategory2);
        layoutCategory3= view.findViewById(R.id.layoutCategory3);
        progressBar = view.findViewById(R.id.progressBar);
        rcvPlaces= view.findViewById(R.id.rcvPlaces);
        residences=new ArrayList<>();
        loadImageCategory();
        visible();

        layoutCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectReceiver.isConnected()){
                    invisible();
                    new GetData(customURL(Define.URL_CATEGORY_1)).execute();
                }

            }
        });
        layoutCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectReceiver.isConnected()){
                    invisible();
                    new GetData(customURL(Define.URL_CATEGORY_2)).execute();
                }
            }
        });
        layoutCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectReceiver.isConnected()){
                    invisible();
                    new GetData(customURL(Define.URL_CATEGORY_3)).execute();
                }
            }
        });
        return view;
    }

    private void invisible(){
        layoutCategory1.setVisibility(View.GONE);
        layoutCategory2.setVisibility(View.GONE);
        layoutCategory3.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
    }
    private void visible(){
        layoutCategory1.setVisibility(View.VISIBLE);
        layoutCategory2.setVisibility(View.VISIBLE);
        layoutCategory3.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    class GetData extends GetJsonData{

        public GetData(String url) {
            super(url);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            residences= (ArrayList<Residence>) getResidenceList();
            adapter= new AItemResidence(getContext(), residences,countRoom);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcvPlaces.setLayoutManager(layoutManager);
            rcvPlaces.setAdapter(adapter);
            adapter.setIOnClickItemPlace(new IOnClickItemPlace() {
                @Override
                public void onClickItem(Residence residence) {
                    Intent i= new Intent(getContext(), DetailItemAct.class);
                    i.putExtra("residence",residence);
                    startActivity(i);
                }
            });
            if(residences!=null)
                progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void loadImageCategory(){
        Glide.with(getContext())
                .load(Define.CATEGORY_1)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivCategory1);
        Glide.with(getContext())
                .load(Define.CATEGORY_2)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivCategory2);
        Glide.with(getContext())
                .load(Define.CATEGORY_3)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivCategory3);
    }
    private String customURL(String s){
        SharedPreferences sharedPref= getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year= calendar.get(Calendar.YEAR);
        String dayStart= sharedPref.getString("dayStart",String.valueOf(day));
        String dayFinish= sharedPref.getString("dayFinish",String.valueOf(day+1));
        String monthStart= sharedPref.getString("monthStart",String.valueOf(month));
        String monthFinish= sharedPref.getString("monthFinish",String.valueOf(month));
        return s.replace("checkin_year=2021&checkin_month=4&checkin_monthday=24&checkout_year=2021&checkout_month=4&checkout_monthday=25","checkin_year="+year+"&checkin_month="+monthStart+"&checkin_monthday="+dayStart+"&checkout_year="+year+"&checkout_month="+monthFinish+"&checkout_monthday="+dayFinish);
    }
}