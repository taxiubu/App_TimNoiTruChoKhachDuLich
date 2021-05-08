package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Feedback;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AFeedback extends RecyclerView.Adapter<AFeedback.ViewHolder>{
    ArrayList<Feedback>list;
    Context context;


    public AFeedback(ArrayList<Feedback> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public AFeedback.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AFeedback.ViewHolder holder, int position) {
        final Feedback feedback= list.get(position);
        holder.tvAvatar.setText(String.valueOf(feedback.getName().charAt(0)));
        holder.tvUserName.setText(feedback.getName());
        holder.tvFeedback.setText(feedback.getContent());
        holder.tvRateStar.setText(feedback.getRateStar());
        holder.tvDayFeedback.setText(feedback.getDay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAvatar,tvUserName,tvFeedback,tvDayFeedback,tvRateStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar= itemView.findViewById(R.id.tvAvatar);
            tvUserName= itemView.findViewById(R.id.tvUserName);
            tvFeedback= itemView.findViewById(R.id.tvFeedback);
            tvDayFeedback= itemView.findViewById(R.id.tvDayFeedback);
            tvRateStar= itemView.findViewById(R.id.tvRateStar);
        }
    }
}
