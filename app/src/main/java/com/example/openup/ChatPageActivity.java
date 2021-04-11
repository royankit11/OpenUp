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

    EditText message;
    RecyclerView mRecyclerView;
    List<MessageData> mMessageData = new ArrayList<>();
    RVAMessage mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        message = findViewById(R.id.et_message);
        mRecyclerView = findViewById(R.id.rv_messages);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        MessageData message = new MessageData("Yo what's good", "Bot");
        mMessageData.add(message);

        mAdapter = new RVAMessage(this, mMessageData);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void respond (View v){

    }
}