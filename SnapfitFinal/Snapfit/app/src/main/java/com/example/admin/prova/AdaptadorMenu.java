package com.example.admin.prova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class  AdaptadorMenu extends ArrayAdapter
    {
        private Context context;
        private ArrayList<ImagenHome> url_images;


        public AdaptadorMenu(Context context, ArrayList<ImagenHome> url_images)
        {
            super(context, R.layout.imageview,  url_images);
            this.context = context;
            this.url_images = url_images;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
           // LayoutInflater inflater = context.getLayoutInflater();

            View item = LayoutInflater.from(context).inflate(R.layout.imageview, null);

            ImageView igridView = (ImageView) item.findViewById(R.id.gridviewImage);
            ImageView igridViewPerfil = (ImageView) item.findViewById(R.id.user_profileGrid);
            TextView textViewuser = (TextView) item.findViewById(R.id.username_Grid);


            textViewuser.setText(url_images.get(pos).getUsername()) ;

            Picasso
                    .with(context)
                    .load(url_images.get(pos).getUrl_perfil())
                    .resize(512,512)
                    .transform(new CircleTransform())
                    .into(igridViewPerfil);


            Picasso
                    .with(context)
                    .load(url_images.get(pos).getUrl_imagen())
                    .resize(512,512)
                    .into(igridView);

            return item;
        }
    }
