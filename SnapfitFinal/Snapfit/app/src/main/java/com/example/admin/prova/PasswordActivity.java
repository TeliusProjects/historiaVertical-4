package com.example.admin.prova;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasswordActivity extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        final EditText editOldPass = (EditText) findViewById(R.id.editOldPassword);
        final EditText editNewPass = (EditText) findViewById(R.id.editNewPassword);
        final EditText editReNewPass = (EditText) findViewById(R.id.editRepeatNewPassword);
        Button bttnChange = (Button) findViewById(R.id.bttnChange);
        final String user_name;
        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");


        bttnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gets the three password from each editText
                String oldPassword = editOldPass.getText().toString();
                String newPassword = editNewPass.getText().toString();
                String reNewPassword = editReNewPass.getText().toString();

                String oldPassword_hash = Encriptacio.md5(oldPassword);
                String newPassword_hash = Encriptacio.md5(newPassword);

                if (newPassword.equals(reNewPassword))
                {
                    JSONArray ArrayPasswords = new JSONArray();
                    JSONObject jPass = new JSONObject();

                    try {
                        jPass.put("username", user_name);
                        jPass.put("oldPassword", oldPassword_hash);
                        jPass.put("newPassword", newPassword_hash);

                        //add the JSONobject with the both passwords to JSON array
                        ArrayPasswords.put(jPass);

                        //Shows in the android studio log what we are sending to our webService

                        String JsonData = ArrayPasswords.toString();

                        DoChangePassword changePassword = (DoChangePassword) new DoChangePassword().execute(JsonData);
                        changePassword.onPostExecute();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //shows in a toast the error message
                    Toast.makeText(getApplicationContext(),
                            "The new passwords does not match", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    class DoChangePassword extends AsyncTask<String, Void, String> {


        private static final String URL = "http://192.168.134.137/REST/modify.php";


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

                while ((line = in.readLine()) != null)
                {
                    responseSB.append(line);
                }

                String result = "";


                result = responseSB.toString();

                Log.d("json api", "DoCreateLogIn.doInBackGround Json return: " + result);


                JSONObject jobject = new JSONObject(result);
                boolean isChanged = jobject.getBoolean("isChanged");


                if(isChanged)
                {
                    message = "Your password has been changed.";

                }else
                {
                    message = "Your old password is not correct.";

                }

                in.close();
                out.close();


                httpurlconnection.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute()
        {


            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

        }
    }



}
