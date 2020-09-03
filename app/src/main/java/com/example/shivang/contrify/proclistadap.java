package com.example.shivang.contrify;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class proclistadap extends ArrayAdapter<fieldsinfo> {
    Context cnt;
    ArrayList<fieldsinfo> arf;
    public proclistadap(Context context, ArrayList<fieldsinfo> users) {
        super(context, 0, users);
        arf=users;
        cnt= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        fieldsinfo user = getProduct(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.proclist, parent, false);
        }
        // Lookup view for data population
        TextView flname =  convertView.findViewById(R.id.filenme);
        // Populate the data into the template view using the data object
        flname.setText(user.name);
        user.pos=position;
        // Return the completed view to render on screen
        return convertView;
    }
    @Override
    public int getCount() {
        return arf.size();
    }

    @Override
    public fieldsinfo getItem(int position) {
        return arf.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    fieldsinfo getProduct(int position) {
        return (getItem(position));
    }

}
