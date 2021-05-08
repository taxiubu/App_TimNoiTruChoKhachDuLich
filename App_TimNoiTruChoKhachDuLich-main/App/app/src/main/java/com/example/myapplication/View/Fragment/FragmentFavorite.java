package com.example.myapplication.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.AItemFavoriteResidence;
import com.example.myapplication.Adapter.AItemResidence;
import com.example.myapplication.IOnClickItemPlace;
import com.example.myapplication.Model.Residence;
import com.example.myapplication.R;
import com.example.myapplication.SQLFavoriteItem;
import com.example.myapplication.View.DetailItemAct;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {
    RecyclerView rcvFavoriteResidence;
    AItemFavoriteResidence adapter;
    SQLFavoriteItem sqlFavoriteItem;
    public static FragmentFavorite newInstance() {
        FragmentFavorite fragment = new FragmentFavorite();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        rcvFavoriteResidence= view.findViewById(R.id.rcvResidenceFavorite);
        sqlFavoriteItem= new SQLFavoriteItem(getContext());

        ArrayList<Residence> residences= (ArrayList<Residence>) sqlFavoriteItem.getAllItem();
        adapter= new AItemFavoriteResidence(getContext(), residences);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvFavoriteResidence.setLayoutManager(layoutManager);
        rcvFavoriteResidence.setAdapter(adapter);
        adapter.setIOnClickItemPlace(new IOnClickItemPlace() {
            @Override
            public void onClickItem(Residence residence) {
                Intent i= new Intent(getContext(), DetailItemAct.class);
                i.putExtra("residence",residence);
                startActivity(i);
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
}