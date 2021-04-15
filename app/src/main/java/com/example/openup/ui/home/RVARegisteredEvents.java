package com.example.openup.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.openup.EventData;
import com.example.openup.R;
import com.example.openup.RegisteredEventData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVARegisteredEvents extends RecyclerView.Adapter<RVARegisteredEvents.ViewHolder> {

    private List<RegisteredEventData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    RVARegisteredEvents(Context context, List<RegisteredEventData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_registered_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mName.setText(mData.get(position).getmName());
        holder.mHost.setText(mData.get(position).getmHost());
        holder.mTime.setText(mData.get(position).getmTime());
        holder.mDate.setText(mData.get(position).getmDate());
        holder.mLink.setText("meet.google.com/" + mData.get(position).getmLink());

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
        TextView mHost;
        TextView mTime;
        TextView mDate;
        TextView mLink;

        ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvRName);
            mHost = itemView.findViewById(R.id.tvRHost);
            mTime = itemView.findViewById(R.id.tvRTime);
            mDate = itemView.findViewById(R.id.tvRDate);
            mLink = itemView.findViewById(R.id.tvRLink);
        }

    }
}