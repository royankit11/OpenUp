package com.example.openup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.openup.ui.chat.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatPageActivity extends AppCompatActivity {

    EditText txtMessage;
    RecyclerView mRecyclerView;
    List<MessageData> mMessageData = new ArrayList<>();
    RVAMessage mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        txtMessage = findViewById(R.id.et_message);
        mRecyclerView = findViewById(R.id.rv_messages);

        mLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);


        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        MessageData message = new MessageData("Yo what's good", "Bot");
        mMessageData.add(message);

        mAdapter = new RVAMessage(this, mMessageData);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void send (View v){

        MessageData message = new MessageData(txtMessage.getText().toString(), "user");
        mMessageData.add(message);

        txtMessage.setText("");

        mAdapter = new RVAMessage(this, mMessageData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                botResponse();
            }
        }, 2000);

    }

    public void botResponse() {
        MessageData message = new MessageData("Great! Are you using the breathing techniques I taught you?", "Bot");
        mMessageData.add(message);

        mAdapter = new RVAMessage(this, mMessageData);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
    }
}