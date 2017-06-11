package com.example.admin.prova;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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

import static android.R.attr.inputType;
import static android.R.attr.password;

public class SettingsActivity extends AppCompatActivity {

    private static final String URL = MainActivity.URL + "remove.php";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final String user_name,mail;
        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        mail = intent.getStringExtra("mail");

        TextView txtUser_name = (TextView) findViewById(R.id.txtUser_name);
        ListView list_profile = (ListView) findViewById(R.id.list_Profile);
        ListView list_account = (ListView) findViewById(R.id.list_Account);

        txtUser_name.setText(user_name);

        String [] Profile = {"EDIT PROFILE","CHANGE PASSWORD"};
        String [] Account = {"DELETE ACCOUNT","SIGN OUT"};

        ListSettingsAdapter adapterProfile = new ListSettingsAdapter(this,Profile);
        list_profile.setAdapter(adapterProfile);

        ListSettingsAdapter adapterAccount = new ListSettingsAdapter(this, Account);
        list_account.setAdapter(adapterAccount);


        list_profile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i)
                {
                    case 0:
                            Intent profile = new Intent(SettingsActivity.this, ProfileActivity.class);
                            profile.putExtra("username",user_name);
                            profile.putExtra("mail",mail);
                            startActivity(profile);
                        break;
                    case 1:
                        Intent passwords = new Intent(SettingsActivity.this, PasswordActivity.class);
                        passwords.putExtra("username",user_name);
                        startActivity(passwords);
                        break;


                }
            }
        });

        list_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialog.setTitle("DELETE ACCOUNT");
                        alertDialog.setMessage("Enter the password to delete this account.");


                        final EditText input = new EditText(SettingsActivity.this);

                        input.setTransformationMethod(PasswordTransformationMethod.getInstance());

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        input.setLayoutParams(lp);
                        alertDialog.setView(input);

                        alertDialog.setPositiveButton("DELETE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int which) {

                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);

                                        alertDialogBuilder.setMessage("Are you sure you want to delete your account?");
                                        alertDialogBuilder.setTitle("Delete account");

                                        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                String password = input.getText().toString();
                                                String pass_hash = Encriptacio.md5(password);

                                                JSONArray ArrayDelete = new JSONArray();
                                                JSONObject jDelete = new JSONObject();

                                                try {
                                                    jDelete.put("username",user_name);
                                                    jDelete.put("password",pass_hash);


                                                    //add to JSON array
                                                    ArrayDelete.put(jDelete);

                                                    Log.d("json api","Json array converted from DeleteAccount " + ArrayDelete.toString() );

                                                    String JsonData= ArrayDelete.toString();

                                                    new SettingsActivity.DoDeleteUser().execute(JsonData);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });
                                        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                dialog.cancel();
                                            }
                                        });

                                        AlertDialog alertDialogFinal = alertDialogBuilder.create();
                                        alertDialogFinal.show();

                                    }
                                });

                        alertDialog.setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog1 = alertDialog.create();
                        alertDialog1.show();
                        break;
                    case 1:
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);

                        alertDialogBuilder.setMessage("Are you sure you want to exit?");
                        alertDialogBuilder.setTitle("LogOut");




                        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent logOut = new Intent(SettingsActivity.this, MainActivity.class);
                                startActivity(logOut);

                            }
                        });
                        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog alertDialog2 = alertDialogBuilder.create();
                        alertDialog2.show();

                        break;

                }
            }
        });
    }

    class DoDeleteUser  extends AsyncTask<String, Void, String> {

        private String photoPath;
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


                result = responseSB.toString();



                in.close();
                out.close();
                Log.d("json api","DoCreateLogIn.doInBackGround Json return: " + result);


                httpurlconnection.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String response) {


            try {


                JSONObject jobject = new JSONObject(result);
                boolean deleted = jobject.getBoolean("Deleted");
                String message = jobject.getString("message");

                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                if(deleted)
                {

                    Intent mainActivity = new Intent(SettingsActivity.this,MainActivity.class);
                    startActivity(mainActivity);

                }


            }catch(JSONException e)
            {
                e.printStackTrace();

            }

        }


    }
}

