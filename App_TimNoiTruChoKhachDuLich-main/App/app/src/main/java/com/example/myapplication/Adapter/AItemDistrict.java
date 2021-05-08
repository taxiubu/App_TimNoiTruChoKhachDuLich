package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOnClickDistrict;
import com.example.myapplication.Model.District;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AItemDistrict extends RecyclerView.Adapter<AItemDistrict.ViewHoder>{
    private Context context;
    private ArrayList<District> districts;
    IOnClickDistrict iOnClickDistrict;

    public void setiOnClickDistrict(IOnClickDistrict iOnClickDistrict) {
        this.iOnClickDistrict = iOnClickDistrict;
    }

    public AItemDistrict(Context context, ArrayList<District> districts) {
        this.context = context;
        this.districts = districts;
    }

    @NonNull
    @Override
    public AItemDistrict.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_district, parent, false);
        return new AItemDistrict.ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AItemDistrict.ViewHoder holder, int position) {
        final District district = districts.get(position);
        holder.tvName.setText(district.getName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickDistrict.onClickItem(district);
            }
        });
    }
    public void filterList(ArrayList<District> filterList) {
        districts= filterList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return districts.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tvNameDistrict);
        }
    }
}
