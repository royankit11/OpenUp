package com.example.openup.ui.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.openup.CalendarEventData;
import com.example.openup.ChatData;
import com.example.openup.EventData;
import com.example.openup.LoginActivity;
import com.example.openup.NavigationActivity;
import com.example.openup.R;
import com.example.openup.RegisterActivity;
import com.example.openup.ui.chat.MyRecyclerViewAdapter;
import com.example.openup.ui.home.RVAEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private CalendarViewModel dashboardViewModel;
    TextView txtDate;
    RequestQueue requestQueue;
    String baseUrl;
    RecyclerView mRecyclerView;
    List<CalendarEventData> mEventData = new ArrayList<>();
    CompactCalendarView calendarView;
    TextView txtMonth;
    ImageButton scrollLeft;
    ImageButton scrollRight;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = root.findViewById(R.id.compactcalendar_view);
        mRecyclerView = root.findViewById(R.id.recyclerView);
        txtDate = root.findViewById(R.id.date);

        scrollLeft = root.findViewById(R.id.scrollLeft);
        scrollRight = root.findViewById(R.id.scrollRight);

        scrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.scrollLeft();
            }
        });

        scrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.scrollRight();
            }
        });


        txtMonth = root.findViewById(R.id.txtMonth);
        Date date = new Date();
        txtMonth.setText(dateFormatMonth.format(date));


        requestQueue = Volley.newRequestQueue(getContext());

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        String urlTemp = getString(R.string.localhostURL);
        baseUrl = urlTemp + "getAllEvents";

        new myAsyncTaskGetAllEvents().execute(baseUrl);

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mRecyclerView.removeAllViewsInLayout();

                mEventData.clear();

                int month = dateClicked.getMonth();
                int day = dateClicked.getDate();
                int year = dateClicked.getYear() + 1900;

                String date = (month + 1) + "/" +day;
                txtDate.setText(date);

                String urlTemp = getString(R.string.localhostURL);
                baseUrl = urlTemp + "getEvents/" +  (month + 1) + "/" + day + "/" + year;

                new myAsyncTaskGetEvents().execute(baseUrl);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtMonth.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });


        return root;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public class myAsyncTaskGetEvents extends AsyncTask<String, Void, String> {

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
                                boolean registered;
                                if(record.getString("Registered").equals("Yes")) {
                                    registered = true;
                                } else {
                                    registered = false;
                                }
                                int ID = record.getInt("ID");

                                CalendarEventData mCalEvent = new CalendarEventData(host, time,
                                        name, ID, registered);
                                mEventData.add(mCalEvent);
                                //Toast.makeText(getContext(), name + " " + host + " " + time + " " + link, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                        RVACalendarEvent mAdapter = new RVACalendarEvent(getContext(), mEventData);
                        mRecyclerView.setAdapter(mAdapter);

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

    public class myAsyncTaskGetAllEvents extends AsyncTask<String, Void, String> {

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
                                String date = record.getString("EventDate");

                                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                                Date objDate = format.parse(date);

                                long millis = objDate.getTime();

                                if(calendarView.getEvents(millis).size() == 0) {
                                    Event ev1 = new Event(Color.RED, millis, "Name");
                                    calendarView.addEvent(ev1);
                                }

                            } else {
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException | ParseException e) {
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
}