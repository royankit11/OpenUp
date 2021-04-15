package com.example.openup.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.openup.ChatData;
import com.example.openup.EventData;
import com.example.openup.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVAEvent extends RecyclerView.Adapter<RVAEvent.ViewHolder> {

    private List<EventData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    RVAEvent(Context context, List<EventData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_upcomingevents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Map<Integer, Integer> images = new HashMap<>();

        images.put(1, R.drawable.women_pic1);
        images.put(2, R.drawable.women_pic2);
        images.put(3, R.drawable.women_pic3);
        images.put(4, R.drawable.women_pic4);

        holder.mName.setText(mData.get(position).getmHost());
        holder.mTime.setText(mData.get(position).getmTime());
        holder.backgroundImage.setImageResource(images.get(mData.get(position).getmImage()));

    }

    // binds the data to the TextView in each row
    public void onBindViewHolder(ViewHolder holder, String sender, String details, String time) {

    }

    // total number of rows
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mTime;
        ImageView backgroundImage;
        ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName);
            mTime = itemView.findViewById(R.id.tvDatetime);
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
        }

    }
}