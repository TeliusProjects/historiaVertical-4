package com.example.admin.prova;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPhoto {

    final String TAG = this.getClass().getSimpleName();

    private static String APP_DIRECTORY = "Snapfit/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Images";

    private String photoPath;
    private String imageFileName;

    public String getPhotoPath() {
        return photoPath;
    }
    public String getFileName(){
        return imageFileName;
    }

    private Context context;
    public CameraPhoto(Context context){
        this.context = context;
    }

    public Intent takePhotoIntent() throws IOException {
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (in.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                in.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            }else{
                Toast.makeText(context, "photoFile es null.", Toast.LENGTH_LONG).show();
            }
        }
        return in;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
      /*  File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );*/

        String path = storageDir + "/" + imageFileName;

        File imageFile = new File(path);

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = path;

        return imageFile;
    }

    public void addToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
