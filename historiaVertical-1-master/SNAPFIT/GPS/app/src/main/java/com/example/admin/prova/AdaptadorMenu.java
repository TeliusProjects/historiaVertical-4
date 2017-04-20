package com.example.admin.prova;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorMenu extends ArrayAdapter
    {
        private Menu_Principal context;
        private ArrayList<String> url_images;
        private String[] url;


        public AdaptadorMenu(Menu_Principal context, ArrayList<String> url_images)
        {
            super(context, R.layout.imageview,  url_images);
            this.context = context;
            this.url_images = url_images;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            url = new String[url_images.size()];
            LayoutInflater inflater = context.getLayoutInflater();

            View item = inflater.inflate(R.layout.imageview, null);

            ImageView igridView = (ImageView) item.findViewById(R.id.gridviewImage);

            for(int i=0; i<url_images.size();i++){
                url[i] = url_images.get(i);
            }

            Picasso
                    .with(context)
                    .load(url[pos])
                    .fit()
                    .into(igridView);

            return item;
        }
    }
