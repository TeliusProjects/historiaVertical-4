package com.example.admin.prova;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Menu_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private static final int PERMS_REQUEST_CODE = 123;
    private static final int READ_REQUEST_CODE = 122;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    FloatingActionButton fabcamera;
    private ImageView mSetImage;
   //s private Button mOptionButtons;


    private ConnectiongDetector cd;
    private Boolean upflag = false;
    private String upflag2 = null;
    private Uri selectedImage = null;
    private Bitmap bitmap, bitmapRotate;
    private ProgressDialog pDialog;
    String imagepath = "";
    String fname;
    File file;
    private String mPath;
    TextView txUsername = null;
    TextView txUserEmail = null;
    String user_name = "";
    String user_email = "";
    private static GridView gvImages = null;
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

        setContentView(R.layout.activity_menu__principal);

        cd = new ConnectiongDetector(Menu_Principal.this);



        cd = new ConnectiongDetector(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      //  Menu_Principal.FillList fillList = new Menu_Principal.FillList(homefrag.getActivity());

      //  fillList.execute();

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
        if (id == R.id.action_settings)
        {

            Intent settings = new Intent(Menu_Principal.this, SettingsActivity.class);
            settings.putExtra("username", user_name);
            startActivity(settings);
            return true;
        }

        //boton del action bar (arriba) el logout
        //if(id == R.id.action_logout) {
          //  Intent login = new Intent(Menu_Principal.this, MainActivity.class);
            //startActivity(login);
            //return true;
        //}
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

            Intent settings = new Intent(Menu_Principal.this, PasswordActivity.class);
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
    private void showOptions(){
        final CharSequence[] options = {"Tomar foto", "Elegir de galería", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal.this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i] == "Tomar foto"){
                    String camera_option = options[i].toString();
                    if(hasPermission(camera_option)){
                        openCamera();
                    }else{
                        requestPermission(camera_option);
                    }
                }else if(options[i] == "Elegir de galería"){
                    String gallery_option = options[i].toString();
                    if(hasPermission(gallery_option)){
                        openGallery();
                    }else{
                        requestPermission(gallery_option);
                    }
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
            Intent cameraintent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraintent, PHOTO_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch(requestCode){
                case PHOTO_CODE:
                    try{
                        if (data != null) {
                            selectedImage = data.getData(); // the uri of the image taken
                            if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                            } else {
                                bitmap = (Bitmap) data.getExtras().get("data");
                            }
                            if (Float.valueOf(getImageOrientation()) >= 0) {
                                bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                            } else {
                                bitmapRotate = bitmap;
                                bitmap.recycle();
                            }
//                            Saving image to mobile internal memory for sometime
                            // String root = getApplicationContext().getFilesDir().toString();
                            // File myDir = new File(root + "/androidlift");
                            // myDir.mkdirs();

                            Long timestamp = System.currentTimeMillis() / 1000;

//                            Give the file name that u want
                            fname = timestamp + ".png";

                            imagepath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + fname;
                            file = new File(imagepath);
                            upflag = true;

                        }
                        if (cd.isConnectingToInternet()) {
                            if (!upflag) {
                                Toast.makeText(Menu_Principal.this, "Image Not Captured..!", Toast.LENGTH_LONG).show();
                            } else {
                                saveFile(bitmapRotate, file);
                            }
                        } else {
                            Toast.makeText(Menu_Principal.this, "No Internet Connection !", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;
            }
        }
    }

    private void saveFile(Bitmap sourceUri, File destination) {
        if (destination.exists()) destination.delete();
        try {
            FileOutputStream out = new FileOutputStream(destination);
            sourceUri.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
            if (cd.isConnectingToInternet()) {
                new DoFileUpload(Menu_Principal.this).execute();
            } else {
                Toast.makeText(Menu_Principal.this, "No Internet Connection..", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        if (cursor.moveToFirst()) {
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            System.out.println("orientation===" + orientation);
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }
    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
    }

    private boolean hasPermission(String option){
        int res = 0;

        String [] permissions = new String[]{android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String [] permissions2 = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};

        if(option.equals("Tomar foto")) {
            for (String perms : permissions) {
                res = checkCallingOrSelfPermission(perms);

                if (!(res == PackageManager.PERMISSION_GRANTED)) {
                    return false;
                }
            }
        }else{
            for (String perms : permissions2) {
                res = checkCallingOrSelfPermission(perms);

                if (!(res == PackageManager.PERMISSION_GRANTED)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed_camera = true;
        boolean allowed_gallery = true;

        switch(requestCode){
            case PERMS_REQUEST_CODE:

                for(int res : grantResults){
                    allowed_camera = allowed_camera && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;

            case READ_REQUEST_CODE:
                for(int res : grantResults){
                    allowed_gallery = allowed_gallery && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed_camera = false;
                allowed_gallery = false;
                break;

        }

        
        if(allowed_camera){
            openCamera();

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
                        && shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Camera and storage permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if(allowed_gallery){
            openGallery();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if((shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE))){
                    Toast.makeText(this, "Storage permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void requestPermission(String option){
        String [] permissions = new String[]{android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        String [] permissions2 = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};

        if(option.equals("Tomar foto")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(permissions, PERMS_REQUEST_CODE);

            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(permissions2, READ_REQUEST_CODE);

            }
        }
    }

    class DoFileUpload extends AsyncTask<String, String, String> {

        private Activity activity;

        DoFileUpload(Activity mActivity){

            activity = mActivity;
        }
        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(Menu_Principal.this);
            pDialog.setMessage("Wait. Uploading Image..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                // Set your file path here
                FileInputStream fstrm = new FileInputStream(imagepath);
                // Set your server page url (and the file title/description)
                HttpFileUpload hfu = new HttpFileUpload("http://192.168.1.43/REST/file_upload.php", "ftitle", "fdescription", fname);
                upflag2 = hfu.Send_Now(fstrm);
            } catch (FileNotFoundException e) {
                // Error: File not found
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (!upflag2.equals(null)) {

                Toast.makeText(getApplicationContext(), "Uploading Complete", Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(upflag2);
                    String colorName = jsonObject.getString("Color");
                     String logoName = jsonObject.getString("Logo");
                     String firstLabel = jsonObject.getString("First label");
                     String secondLabel = jsonObject.getString("Second label");
                    String filename = "bitmap.png";
                    FileOutputStream stream = this.activity.openFileOutput(filename, Context.MODE_PRIVATE);

                    bitmapRotate.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.close();
                    bitmapRotate.recycle();
                    Intent popIntent = new Intent(Menu_Principal.this, PopWindow.class);

                    popIntent.putExtra("color", colorName);
                    popIntent.putExtra("logo", logoName);
                    popIntent.putExtra("first", firstLabel);
                    popIntent.putExtra("second", secondLabel);
                    popIntent.putExtra("image", filename);


                    startActivity(popIntent);



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Unfortunately file is not Uploaded..", Toast.LENGTH_LONG).show();
            }
        }
    }



}
