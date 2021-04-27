package com.example.openup.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.openup.CalendarEventData;
import com.example.openup.ChatPageActivity;
import com.example.openup.EventData;
import com.example.openup.LoginActivity;
import com.example.openup.R;
import com.example.openup.RegisteredEventData;
import com.example.openup.ui.calendar.CalendarFragment;
import com.example.openup.ui.calendar.RVACalendarEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView mRecyclerView;
    List<EventData> mEventData = new ArrayList<>();
    RecyclerView mRecyclerView2;
    List<RegisteredEventData> mRegisteredEventData = new ArrayList<>();
    String baseUrl;
    RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        mRecyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mRecyclerView2 = root.findViewById(R.id.recyclerView2);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView2.setLayoutManager(mLinearLayoutManager2);

        mRecyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        ImageButton btn = root.findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                prefs.edit().putBoolean("IsLoggedIn", false).apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());

        String urlTemp = getString(R.string.localhostURL);
        baseUrl = urlTemp + "getRecentEvents";

        new myAsyncTaskGetRecentEvents().execute(baseUrl);

        baseUrl = urlTemp + "getRegisteredEvents";

        new myAsyncTaskGetRegisteredEvents().execute(baseUrl);

        Button fabChat = root.findViewById(R.id.fabChat);

        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatPageActivity.class);
                startActivity(intent);
            }
        });


        
        return root;
    }
    

    public class myAsyncTaskGetRecentEvents extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            JsonArrayRequest arrReq = new JsonArrayRequest(url[0], listener(), errorListener());
            requestQueue.add(arrReq);

            String done = "Logging In...";
            return done;
        }

        protected void onPostExecute(String done){

        }

        private Response.Listener<JSONArray> listener(){
            return new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONArray jsonArray = response;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject record = response.getJSONObject(i);

                            String error = record.getString("Error");

                            if (error.equals("")) {
                                String name = record.getString("EventName");
                                String host = record.getString("Host");
                                String time = record.getString("EventTime");
                                String date = record.getString("EventDate");
                                String link = record.getString("Link");
                                int picNum = record.getInt("PicNum");


                                EventData mEvent = new EventData(name, date + " " + time, picNum);
                                mEventData.add(mEvent);


                            } else {
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                        RVAEvent mAdapter = new RVAEvent(getContext(), mEventData);
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Unable to connect to database.", Toast.LENGTH_SHORT).show();

                    }

                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
        }

    }

    public class myAsyncTaskGetRegisteredEvents extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            JsonArrayRequest arrReq = new JsonArrayRequest(url[0], listener(), errorListener());
            requestQueue.add(arrReq);

            String done = "Logging In...";
            return done;
        }

        protected void onPostExecute(String done){

        }

        private Response.Listener<JSONArray> listener(){
            return new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONArray jsonArray = response;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject record = response.getJSONObject(i);

                            String error = record.getString("Error");

                            if (error.equals("")) {
                                String name = record.getString("EventName");
                                String host = record.getString("Host");
                                String time = record.getString("EventTime");
                                String date = record.getString("EventDate");
                                String link = record.getString("Link");


                                RegisteredEventData mRegisteredEvent = new RegisteredEventData(host, time, name, date, link);
                                mRegisteredEventData.add(mRegisteredEvent);


                            }
                        }
                        RVARegisteredEvents mAdapter = new RVARegisteredEvents(getContext(), mRegisteredEventData);
                        mRecyclerView2.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

                    }

                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
        }

    }
}