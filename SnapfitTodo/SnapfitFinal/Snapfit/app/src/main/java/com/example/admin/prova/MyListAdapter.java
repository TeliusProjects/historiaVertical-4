package com.example.admin.prova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Arnau on 4/5/17.
 */

public class MyListAdapter extends ArrayAdapter {

    private Context context;
    private File[] url_images;


    public MyListAdapter(Context context, File [] url_images)
    {
        super(context, R.layout.mylist_item_adapter, url_images);

        this.context = context;
        this.url_images = url_images;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        View item = LayoutInflater.from(context).inflate(R.layout.mylist_item_adapter, null);

        ImageView imageViewMyList = (ImageView) item.findViewById(R.id.imageViewMyList);




        Picasso
                .with(context)
                .load(url_images[pos])
                .resize(512,512)
                .fit()
                .into(imageViewMyList);

        return item;
    }
}
