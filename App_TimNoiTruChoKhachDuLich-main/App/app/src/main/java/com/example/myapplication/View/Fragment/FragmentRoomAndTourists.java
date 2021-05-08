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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;

public class FragmentRoomAndTourists extends Fragment {
    ImageView btBack;
    Button btConfirm;
    EditText etRoom, etTourist;
    SharedPreferences sharedPref;

    public static FragmentRoomAndTourists newInstance() {

        Bundle args = new Bundle();

        FragmentRoomAndTourists fragment = new FragmentRoomAndTourists();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_and_tourists, container, false);
        sharedPref= getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        btBack= view.findViewById(R.id.btBack2);
        btConfirm = view.findViewById(R.id.btOk2);
        etRoom = view.findViewById(R.id.etRoom);
        etTourist = view.findViewById(R.id.etTourist);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNumber();
                getActivity().finish();
            }
        });
        return view;
    }

    private void setNumber(){
        int room= Integer.parseInt(etRoom.getText().toString());
        int tourist= Integer.parseInt(etTourist.getText().toString());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("room", room);
        editor.putInt("tourist", tourist);
        editor.commit();
    }
}