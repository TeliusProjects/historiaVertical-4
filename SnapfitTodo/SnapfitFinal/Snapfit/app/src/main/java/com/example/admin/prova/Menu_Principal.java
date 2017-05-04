package com.example.admin.prova;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.kosalgeek.android.photoutil.*;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int PERMS_REQUEST_CODE = 123;
    private static final int READ_REQUEST_CODE = 122;
    private final int PHOTO_CODE = 13323;
    private final int SELECT_PICTURE = 12345;
    private Bitmap bitmap;
    private LogIn logData;
    FloatingActionButton fabcamera;
    String selectedPhoto;
    boolean entrado  = false;
    private final String TAG = this.getClass().getName();
   //s private Button mOptionButtons;


    private String result = "";
    private ConnectiongDetector cd;
    CameraPhoto cameraPhoto = null;
    GalleryPhoto galleryPhoto = null;

    TextView txUsername = null;
    TextView txUserEmail = null;
    String user_name = "";
    String user_email = "";
    String imageURL   = "";
    boolean clicked = false;
    private static GridView gvImages = null;
    private ImageView bussinesImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //abrir como página principal el fragment_home
        HomeFragment homefrag = HomeFragment.newInstance("home1", "home2");
        FragmentManager managerhome = getSupportFragmentManager();
        managerhome.beginTransaction().replace(
                R.id.relative_for_fragment,
                homefrag,
                homefrag.getTag()

        ).commit();


      //  mSetImage = (ImageView) findViewById(R.id.set_picture);

        user_name  = intent.getStringExtra("Username");
        user_email = intent.getStringExtra("Email");
        imageURL   = intent.getStringExtra("ProfileImage");
        logData    = (LogIn) intent.getSerializableExtra("UserObj");

        setContentView(R.layout.activity_menu__principal);

        cd = new ConnectiongDetector(Menu_Principal.this);
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        cd = new ConnectiongDetector(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                showOptions(false);
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
        bussinesImageView = (ImageView) findViewById(R.id.imageViewUser);

        if(!imageURL.equals("null")){
            Picasso
                    .with(Menu_Principal.this)
                    .load(imageURL)
                    .transform(new CircleTransform())
                    .into(bussinesImageView);
        }

        bussinesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                showOptions(clicked);
            }
        });
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
            settings.putExtra("email",user_email);
            startActivity(settings);
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


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        }

        //boton mylist
        if (id == R.id.nav_mylist) {

            MyList_Fragment mylistfragment = MyList_Fragment.newInstance("list1", "list2");
            FragmentManager managerlist = getSupportFragmentManager();
            managerlist.beginTransaction().replace(
                    R.id.relative_for_fragment,
                    mylistfragment,
                    mylistfragment.getTag()
            ).commit();


        } else if (id == R.id.nav_settings) {

            Intent settings = new Intent(Menu_Principal.this, SettingsActivity.class);
            settings.putExtra("mail",user_email);
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


    private boolean mayRequestStoragePermission() {

        return true;
    }
    private void showOptions(boolean clicked){
        final CharSequence[] options = {"Tomar foto", "Elegir de galería", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal.this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (options[i] == "Tomar foto") {

                        if (ContextCompat.checkSelfPermission(Menu_Principal.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Menu_Principal.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(Menu_Principal.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                        } else {
                            startActivityForResult(cameraPhoto.takePhotoIntent(), PHOTO_CODE);
                        }
                    } else if (options[i] == "Elegir de galería") {
                       if(ContextCompat.checkSelfPermission(Menu_Principal.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                           ActivityCompat.requestPermissions(Menu_Principal.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 120);
                        }else{
                           startActivityForResult(galleryPhoto.openGalleryIntent(), SELECT_PICTURE);
                       }

                    } else {
                        dialogInterface.dismiss();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed_camera = true;
        boolean allowed_gallery = true;

        if (requestCode == 110) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), PHOTO_CODE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == 120){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), SELECT_PICTURE);
            }
        }
    }


    private void requestPermission(String option) {
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        String[] permissions2 = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        if (option.equals("Tomar foto")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, PERMS_REQUEST_CODE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions2, READ_REQUEST_CODE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_CODE) {

                entrado = true;
                selectedPhoto = cameraPhoto.getPhotoPath();

                Log.d(TAG, selectedPhoto);
                try {
                    bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();

                    if(!clicked) {

                        final  Intent pictureIntent = new Intent(Menu_Principal.this,PictureActivity.class);
                        String encodedImage = com.example.admin.prova.ImageBase64.encode(bitmap);



                        String encoded_profileImage = logData.getEncoded_profileImageBitmap();
                        String imageProfileName = logData.getProfileImageName();


                        Log.d(TAG,encodedImage);
                        String imageName = cameraPhoto.getFileName();
                        HashMap<String,String> postData = new HashMap<String, String>();

                        postData.put("image", encodedImage);
                        postData.put("username", user_name);
                        postData.put("imageName", imageName);
                        postData.put("imageProfile", encoded_profileImage);
                        postData.put("imageProfileName", imageProfileName);

                        PostResponseAsyncTask task = new PostResponseAsyncTask(Menu_Principal.this, postData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String status = jsonObject.getString("Status");
                                    if(status.equals("200")){
                                        Toast.makeText(getApplicationContext(), "Foto enviada!", Toast.LENGTH_LONG).show();
                                    }

                                    //if(status.equals("200")){
                                    //    result = s;
                                    //    Toast.makeText(getApplicationContext(), "Foto enviada!", Toast.LENGTH_LONG).show();
                                    //    pictureIntent.putExtra("image", selectedPhoto);
                                    //    pictureIntent.putExtra("resultado", result);
                                    //    startActivity(pictureIntent);
                                    //    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Foto no enviada!", Toast.LENGTH_LONG).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        task.execute("http://192.168.1.47/REST/file_upload.php");
                        task.setEachExceptionsHandler(new EachExceptionsHandler() {
                            @Override
                            public void handleIOException(IOException e) {

                                Toast.makeText(getApplicationContext(), "No se ha podido conectar al servidor", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleMalformedURLException(MalformedURLException e) {
                                Toast.makeText(getApplicationContext(), "Error en la url", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleProtocolException(ProtocolException e) {

                                Toast.makeText(getApplicationContext(), "Error del protocolo", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

                                Toast.makeText(getApplicationContext(), "Error en la codificacion de la imagen", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        bitmap = ImageLoader.init().from(selectedPhoto).getBitmap();
                        String encoding = com.example.admin.prova.ImageBase64.encode(bitmap);
                        logData.setEncoded_profileImageBitmap(encoding);
                        File file = new File(selectedPhoto);
                        logData.setProfileImageName(file.getName());
                        Picasso
                                .with(Menu_Principal.this)
                                .load(selectedPhoto)
                                .transform(new CircleTransform())
                                .into(bussinesImageView);

                        clicked = false;
                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error mientras se cargaba la foto.", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_PICTURE) {

                entrado = true;
                File file = null;
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                Bitmap bitmap;

                Log.d(TAG, photoPath);

                try {
                    bitmap  = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();

                    if(!clicked) {
                        final Intent pictureIntent = new Intent(Menu_Principal.this,PictureActivity.class);
                        String encodedImage = com.example.admin.prova.ImageBase64.encode(bitmap);
                        Log.d(TAG,encodedImage);

                        String encoded_profileImage = logData.getEncoded_profileImageBitmap();
                        String imageProfileName = logData.getProfileImageName();

                        String path = galleryPhoto.getPath();

                        file = new File(path);

                        String imageName = file.getName();

                        HashMap<String,String> postData = new HashMap<String, String>();
                        postData.put("image",encodedImage);
                        postData.put("username",user_name);
                        postData.put("imageName",imageName);
                        postData.put("imageProfile", encoded_profileImage);
                        postData.put("imageProfileName", imageProfileName);

                        PostResponseAsyncTask task = new PostResponseAsyncTask(Menu_Principal.this, postData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String status = jsonObject.getString("Status");

                                    if(status.equals("200")){
                                        result = s;
                                        Toast.makeText(getApplicationContext(), "Foto enviada!", Toast.LENGTH_LONG).show();
                                        pictureIntent.putExtra("image", selectedPhoto);
                                        pictureIntent.putExtra("resultado", result);
                                        startActivity(pictureIntent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Foto no enviada!", Toast.LENGTH_LONG).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        task.execute("http://192.168.1.47/REST/file_upload.php");
                        task.setEachExceptionsHandler(new EachExceptionsHandler() {
                            @Override
                            public void handleIOException(IOException e) {

                                Toast.makeText(getApplicationContext(), "No se ha podido conectar al servidor", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleMalformedURLException(MalformedURLException e) {
                                Toast.makeText(getApplicationContext(), "Error en la url", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleProtocolException(ProtocolException e) {

                                Toast.makeText(getApplicationContext(), "Error del protocolo", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

                                Toast.makeText(getApplicationContext(), "Error en la codificacion de la imagen", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        File file_profile = new File(photoPath);
                        bitmap = ImageLoader.init().from(photoPath).getBitmap();
                        String encoding = com.example.admin.prova.ImageBase64.encode(bitmap);
                        logData.setEncoded_profileImageBitmap(encoding);
                        logData.setProfileImageName(file_profile.getName());
                        Picasso
                                .with(Menu_Principal.this)
                                .load(uri)
                                .transform(new CircleTransform())
                                .into(bussinesImageView);
                        clicked = false;
                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error mientras se escogía la foto.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
