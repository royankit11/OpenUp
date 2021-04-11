package com.example.openup.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.openup.CalendarEventData;
import com.example.openup.EventData;
import com.example.openup.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVACalendarEvent extends RecyclerView.Adapter<RVACalendarEvent.ViewHolder> {

    private List<CalendarEventData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    RVACalendarEvent(Context context, List<CalendarEventData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHost.setText(mData.get(position).getmHost());
        holder.mTime.setText(mData.get(position).getmTime());
        holder.mName.setText(mData.get(position).getmName());
        holder.mLink.setText(mData.get(position).getmLink());

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
        TextView mHost;
        TextView mTime;
        TextView mName;
        TextView mLink;

        ViewHolder(View itemView) {
            super(itemView);
            mHost = itemView.findViewById(R.id.tvHost);
            mTime = itemView.findViewById(R.id.tvTime);
            mName = itemView.findViewById(R.id.tvName);
            mLink = itemView.findViewById(R.id.tvLink);
        }

    }
}