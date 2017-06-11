package com.example.admin.prova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

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

        File fileGetExten = new File(url_images[pos].getAbsolutePath());
        if(getFileExtension(fileGetExten).equals("jpg")) {
            Picasso
                    .with(context)
                    .load(new File(url_images[pos].getPath()))
                    .resize(512, 512)
                    .into(imageViewMyList);
        }
        return item;
    }


    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
