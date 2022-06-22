package com.example.try1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RidesAdapter extends ArrayAdapter<Ride> {
    Context context;
    ArrayList<Ride> object;




    public RidesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Ride> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.object = objects;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.search_oneline,parent,false);


        TextView tv_oneLine_to = (TextView)view.findViewById(R.id.tv_oneLine_to);
        TextView tv_oneLine_date = (TextView)view.findViewById(R.id.tv_oneLine_date);
        TextView tv_oneLine_time = (TextView)view.findViewById(R.id.tv_oneLine_time);
        TextView tv_oneLine_from = (TextView)view.findViewById(R.id.tv_oneLine_from);


        Ride temp = object.get(position);
        tv_oneLine_from.setText("From "+String.valueOf(temp.getGoingFrom()));
        tv_oneLine_to.setText("To "+String.valueOf(temp.getGoingTo()));
        tv_oneLine_date.setText(String.valueOf(temp.getDay()+"/"+temp.getMonth()));
        tv_oneLine_time.setText(String.valueOf(temp.getHour()+":" + temp.getMinute()));

        return view;
    }



}
