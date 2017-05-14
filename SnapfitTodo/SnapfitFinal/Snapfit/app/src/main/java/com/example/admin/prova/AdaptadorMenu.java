package com.example.admin.prova;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class  AdaptadorMenu extends ArrayAdapter
    {
        private Context context;
        private ArrayList<String> url_images;
        private String[] url;


        public AdaptadorMenu(Context context, ArrayList<String> url_images)
        {
            super(context, R.layout.imageview,  url_images);
            this.context = context;
            this.url_images = url_images;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            url = new String[url_images.size()];
           // LayoutInflater inflater = context.getLayoutInflater();

            View item = LayoutInflater.from(context).inflate(R.layout.imageview, null);

            ImageView igridView = (ImageView) item.findViewById(R.id.gridviewImage);

            for(int i=0; i<url_images.size();i++){
                url[i] = url_images.get(i);
            }

            Picasso
                    .with(context)
                    .load(url[pos])
                    .resize(512,512)
                    .into(igridView);

            return item;
        }
    }
