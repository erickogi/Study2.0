package com.erickogi14gmail.study20.Main.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.MainActivity;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText emailText, passwordText;
    TextView signUpText, loginButton;
    //  AppCompatButton loginButton;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signUpText = (TextView) findViewById(R.id.link_signup);

        loginButton = (TextView) findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    onLoginFailed();
                    return;
                }

                loginButton.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                        R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                if (!login()) {
                    progressDialog.dismiss();
                }


            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(api.SHARED_PREF_NAME, Context.MODE_PRIVATE);


        loggedIn = sharedPreferences.getBoolean(api.LOGGEDIN_SHARED_PREF, false);


        if (loggedIn) {

            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {

        StyleableToast st = new StyleableToast(Login.this, "Login failed", Toast.LENGTH_SHORT);
        st.setBackgroundColor(Color.parseColor("#ff9040"));
        st.setTextColor(Color.WHITE);
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();


        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private boolean login() {
        boolean success = false;

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, api.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase(api.LOGIN_SUCCESS)) {

                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences(api.SHARED_PREF_NAME, Context.MODE_PRIVATE);


                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            editor.putBoolean(api.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(api.EMAIL_SHARED_PREF, email);
                            editor.putBoolean(api.ADMINCHANNEL_SHARED_PREF, false);


                            editor.commit();


                            Intent intent = new Intent(Login.this, MainActivity.class);
                            // MainActivity.requestDataSources(api.CHANNELS_END_POINT+1, getApplicationContext());
                            startActivity(intent);
                            finish();
                        } else {

                            StyleableToast st = new StyleableToast(Login.this, "Invalid username or password", Toast.LENGTH_SHORT);
                            st.setBackgroundColor(Color.parseColor("#ff9040"));
                            st.setTextColor(Color.WHITE);
                            st.setIcon(R.drawable.ic_error_outline_white_24dp);

                            st.setMaxAlpha();
                            st.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StyleableToast st = new StyleableToast(Login.this, "Error Authenticating", Toast.LENGTH_SHORT);
                        st.setBackgroundColor(Color.parseColor("#ff5a5f"));
                        st.setTextColor(Color.WHITE);
                        st.setIcon(R.drawable.ic_error_outline_white_24dp);

                        st.setMaxAlpha();
                        st.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(api.KEY_EMAIL, email);
                params.put(api.KEY_PASSWORD, password);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        return success;
    }

}
