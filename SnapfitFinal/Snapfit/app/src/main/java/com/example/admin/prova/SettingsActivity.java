package com.example.admin.prova;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final String user_name;
        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");

        TextView txtUser_name = (TextView) findViewById(R.id.txtUser_name);
        ListView list_profile = (ListView) findViewById(R.id.list_Profile);
        ListView list_account = (ListView) findViewById(R.id.list_Account);



        String [] Profile = {"EDIT PROFILE","CHANGE PASSWORD","MAS","MIERDA","QUE", "PONER"};
        String [] Account = {"DELETE ACCOUNT","SIGN OUT"};

        ListSettingsAdapter adapterProfile = new ListSettingsAdapter(this,Profile);
        list_profile.setAdapter(adapterProfile);

        ListSettingsAdapter adapterAccount = new ListSettingsAdapter(this, Account);
        list_account.setAdapter(adapterAccount);


        list_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 1:

                        break;
                    case 2:
                        Intent changePassword = new Intent(SettingsActivity.this,PasswordActivity.class);
                        changePassword.putExtra("username",user_name);
                        startActivity(changePassword);

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
