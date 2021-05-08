package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class AdapterSliderView extends SliderViewAdapter<AdapterSliderView.SliderViewHolder> {
    List<String> listImage;
    Context context;

    public AdapterSliderView(List<String> listImage, Context context) {
        this.listImage = listImage;
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_slider_view, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Glide.with(context)
                .load(listImage.get(position))
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.ivImageCover);
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView ivImageCover;
        public SliderViewHolder(View itemView) {
            super(itemView);
            ivImageCover= itemView.findViewById(R.id.ivImageCover);
        }
    }
}
