package com.example.openup.ui.calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.openup.CalendarEventData;
import com.example.openup.EventData;
import com.example.openup.R;
import com.example.openup.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVACalendarEvent extends RecyclerView.Adapter<RVACalendarEvent.ViewHolder> {

    private List<CalendarEventData> mData;
    private LayoutInflater mInflater;
    private Context context;
    RequestQueue requestQueue;

    // data is passed into the constructor
    RVACalendarEvent(Context context, List<CalendarEventData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHost.setText(mData.get(position).getmHost());
        holder.mTime.setText(mData.get(position).getmTime());
        holder.mName.setText(mData.get(position).getmName());
        holder.ID = mData.get(position).getmID();

        holder.registered = mData.get(position).getmRegistered();

        if(holder.registered) {
            holder.btnRegister.setVisibility(View.INVISIBLE);
            holder.btnUnregister.setVisibility(View.VISIBLE);
        } else {
            holder.btnRegister.setVisibility(View.VISIBLE);
            holder.btnUnregister.setVisibility(View.INVISIBLE);
        }

    }

    // binds the data to the TextView in each row
    public void onBindViewHolder(ViewHolder holder, String sender, String details, String time) {

    }

    // total number of rows
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mHost;
        TextView mTime;
        TextView mName;
        Button btnRegister;
        Button btnUnregister;
        int ID;
        boolean registered;
        String baseUrl;

        ViewHolder(View itemView) {
            super(itemView);
            mHost = itemView.findViewById(R.id.tvHost);
            mTime = itemView.findViewById(R.id.tvTime);
            mName = itemView.findViewById(R.id.tvName);

            btnRegister = itemView.findViewById(R.id.btnRegister);
            btnUnregister = itemView.findViewById(R.id.btnUnRegister);
            requestQueue = Volley.newRequestQueue(context);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlTemp = context.getResources().getString(R.string.localhostURL);
                    baseUrl = urlTemp + "RegisterForEvent/" + ID;

                    btnRegister.setVisibility(View.INVISIBLE);
                    btnUnregister.setVisibility(View.VISIBLE);

                    new myAsyncTaskRegisterEvent().execute(baseUrl);
                }
            });

            btnUnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlTemp = context.getResources().getString(R.string.localhostURL);
                    baseUrl = urlTemp + "UnregisterForEvent/" + ID;

                    btnRegister.setVisibility(View.VISIBLE);
                    btnUnregister.setVisibility(View.INVISIBLE);

                    new myAsyncTaskRegisterEvent().execute(baseUrl);
                }
            });
        }

    }

    public class myAsyncTaskRegisterEvent extends AsyncTask<String, Void, String> {
        //this calls API to register user
        @Override
        protected String doInBackground(String... url) {
            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url[0], (JSONObject) null, listener(), errorListener());

            requestQueue.add(arrReq);

            String done = "";
            return done;
        }

        protected void onPostExecute(String done) {

        }

        private Response.Listener<JSONObject> listener() {
            return new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String result = response.getString("Message");

                        if (result.equals("REGISTERED")) {
                            Toast.makeText(context, "Event registered successfully!", Toast.LENGTH_LONG).show();
                        } else if(result.equals("UNREGISTERED")){
                            Toast.makeText(context, "Event unregistered successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Could not register", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(context, "Unable to connect to database.", Toast.LENGTH_LONG).show();

                    }

                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            };
        }
    }
}