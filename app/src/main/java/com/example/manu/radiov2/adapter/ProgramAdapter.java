package com.example.manu.radiov2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Item;
import com.example.manu.radiov2.R;

import java.util.ArrayList;


public class ProgramAdapter extends ArrayAdapter <Item>{

    private Context mContext;
    private int mResource;

    static class ViewHolder{
        TextView TitleView;
        TextView DescripView;
    }

    public ProgramAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.TitleView = (TextView) convertView.findViewById(R.id.adapter_title);
        viewHolder.DescripView = (TextView) convertView.findViewById(R.id.adapter_description);

        viewHolder.TitleView.setText(title);
        viewHolder.DescripView.setText(description);

        return convertView;
    }
}
