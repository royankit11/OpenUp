package com.example.openup.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openup.EventData;
import com.example.openup.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView mRecyclerView;
    List<EventData> mEventData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        EventData mEvent = new EventData("Sam", "10:42am");
        mEventData.add(mEvent);
        mEvent = new EventData("Richie", "2:04pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);
        mEvent = new EventData("Janet", "3:44pm");
        mEventData.add(mEvent);

        RVAEvent mAdapter = new RVAEvent(getContext(), mEventData);
        mRecyclerView.setAdapter(mAdapter);
        
        return root;
    }
}