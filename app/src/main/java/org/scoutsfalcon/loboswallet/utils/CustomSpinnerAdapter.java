package org.scoutsfalcon.loboswallet.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.scoutsfalcon.loboswallet.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomSpinnerAdapter extends ArrayAdapter<Estacion> {
    private ArrayList<Estacion> data;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, int Resource, ArrayList<Estacion> objects) {
        super(context, Resource, objects);
        data = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        /***** Get each Model object from Arraylist ********/
        Estacion values = (Estacion) data.get(position);

        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        TextView title = (TextView) row.findViewById(R.id.title);
        TextView subTitle = (TextView) row.findViewById(R.id.subtitle);

        if (values.getTipo()) {
            icon.setImageResource(R.drawable.ic_action_account_balance);
        } else {
            icon.setImageResource(R.drawable.ic_action_shopping_cart);
        }

        title.setText(values.getNombre());
        subTitle.setText(String.format("Máximo %d", values.getMaximo()));

        return row;
    }
}
