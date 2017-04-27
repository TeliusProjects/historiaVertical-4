package com.example.admin.prova;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private String username;
    private String email;
    private String name;
    private String birthday;
    private String phoneNumber;
    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button bttnDone = (Button) findViewById(R.id.bttnChangeProfile);
        EditText editUsername = (EditText) findViewById(R.id.editUsername);


        final EditText editEmail = (EditText) findViewById(R.id.editEmail);
        final EditText editName= (EditText) findViewById(R.id.editName);
        final EditText editBirthday = (EditText) findViewById(R.id.editBirthday);
        final EditText editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        final Spinner spinnerGender = (Spinner) findViewById(R.id.spinnerGender);

        final String [] genders = {"Male","Female"};

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("mail");

        final JSONArray ArrayProfile = new JSONArray();
        final JSONObject jProfile = new JSONObject();
        try
        {

            jProfile.put("username", username);

            //add the JSONobject with the profileData to JSON array
            ArrayProfile.put(jProfile);

            //Shows in the android studio log what we are sending to our webService

            String JsonData = ArrayProfile.toString();

            new DoEditProfile().execute(JsonData);

            editUsername.setText(username);
            editEmail.setText(email);
            editName.setText(name);
            editBirthday.setText(birthday);
            editPhoneNumber.setText(phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        editUsername.setText(username);
        editEmail.setText(email);
        editName.setText(name);
        editBirthday.setText(birthday);
        editPhoneNumber.setText(phoneNumber);


        ArrayAdapter<String> adapterGender = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genders);
        spinnerGender.setAdapter(adapterGender);

       /* if(gender =="Male")
        {
            spinnerGender.setSelection(0);

        }else if(gender=="Female")
        {
            spinnerGender.setSelection(1);

        }*/


        bttnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email= editEmail.getText().toString();
                name = editName.getText().toString();
                birthday = editBirthday.getText().toString();
                phoneNumber = editPhoneNumber.getText().toString();
                gender = spinnerGender.getSelectedItem().toString();
                try {
                    jProfile.put("email", email);
                    jProfile.put("name", name);
                    jProfile.put("birthday",birthday);
                    jProfile.put("phoneNumber",phoneNumber);
                    jProfile.put("gender",gender);

                    //add the JSONobject with the profileData to JSON array
                    ArrayProfile.put(jProfile);

                    //Shows in the android studio log what we are sending to our webService

                    String JsonData = ArrayProfile.toString();

                    new DoEditProfile().execute(JsonData);


                }catch(JSONException e)
                {
                    e.printStackTrace();

                }

            }
        });

        editEmail.setText(email);
        editBirthday.setText(birthday);
        editName.setText(name);
        editPhoneNumber.setText(phoneNumber);

        /*if(gender=="Male")
        {
            spinnerGender.setSelection(0);

        }else if (gender == "Female")
        {
            spinnerGender.setSelection(1);

        }*/

    }


    //public boolean onCreateOptionsMenu(Menu menu) {
      //  MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_profile, menu);
        //return true;
    //}


    class DoEditProfile extends AsyncTask<String, Void, String> {


        private static final String URL = "http://192.168.134.137/REST/modifyProfile.php";


        @Override
        protected String doInBackground(String... Params) {

            String JsonData = Params[0];


            try {

                //Creates http url connection
                java.net.URL url = new URL(URL);

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

                Log.d("json api", "DoEditProfile.doInBackGround Json return: " + result);


                JSONObject jobject = new JSONObject(result);

                email       = jobject.getString("email");
                name        = jobject.getString("name");
                birthday    = jobject.getString("birthday");
                phoneNumber = jobject.getString("phoneNumber");
                gender      = jobject.getString("gender");

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

    }
}
