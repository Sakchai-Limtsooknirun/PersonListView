package com.example.sakchai.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sakchai on 10/27/2017 AD.
 */

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, null);
            Person p = getItem(position);
            Log.i("debug", "debug"+p.getName() + position);

            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText("Name : " + p.getName());

            TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
            textView2.setText("Lastname : " + p.getLastName());

            TextView textView3 = (TextView) convertView.findViewById(R.id.textView3);
            textView3.setText("Nickname : " + p.getNickname());


        }
        return convertView;
    }
}
