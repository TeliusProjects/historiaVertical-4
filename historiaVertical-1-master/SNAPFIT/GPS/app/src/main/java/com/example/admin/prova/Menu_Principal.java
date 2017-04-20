package com.example.admin.prova;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Menu_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    FloatingActionButton fabcamera;
    private ImageView mSetImage;
   //s private Button mOptionButtons;

    private String mPath;
    TextView txUsername = null;
    TextView txUserEmail = null;
    String user_name = "";
    String user_email = "";
    private static GridView gvImages = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //abrir como página principal el fragment_home
        HomeFragment homefrag = HomeFragment.newInstance("home1", "home2");
        FragmentManager managerhome = getSupportFragmentManager();
        managerhome.beginTransaction().replace(
                R.id.relative_for_fragment,
                homefrag,
                homefrag.getTag()
        ).commit();

        Intent intent = getIntent();

      //  mSetImage = (ImageView) findViewById(R.id.set_picture);

        user_name  = intent.getStringExtra("Username");
        user_email = intent.getStringExtra("Email");

        setContentView(R.layout.activity_menu__principal);

        gvImages = (GridView) findViewById(R.id.GridHome);

       // FillList fillList = new FillList(Menu_Principal.this);

        //fillList.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Botón abajo derecha (looks)
       /* FloatingActionButton fablooks = (FloatingActionButton) findViewById(R.id.home);
        fablooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                /*MyList_Fragment mylistfragment = MyList_Fragment.newInstance("list1", "list2");
                FragmentManager managerlist = getSupportFragmentManager();
                managerlist.beginTransaction().replace(
                        R.id.relative_for_fragment,
                        mylistfragment,
                        mylistfragment.getTag()
                ).commit();
            }
        });*/

        //Botón abajo medio (CAMERA)
        fabcamera = (FloatingActionButton) findViewById(R.id.camera);

        if(mayRequestStoragePermission()){
            fabcamera.setEnabled(true);
        }else{
            fabcamera.setEnabled(false);

        }
        fabcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        txUsername = (TextView) findViewById(R.id.Username);
        txUserEmail = (TextView) findViewById(R.id.EmailUser);
        if(user_name!= null) {
            txUsername.setText(user_name);
            txUserEmail.setText(user_email);
        }else{
            txUsername.setText("Username");
            txUserEmail.setText("useremail@user.com");
        }
        getMenuInflater().inflate(R.menu.menu__principal, menu);
        return true;
    }

    @Override
    //BOTON DE ARRIBA
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //boton del action bar (arriba) el settings
        if (id == R.id.action_settings) {

            Intent settings = new Intent(Menu_Principal.this, SettingsActivity.class);
            settings.putExtra("username", user_name);
            startActivity(settings);
            return true;
        }

        //boton del action bar (arriba) el logout
        if(id == R.id.action_logout) {
            Intent login = new Intent(Menu_Principal.this, MainActivity.class);
            startActivity(login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    //NAVIGATION DRAWER
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //boton de home
        if (id == R.id.nav_home) {

            HomeFragment homefrag = HomeFragment.newInstance("home1", "home2");
            FragmentManager managerhome = getSupportFragmentManager();
            managerhome.beginTransaction().replace(
                    R.id.relative_for_fragment,
                    homefrag,
                    homefrag.getTag()
            ).commit();

            /*Intent home = new Intent(Menu_Principal.this, Menu_Principal.class);
            startActivity(home);*/

            //android.support.v4.app.Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.drawer_layout);



            //setContentView(R.layout.activity_menu__principal);

           /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();*/

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        }

        //boton mylist
        if (id == R.id.nav_mylist) {
            // Handle the camera action

            /*TextView txView = (TextView) findViewById(R.id.content_text);
            TextView txtViewFr = (TextView) findViewById(R.id.content_frase);

            txView.setVisibility(View.INVISIBLE);
            txtViewFr.setVisibility(View.INVISIBLE);
            gvImages.setVisibility(View.INVISIBLE);*/

            MyList_Fragment mylistfragment = MyList_Fragment.newInstance("list1", "list2");
            FragmentManager managerlist = getSupportFragmentManager();
            managerlist.beginTransaction().replace(
                    R.id.relative_for_fragment,
                    mylistfragment,
                    mylistfragment.getTag()
            ).commit();

        //boton tags
        } else if (id == R.id.nav_tags) {

           /* TextView txView = (TextView) findViewById(R.id.content_text);
            TextView txtViewFr = (TextView) findViewById(R.id.content_frase);

            txView.setVisibility(View.INVISIBLE);
            txtViewFr.setVisibility(View.INVISIBLE);
            gvImages.setVisibility(View.INVISIBLE);*/

            Tags_Fragment mylistfragment = Tags_Fragment.newInstance("tags1", "tags2");
            FragmentManager managertags = getSupportFragmentManager();
            managertags.beginTransaction().replace(
                    R.id.relative_for_fragment,
                    mylistfragment,
                    mylistfragment.getTag()
            ).commit();

        } else if (id == R.id.nav_settings) {

                Intent settings = new Intent(Menu_Principal.this, SettingsActivity.class);
                settings.putExtra("username", user_name);
                startActivity(settings);

        } else if (id == R.id.nav_logout) {

            Intent login = new Intent(Menu_Principal.this, MainActivity.class);
            startActivity(login);

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
   /* public static class FillList extends AsyncTask<String,Void,ArrayList<String>> {
        private ArrayList<String> url_images = new ArrayList<String>();
        private static final String URL = "http://192.168.139.155/REST/rellenarlist.php";
        private Context context;
        private String urls;

        public FillList(Context context){
            this.context = context;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                java.net.URL url = new URL(URL);

                HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();

                //sets http url connection settings
                httpurlconnection.setRequestMethod("POST");

                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                httpurlconnection.connect();


                //send data




                // Read response
                StringBuilder responseSB = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));

                String line;

                InputStream is = null;
                while ((line = in.readLine()) != null) {
                    responseSB.append(line);
                }
                urls = responseSB.toString();

                JSONArray jsonArray = new JSONArray(urls);

                for (int i=0; i<jsonArray.length();i++){
                    String unique_url = jsonArray.getJSONObject(i).getString("url");
                    url_images.add(unique_url);
                }

                Log.d("json api","DoCreateLogIn.doInBackGround Json return: " + is);

            }catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return url_images;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            AdaptadorMenu gridviewadapter = new AdaptadorMenu((Menu_Principal)context,strings);

            gvImages.setAdapter(gridviewadapter);
        }
    }*/

    private boolean mayRequestStoragePermission() {

        return true;
    }
    private void showOptions(){
        final CharSequence[] options = {"Tomar foto", "Elegir de galería", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal.this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i] == "Tomar foto"){
                    openCamera();
                }else if(options[i] == "Elegir de galería"){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }
        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator
                    + imageName;
            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));

            startActivityForResult(intent, PHOTO_CODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch(requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this, new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned" + path + ":");
                                    Log.i("ExternalStorage", "-> Uri" + uri);
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);

                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;
            }
        }
    }

}
