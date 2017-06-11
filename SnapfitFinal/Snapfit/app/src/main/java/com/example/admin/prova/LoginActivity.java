package com.example.admin.prova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    private static final String URL = MainActivity.URL + "login.php";

    private LogIn log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button BtnLogIn = (Button) findViewById(R.id.btnLogIn);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLogin);
        TextView txtPassword = (TextView) findViewById(R.id.txtForgotenPassword);
        final EditText EditTextUser = (EditText) findViewById(R.id.editTextUser);
        final EditText EditTextPassword = (EditText) findViewById(R.id.editTextPass);


        BtnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String user = EditTextUser.getText().toString();
                String pass = EditTextPassword.getText().toString();

                String pass_hashed = Encriptacio.md5(pass);


                log  = new LogIn(user,pass_hashed);

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

        txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                alertDialog.setTitle("FORGOTTEN PASSWORD");
                alertDialog.setMessage("Enter your email address associated with your account.We will send you a recovery password.");


                final EditText input = new EditText(LoginActivity.this);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("SEND PASSWORD",new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which)
                    {

                        String mail= input.getText().toString();

                        JSONArray ArrayEmail = new JSONArray();
                        JSONObject jMail = new JSONObject();

                        try {
                            jMail.put("mail",mail);
                            //add to JSON array
                            ArrayEmail.put(jMail);

                            Log.d("json api","Json array converted from DeleteAccount " + ArrayEmail.toString() );

                            String JsonData= ArrayEmail.toString();

                            new LoginActivity.sendMail().execute(JsonData);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
            }
        });
    }

    /**
     * Created by admin on 16/03/2017.
     */
    /**
     * Created by admin on 16/03/2017.
     */
    class DoCreateLogin  extends AsyncTask<String, Void, String> {

        private String photoPath;
        @Override
        protected String doInBackground(String... Params) {

            String JsonData = Params[0];
            String result = "";

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

                JSONObject jobject = new JSONObject(result);
                String user_name = "";
                String user_email = "";

                boolean correct_user = jobject.getBoolean("Correct");

                in.close();
                out.close();
                Log.d("json api","DoCreateLogIn.doInBackGround Json return: " + result);

                httpurlconnection.disconnect();

                if(correct_user){
                    Intent menu_principal =
                            new Intent(LoginActivity.this, Menu_Principal.class);

                    user_name = jobject.getString("Username");
                    user_email = jobject.getString("Email");
                    photoPath  = jobject.getString("ProfileImage");
                    String encodedImage = jobject.getString("EncodedProfileImage");
                    String androidPath = jobject.getString("ProfilePath");


                    if(!photoPath.equals(null) && !androidPath.equals("null")){
                        File newFile = new File(androidPath);
                        log.setEncoded_profileImageBitmap(encodedImage);
                        log.setProfileImageName(newFile.getName());
                        log.setEmail(user_email);
                        log.setUsername(user_name);
                        log.setAndroidPath(androidPath);
                        menu_principal.putExtra("Encoded_Profile",log.getEncoded_profileImageBitmap());
                        menu_principal.putExtra("Android_path",androidPath);
                        menu_principal.putExtra("ProfileImageName",log.getProfileImageName());
                    }


                    menu_principal.putExtra("Username", user_name);
                    menu_principal.putExtra("Email", user_email);
                    menu_principal.putExtra("ProfileImage", photoPath);
                    menu_principal.putExtra("Log", log);
                    startActivity(menu_principal);

                    //  ((LoginActivity)context).finish();

                }




            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if(!jsonObject.getBoolean("Correct")){
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    class sendMail  extends AsyncTask<String, Void, String> {


        JSONObject jobject;
        @Override
        protected String doInBackground(String... Params) {

            String JsonData = Params[0];
            String result = "";

            try {

                //Creates http url connection
                URL url = new URL(MainActivity.URL + "/mail.php");

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

                jobject = new JSONObject(result);

                in.close();
                out.close();
                Log.d("json api","DoCreateLogIn.doInBackGround Json return: " + result);


                httpurlconnection.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            boolean respuesta;
            int status;


            try {
                respuesta = jobject.getBoolean("sent");
                status= jobject.getInt("status");
                if(respuesta)
                {
                    Toast.makeText(LoginActivity.this,"An e-mail with the recovery password has been sended. Check your inbox.",Toast.LENGTH_LONG).show();
                }else
                {

                    switch(status)
                    {
                        case 1:
                            Toast.makeText(LoginActivity.this,"The email field is empty. Please, introduce a valid email.",Toast.LENGTH_LONG).show();
                           break;
                        case 2:
                            Toast.makeText(LoginActivity.this,"Couldn't send you the recovery password.",Toast.LENGTH_LONG).show();
                            break;
                        case 3:
                            Toast.makeText(LoginActivity.this,"This email does not exist. Please, try again.",Toast.LENGTH_LONG).show();
                            break;

                    }

                }

            } catch(JSONException e) {
                e.printStackTrace();
            }

        }
    }
}




