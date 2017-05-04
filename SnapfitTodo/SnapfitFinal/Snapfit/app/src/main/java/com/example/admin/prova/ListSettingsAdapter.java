package com.example.admin.prova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arnau on 21/4/17.
 */

public class ListSettingsAdapter extends ArrayAdapter {

    private Context context;
    private String [] datos;

    public ListSettingsAdapter(Context context, String [] datos)
    {
        super(context,R.layout.list_adapter_item,datos);
        this.context = context;
        this.datos = datos;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_adapter_item, null);


        //ImageView imagen = (ImageView) item.findViewById(R.id.imgItem);



        TextView txtItem = (TextView) item.findViewById(R.id.txtViewItem);

        txtItem.setText(datos[position]);


        return item;
    }
}
