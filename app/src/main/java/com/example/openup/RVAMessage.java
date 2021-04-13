package com.example.openup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            if(message.getMessage().length() > 25) {
                holder.mBotMessage.setWidth(400);
            }
            holder.mBotMessage.setText(message.getMessage());
            holder.mMessage.setVisibility(View.INVISIBLE);
        } else {
            if(message.getMessage().length() > 25) {
                holder.mMessage.setWidth(400);
            }
            holder.mMessage.setText(message.getMessage());
            holder.mBotMessage.setVisibility(View.INVISIBLE);
            holder.botIcon.setVisibility(View.INVISIBLE);
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
        TextView botIcon;
        ViewHolder(View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.tv_message);
            mBotMessage = itemView.findViewById(R.id.tv_bot_message);
            botIcon = itemView.findViewById(R.id.botIcon);
        }

    }
}
