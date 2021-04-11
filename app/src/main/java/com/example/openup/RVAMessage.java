package com.example.openup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.openup.ui.home.RVAEvent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVAMessage extends RecyclerView.Adapter<RVAMessage.ViewHolder>{
    private List<MessageData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    RVAMessage(Context context, List<MessageData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAMessage.ViewHolder holder, int position) {
        MessageData message = mData.get(position);

        if(message.getId().equals("Bot")) {
            holder.mBotMessage.setText(mData.get(position).getMessage());
        } else {
            holder.mMessage.setText(message.getMessage());
        }

    }

    // total number of rows
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mMessage;
        TextView mBotMessage;
        ViewHolder(View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.tv_message);
            mBotMessage = itemView.findViewById(R.id.tv_bot_message);
        }

    }
}
