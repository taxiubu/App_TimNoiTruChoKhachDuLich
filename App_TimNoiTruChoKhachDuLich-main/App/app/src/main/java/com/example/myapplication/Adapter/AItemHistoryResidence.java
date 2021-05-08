package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.IOnClickItemPlace;
import com.example.myapplication.Model.Residence;
import com.example.myapplication.R;
import com.example.myapplication.SQLHistoryItem;

import java.util.ArrayList;

public class AItemHistoryResidence extends RecyclerView.Adapter<AItemHistoryResidence.ViewHoder>{
    private Context context;
    private ArrayList<Residence> residences;
    IOnClickItemPlace iOnClickItemPlace;
    private int countRoom;
    SQLHistoryItem sql;
    public void setIOnClickItemPlace(IOnClickItemPlace iOnClickItemPlace) {
        this.iOnClickItemPlace = iOnClickItemPlace;
    }
    public AItemHistoryResidence(Context context, ArrayList<Residence> residences, int countRoom) {
        this.context = context;
        this.residences = residences;
        this.countRoom= countRoom;
        sql= new SQLHistoryItem(context);
    }
    @NonNull
    @Override
    public AItemHistoryResidence.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_history_residence, parent, false);
        return new AItemHistoryResidence.ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AItemHistoryResidence.ViewHoder holder, int position) {
        final Residence residence= residences.get(position);
        holder.nameResidence.setText(residence.getName());
        holder.rateStar.setText(residence.getRateStar().trim().replace(",","."));
        holder.addressResidence.setText(residence.getAddress());
        Glide.with(context)
                .load(residence.getImageLink())
                .placeholder(R.drawable.phong_dep)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imResidence);
        holder.imResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemPlace.onClickItem(residence);
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql.deleteItem(residence.getName());
                residences.remove(residence);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return residences.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView nameResidence,addressResidence,rateStar;
        ImageView imResidence;
        RelativeLayout btDelete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            nameResidence = itemView.findViewById(R.id.tvNameItem);
            addressResidence = itemView.findViewById(R.id.tvAddressItem);
            rateStar = itemView.findViewById(R.id.tvRateStar);
            imResidence = itemView.findViewById(R.id.imageResidence);
            btDelete = itemView.findViewById(R.id.btDeleteItem);
        }
    }
}
