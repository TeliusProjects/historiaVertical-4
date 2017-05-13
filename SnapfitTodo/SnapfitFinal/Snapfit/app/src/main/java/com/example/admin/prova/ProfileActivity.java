package com.example.admin.prova;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    EditText editUsername;
    EditText editEmail;
    EditText editName;
    EditText editBirthday;
    EditText editPhoneNumber;
    Spinner spinnerGender;
    JSONObject jProfile;
    JSONArray ArrayProfile;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         editUsername = (EditText) findViewById(R.id.editUsername);


         editEmail         = (EditText) findViewById(R.id.editEmail);
         editName          = (EditText) findViewById(R.id.editName);
         editBirthday      = (EditText) findViewById(R.id.editBirthday);
         editPhoneNumber   = (EditText) findViewById(R.id.editPhoneNumber);
         spinnerGender     = (Spinner) findViewById(R.id.spinnerGender);


        final String [] genders = {"Gender...","Male","Female"};

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("mail");

        ArrayProfile = new JSONArray();
        jProfile = new JSONObject();

        ArrayAdapter<String> adapterGender = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genders);
        spinnerGender.setAdapter(adapterGender);

        try
        {

            jProfile.put("username", username);

            //add the JSONobject with the profileData to JSON array
            ArrayProfile.put(jProfile);

            //Shows in the android studio log what we are sending to our webService

            String JsonData = ArrayProfile.toString();

            new DoEditProfile().execute(JsonData);




        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    public void getEditTexts()
    {

        email= editEmail.getText().toString();
        name = editName.getText().toString();
        birthday = editBirthday.getText().toString();
        phoneNumber = editPhoneNumber.getText().toString();
        gender = spinnerGender.getSelectedItem().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.BttnProfile)
        {

                getEditTexts();


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

                   DoEditProfile editProfile = new DoEditProfile();

                    editProfile.execute(JsonData);


                }catch(JSONException e)
                {
                    e.printStackTrace();

                }

            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class DoEditProfile extends AsyncTask<String, Void, String> {


        private static final String URL = "http://192.168.1.234/REST/modifyProfile.php";


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

               result = "";


                result = responseSB.toString();

                Log.d("json api", "DoEditProfile.doInBackGround Json return: " + result);

                in.close();
                out.close();


                httpurlconnection.disconnect();
            } catch (IOException e)
            {
                e.printStackTrace();
            }




            return null;
        }

        @Override
        protected void onPostExecute(String response) {

            try {


                JSONObject jobject = new JSONObject(result);

                email       = jobject.getString("email");
                name        = jobject.getString("name");
                birthday    = jobject.getString("birthday");
                phoneNumber = jobject.getString("phoneNumber");
                gender      = jobject.getString("gender");

                editUsername.setText(username);
                editEmail.setText(email);
                editName.setText(name);
                editBirthday.setText(birthday);
                editPhoneNumber.setText(phoneNumber);

                int index = 0;

                for (int i=0;i<spinnerGender.getCount();i++)
                {
                    if (spinnerGender.getItemAtPosition(i).equals(gender))
                    {
                        index = i;
                    }
                }
                spinnerGender.setSelection(index);

            }catch(JSONException e)
            {
                e.printStackTrace();

            }
        }
    }
}
