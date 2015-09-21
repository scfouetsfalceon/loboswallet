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

public class CustomListAdapter extends ArrayAdapter<Joven> {
    ArrayList<Joven> data;
    LayoutInflater inflater;

    public CustomListAdapter(Context context, ArrayList<Joven> objects) {
        super(context, -1, objects);
        this.data = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Joven current_joven = data.get(position);

        int image_resource = 0;
        if (current_joven.getCode() != "") {
            if (current_joven.getSex()) {
                // Varón
            } else {
                // Hembra
            }
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);

            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imagePerson);
            holder.data = (TextView) convertView.findViewById(R.id.textData);
            holder.account = (TextView) convertView.findViewById(R.id.textAccount);

            if (current_joven.getCode() == "") {
                holder.img.setVisibility(View.INVISIBLE);
                holder.account.setVisibility(View.INVISIBLE);
            } else {
                holder.img.setVisibility(View.VISIBLE);
                holder.account.setVisibility(View.VISIBLE);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            if (current_joven.getCode() == "") {
                holder.img.setVisibility(View.INVISIBLE);
                holder.account.setVisibility(View.INVISIBLE);
            } else {
                holder.img.setVisibility(View.VISIBLE);
                holder.account.setVisibility(View.VISIBLE);
            }
        }

        holder.data.setText(current_joven.getData());
        holder.account.setText(String.format("Saldo: %d £", current_joven.getAccount()));
        return convertView;
    }

    private static class ViewHolder {
        public ImageView img;
        public TextView data;
        public TextView account;

    }
}
