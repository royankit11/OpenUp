package com.example.openup.ui.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openup.ChatData;
import com.example.openup.ChatPageActivity;
import com.example.openup.LoginActivity;
import com.example.openup.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements MyRecyclerViewAdapter.OnChatListener{

    private ChatViewModel chatViewModel;
    RecyclerView mRecyclerView;
    List<ChatData> mChatData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        ChatData mChat = new ChatData("Sam", "Hey, how are you doing?",
                "10:42am");
        mChatData.add(mChat);
        mChat = new ChatData("Richie", "Sounds good!",
                "2:04pm");
        mChatData.add(mChat);
        mChat = new ChatData("Janet", "I hope you are doing ok",
                "3:44pm");
        mChatData.add(mChat);



        MyRecyclerViewAdapter mAdapter = new MyRecyclerViewAdapter(getContext(), mChatData, this);
        mRecyclerView.setAdapter(mAdapter);


        return root;
    }

    @Override
    public void onChatClick(int position) {
        //mChatData.get(position);
        Intent intent = new Intent(getActivity(), ChatPageActivity.class);
        startActivity(intent);
    }
}