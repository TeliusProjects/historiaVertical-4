package com.example.admin.prova;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    private static final String URL = "http://192.168.134.137/REST/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton BtnLogIn = (ImageButton) findViewById(R.id.btnLogIn);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLogin);
        //TextInputEditText inputEditTextUser = (TextInputEditText) findViewById(R.id.editTextUser);
        final EditText EditTextUser = (EditText) findViewById(R.id.editTextUser);
        final EditText EditTextPassword = (EditText) findViewById(R.id.editTextPass);


        BtnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String user = EditTextUser.getText().toString();
                String pass = EditTextPassword.getText().toString();


                    LogIn log = new LogIn(user,pass);

                    JSONArray ArrayLogin = new JSONArray();
                    JSONObject jLogin = new JSONObject();

                    try {
                        jLogin.put("username",log.getUsername());
                        jLogin.put("Password",log.getPassword());

                        //add to JSON array
                        ArrayLogin.put(jLogin);

                        Log.d("json api","Json array converted from login " + ArrayLogin.toString() );

                        String JsonData= ArrayLogin.toString();

                        new DoCreateLogin().execute(JsonData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



            }


        });
    }

    /**
     * Created by admin on 16/03/2017.
     */
    class LogIn {
        private String username;
        private String Password;

        public LogIn(String username, String Password)
        {
            this.username = username;
            this.Password=Password;
        }

        public String getPassword() {
            return Password;
        }

        public String getUsername() {
            return username;
        }
    }

    /**
     * Created by admin on 16/03/2017.
     */
     class DoCreateLogin  extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... Params) {

                String JsonData = Params[0];


            try {

                //Creates http url connection
                URL url = new URL(URL);

                HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();

                //sets http url connection settings
                httpurlconnection.setRequestMethod("POST");

                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                httpurlconnection.connect();



                //send data
                OutputStream out = httpurlconnection.getOutputStream();

                out.write(JsonData.getBytes());



                // Read response
                StringBuilder responseSB = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));

                String line;

                while ( (line = in.readLine()) != null) {
                    responseSB.append(line);
                }

                String result= "";
                result = responseSB.toString();

                JSONObject jobject = new JSONObject(result);
                String user_name = "";
                String user_email = "";

                boolean correct_user = jobject.getBoolean("Correct");

                if(correct_user){
                    user_name = jobject.getString("Username");
                    user_email = jobject.getString("Email");
                    Intent menu_principal =
                            new Intent(LoginActivity.this, Menu_Principal.class);

                    menu_principal.putExtra("Username", user_name);
                    menu_principal.putExtra("Email", user_email);
                    startActivity(menu_principal);

                  //  ((LoginActivity)context).finish();

                }

                in.close();
                out.close();
                Log.d("json api","DoCreateLogIn.doInBackGround Json return: " + result);


                httpurlconnection.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }


    }
}



