package com.example.admin.prova;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;

public class PictureActivity extends AppCompatActivity {

    private ImageView pictureImageActivity;
    private TextView pictureColorView;
    private TextView pictureLogoView;
    private TextView pictureLabelsView;
    private String photoPath = null;
    private Bitmap bitmaprotate = null;
    private String result = "";
    private  String colorView;
    private String logoView;
    private String firstLabel;
    private String secondLabel;
    //private Bitmap bitmapOrientated = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        pictureImageActivity = (ImageView) findViewById(R.id.pictureImage);
        TextView pictureColorView = (TextView) findViewById(R.id.pictureColor);
        TextView pictureLogoView = (TextView) findViewById(R.id.pictureLogo);
        TextView pictureLabelsView = (TextView) findViewById(R.id.pictureLabels);
        Intent pictureIntent = getIntent();

        photoPath = pictureIntent.getStringExtra("image");
        result    = pictureIntent.getStringExtra("resultado");
        try {

            JSONObject jsonObject = new JSONObject(result);
            colorView = jsonObject.getString("Color");
            logoView = jsonObject.getString("Logo");
            firstLabel = jsonObject.getString("First label");
            secondLabel = jsonObject.getString("Second label");

            bitmaprotate = ImageLoader.init().from(photoPath).getBitmap();

            pictureImageActivity.setImageBitmap(bitmaprotate);
            pictureColorView.setText("Color: " + colorView);
            pictureLogoView.setText("Logo: " + logoView);
            pictureLabelsView.setText("Tags: " + firstLabel + ", " + secondLabel);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //Toast.makeText(this, "Resultado " + result,Toast.LENGTH_SHORT ).show();

    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
    public int getImageOrientation() {
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

}
