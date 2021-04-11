package com.example.openup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    String baseUrl;
    RequestQueue requestQueue;
    EditText txtUsernameReg;
    EditText txtPasswordReg;
    EditText txtPasswordConfirm;
    EditText txtFirstName;
    EditText txtLastName;
    String username;
    String id;
    //AESCrypt aesCrypt;
    //boolean updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        baseUrl = getString(R.string.localhostURL) + "Register/";

        Intent receiveRegister = getIntent();

        txtUsernameReg = findViewById(R.id.txtUsername);
        txtPasswordReg = findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        txtFirstName = findViewById(R.id.txtfName);
        txtLastName = findViewById(R.id.txtlName);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void registerNewUser(View view){

        String strRegisterUsername = String.valueOf(txtUsernameReg.getText());
        String strRegisterPassword = String.valueOf(txtPasswordReg.getText());
        String strPasswordConfirm = String.valueOf(txtPasswordConfirm.getText());
        String strFirstName = String.valueOf(txtFirstName.getText());;
        String strLastName = String.valueOf(txtLastName.getText());

        if(!strRegisterUsername.equals("")) {
            if(!strRegisterPassword.equals("")) {
                if(strRegisterPassword.equals(strPasswordConfirm)) {
                    //String strEncryptedPassword = aesCrypt.encrypt(strRegisterPassword);
                    String final_url = baseUrl + strRegisterUsername + "/" + strRegisterPassword + "/" + strFirstName + "/" +
                            strLastName;

                    new myAsyncTaskRegister().execute(final_url);
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            } else {
            Toast.makeText(RegisterActivity.this, "Please enter a password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Please enter a username", Toast.LENGTH_LONG).show();
        }
    }

    public void backToLogin(View view) { finish(); }

    public class myAsyncTaskRegister extends AsyncTask<String, Void, String> {
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

                        if (result.equals("SUCCESS")) {
                            finish();
                            Toast.makeText(RegisterActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(RegisterActivity.this, "Unable to connect to database.", Toast.LENGTH_LONG).show();

                    }

                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            };
        }
    }
}