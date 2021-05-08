package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.myapplication.SQLFavoriteItem;

import java.util.ArrayList;

public class AItemFavoriteResidence extends RecyclerView.Adapter<AItemFavoriteResidence.ViewHoder>{
    private Context context;
    private ArrayList<Residence> residences;
    private SQLFavoriteItem sql;
    IOnClickItemPlace iOnClickItemPlace;
    public void setIOnClickItemPlace(IOnClickItemPlace iOnClickItemPlace) {
        this.iOnClickItemPlace = iOnClickItemPlace;
    }

    public AItemFavoriteResidence(Context context, ArrayList<Residence> residences) {
        this.context = context;
        this.residences = residences;
        sql= new SQLFavoriteItem(context);
    }

    @NonNull
    @Override
    public AItemFavoriteResidence.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.favorite_residence_item, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AItemFavoriteResidence.ViewHoder holder, int position) {
        final Residence residence = residences.get(position);
        final int[] checkButton = {0};
        holder.nameResidence.setText(residence.getName());
        holder.typeResidence.setText(residence.getType());
        holder.tvShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemPlace.onClickItem(residence);
            }
        });
        holder.priceResidence.setText(residence.getPrice());
        Glide.with(context)
                .load(residence.getImageLink())
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageResidence);
    }

    @Override
    public int getItemCount() {
        return residences.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView nameResidence, typeResidence,priceResidence,tvShowDetail;
        ImageView imageResidence;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            nameResidence = itemView.findViewById(R.id.nameResidence);
            typeResidence = itemView.findViewById(R.id.typeResidence);
            imageResidence = itemView.findViewById(R.id.imageResidence);
            priceResidence = itemView.findViewById(R.id.priceResidence);
            tvShowDetail = itemView.findViewById(R.id.tvShowDetail);
        }
    }
}
