package com.example.myapplication.View.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.myapplication.R;

public class FragmentDatePicker extends Fragment {
    DatePicker datePickerStart;
    DatePicker datePickerFinish;
    ImageView btBack;
    Button btConfirm;
    SharedPreferences sharedPref;

    public static FragmentDatePicker newInstance() {

        Bundle args = new Bundle();

        FragmentDatePicker fragment = new FragmentDatePicker();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        sharedPref= getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        datePickerStart= view.findViewById(R.id.datePickerStart);
        datePickerFinish= view.findViewById(R.id.datePickerFinish);
        btBack= view.findViewById(R.id.btBack);
        btConfirm= view.findViewById(R.id.btConfirm);

        datePickerStart.setMinDate(System.currentTimeMillis() - 1000);
        datePickerFinish.setMinDate(System.currentTimeMillis() - 1000);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateStart();
                setDateFinish();
                getActivity().finish();
            }
        });
        return view;
    }

    private void setDateStart(){
        int year = this.datePickerStart.getYear();
        int month = this.datePickerStart.getMonth()+1; // 0 - 11 +1
        int day = this.datePickerStart.getDayOfMonth();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("dayStart", String.valueOf(day));
        editor.putString("monthStart", String.valueOf(month));
        editor.commit();
    }
    private void setDateFinish(){
        int year = this.datePickerFinish.getYear();
        int month = this.datePickerFinish.getMonth()+1; // 0 - 11 +1
        int day = this.datePickerFinish.getDayOfMonth();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("dayFinish", String.valueOf(day));
        editor.putString("monthFinish", String.valueOf(month));
        editor.commit();

    }


}