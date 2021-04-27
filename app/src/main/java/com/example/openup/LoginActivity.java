package com.example.openup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    EditText txtUsername;
    EditText txtPassword;
    String baseUrl;
    String strUsername;
    String strPassword;
    RequestQueue requestQueue;
    AESCrypt aesCrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aesCrypt = new AESCrypt();

        String urlTemp = getString(R.string.localhostURL);
        baseUrl = urlTemp + "getUser/";

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void login(View v) throws Exception {

        strUsername = String.valueOf(txtUsername.getText());
        strPassword = String.valueOf(txtPassword.getText());

        if(!strUsername.equals("")) {
            if(!strPassword.equals("")) {
                String strEncryptedPassword = aesCrypt.encrypt(strPassword);
                String full_api_url = baseUrl + strUsername + "/" + strEncryptedPassword + "/" + false;
                new myAsyncTaskLogIn().execute(full_api_url);
            } else {
                Toast.makeText(LoginActivity.this, "Provide password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Provide username", Toast.LENGTH_LONG).show();
        }
    }

    public void goToRegisterCompost(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);

    }

    public class myAsyncTaskLogIn extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url[0], (JSONObject) null, listener(), errorListener());
            requestQueue.add(arrReq);

            String done = "Logging In...";
            return done;
        }

        protected void onPostExecute(String done){

        }

        private Response.Listener<JSONObject> listener(){
            return new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = response;
                        String error = jsonObject.get("Error").toString();

                        //if both the username and password match, then parse the other user information values
                        //and go to bringToHomePage function
                        if (error.equals("")) {
                            String jsonUsername = jsonObject.get("Username").toString();
                            String firstName = jsonObject.get("FirstName").toString();
                            String lastName =jsonObject.get("LastName").toString();
                            Integer id = jsonObject.getInt("ID");


                            bringToHomePage(jsonUsername, firstName, lastName, id);
                        } else {
                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, "Unable to connect to database.", Toast.LENGTH_SHORT).show();

                    }

                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
        }
        private void bringToHomePage (String jsonUsername, String firstName, String lastName, Integer id) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            prefs.edit().putBoolean("IsLoggedIn", true).apply();

            Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();

            Intent homePage = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(homePage);
        }

    }
}

