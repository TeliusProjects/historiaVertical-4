package com.example.admin.prova;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupWindow;

import java.io.FileInputStream;

/**
 * Created by admin on 19/04/2017.
 */
public class PopWindow extends Activity {

    private ImageView popUpImage;
    private TextView colorImageView;
    private Bitmap bitmaprotate = null;
    private TextView searchBtn = null;
    private TextView logoImageView;
    private TextView etiquetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        popUpImage = (ImageView) findViewById(R.id.PopUpImage);
        colorImageView = (TextView) findViewById(R.id.ColorPopUp);
        logoImageView = (TextView) findViewById(R.id.LogoPopUp);
        etiquetas = (TextView) findViewById(R.id.EtiquetasPopUp);
        searchBtn      = (TextView) findViewById(R.id.searchBtn);

        Intent popIntent = getIntent();
        final String color = popIntent.getStringExtra("color");
        final String logoName = popIntent.getStringExtra("logo");
        final String firstLabel = popIntent.getStringExtra("first");
        final String secondLabel = popIntent.getStringExtra("second");
        String filename = popIntent.getStringExtra("image");
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));



        try{
            FileInputStream is = this.openFileInput(filename);
            bitmaprotate = BitmapFactory.decodeStream(is);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       popUpImage.setImageBitmap(bitmaprotate);
       colorImageView.setText("Color de la prenda: " + color);
       logoImageView.setText("Logo de la prenda: " + logoName);
       etiquetas.setText("Etiquetas: " + firstLabel + "," + " " + secondLabel);

       searchBtn.setText("Click aquí para hacer una búsqueda en la web.");

       searchBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String web_search = firstLabel + " " + secondLabel + " " + color + " " + logoName;
               Intent webSearchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
               webSearchIntent.putExtra(SearchManager.QUERY, web_search);

               startActivity(webSearchIntent);
           }
       });
    }
}
