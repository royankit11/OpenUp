package com.example.openup.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.openup.ChatData;
import com.example.openup.R;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<ChatData> mData;
    private LayoutInflater mInflater;
    private OnChatListener mOnChatListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<ChatData> data, OnChatListener onChatListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mOnChatListener = onChatListener;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_mail_item, parent, false);
        return new ViewHolder(view, mOnChatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mSender.setText(mData.get(position).getmSender());
        holder.mDetails.setText(mData.get(position).getmDetails());
        holder.mTime.setText(mData.get(position).getmTime());
        String iconTxt = mData.get(position).getmSender().substring(0, 1);
        holder.mIcon.setText(iconTxt);

        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.mIcon.getBackground()).setColor(color);

    }

    // binds the data to the TextView in each row
    public void onBindViewHolder(ViewHolder holder, String sender, String details, String time) {

    }

    // total number of rows
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mSender;
        TextView mDetails;
        TextView mTime;
        TextView mIcon;
        OnChatListener onChatListener;

        ViewHolder(View itemView, OnChatListener onChatListener) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.tvIcon);
            mSender = itemView.findViewById(R.id.tvSender);
            mDetails = itemView.findViewById(R.id.tvDetails);
            mTime = itemView.findViewById(R.id.tvTime);
            this.onChatListener = onChatListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClick(getAdapterPosition());
        }
    }

    public interface OnChatListener{
        void onChatClick(int position);
    }
}