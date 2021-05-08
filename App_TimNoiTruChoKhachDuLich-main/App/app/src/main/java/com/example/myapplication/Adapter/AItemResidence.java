package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.IOnClickItemPlace;
import com.example.myapplication.Model.Residence;
import com.example.myapplication.R;
import com.example.myapplication.SQLFavoriteItem;

import java.util.ArrayList;

public class AItemResidence extends RecyclerView.Adapter<AItemResidence.ViewHoder>{
    private Context context;
    private ArrayList<Residence> residences;
    private SQLFavoriteItem sql;
    IOnClickItemPlace iOnClickItemPlace;
    private int countRoom;

    public void setIOnClickItemPlace(IOnClickItemPlace iOnClickItemPlace) {
        this.iOnClickItemPlace = iOnClickItemPlace;
    }

    public AItemResidence(Context context, ArrayList<Residence> residences, int countRoom) {
        this.context = context;
        this.residences = residences;
        this.countRoom= countRoom;
        sql= new SQLFavoriteItem(context);
    }

    @NonNull
    @Override
    public AItemResidence.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_residence, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AItemResidence.ViewHoder holder, int position) {
        final Residence residence = residences.get(position);
        final int[] checkButton = {0};
        holder.nameResidence.setText(residence.getName());
        if(residence.getRate()!=null)
            holder.rateResidence.setText(residence.getRate());
        if(residence.getRateStar()!=null)
            holder.rateStar.setText(residence.getRateStar().replace(",","."));
        holder.addressResidence.setText(residence.getAddress());
        holder.typeResidence.setText(residence.getType());
        holder.btShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemPlace.onClickItem(residence);
            }
        });
        holder.imageLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkButton[0] ==0){
                    Toast.makeText(context,"Yêu thích",Toast.LENGTH_SHORT).show();
                    holder.imageLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red);
                    sql.insertItem(residence);
                    checkButton[0] =1;
                }
                else {
                    Toast.makeText(context,"Hủy yêu thích",Toast.LENGTH_SHORT).show();
                    holder.imageLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24_white);
                    sql.deleteItem(residence.getName());
                    checkButton[0] =0;
                }
            }
        });
        /*holder.priceResidence.setText(String.valueOf(Integer.parseInt(residence.getPrice()
                .replace("VND","").replace(".","").trim())*countRoom));*/
        holder.priceResidence.setText(residence.getPrice());
        Glide.with(context)
                .load(residence.getImageLink())
                .placeholder(R.drawable.phong_dep)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageResidence);
    }

    @Override
    public int getItemCount() {
        return residences.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView nameResidence, typeResidence, addressResidence,priceResidence,rateResidence,rateStar;
        ImageView imageResidence,imageLove;
        Button btShowDetail;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            nameResidence = itemView.findViewById(R.id.nameResidence);
            typeResidence = itemView.findViewById(R.id.typeResidence);
            addressResidence = itemView.findViewById(R.id.addressResidence);
            imageResidence = itemView.findViewById(R.id.imageResidence);
            imageLove = itemView.findViewById(R.id.btLove);
            btShowDetail = itemView.findViewById(R.id.btShowDetail);
            priceResidence = itemView.findViewById(R.id.priceResidence);
            rateResidence = itemView.findViewById(R.id.rateResidence);
            rateStar = itemView.findViewById(R.id.rateStarResidence);
        }
    }
}
