package com.example.openup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatPageActivity extends AppCompatActivity {

    EditText txtMessage;
    RecyclerView mRecyclerView;
    List<MessageData> mMessageData = new ArrayList<>();
    RVAMessage mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    Map<String, String> responses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        txtMessage = findViewById(R.id.et_message);
        mRecyclerView = findViewById(R.id.rv_messages);

        mLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        MessageData message = new MessageData("Hey, how are you doing?", "Bot");
        mMessageData.add(message);

        mAdapter = new RVAMessage(this, mMessageData);
        mRecyclerView.setAdapter(mAdapter);

        responses = new HashMap<>();

        responses.put("Fine, I guess", "I can tell something's bothering you. What is it?");
        responses.put("It's nothing really", "You can always talk to me.");
        responses.put("I had a fight with my boyfriend yesterday", "I am sorry to hear that. Have you tried using the breathing exercises?");
        responses.put("It didn't really work", "You should join a therapy group. Maybe talking with others will make you feel better.");
        responses.put("I'll think about it", "Good. If you need anything, I am always here for you.");

    }

    public void send (View v){

        String userMessage = txtMessage.getText().toString();

        MessageData message = new MessageData(userMessage, "user");
        mMessageData.add(message);

        txtMessage.setText("");

        mAdapter = new RVAMessage(this, mMessageData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                botResponse(responses.get(userMessage));
            }
        }, 2000);

    }

    public void botResponse(String response) {
        MessageData message = new MessageData(response, "Bot");
        mMessageData.add(message);

        mAdapter = new RVAMessage(this, mMessageData);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
    }
}