package com.example.admin.prova;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private TextView textViewSearch;
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
        textViewSearch = (TextView) findViewById(R.id.labelSearch);
        textViewSearch.setText("Search on Internet");
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

        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

                String term = firstLabel + " "+ secondLabel + " " + logoView;
                intent.putExtra(SearchManager.QUERY,term);
                startActivity(intent);


            }
        });
    }

}
