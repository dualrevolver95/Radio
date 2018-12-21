package com.example.manu.radiov2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manu.radiov2.Classes.Program.Item;
import com.example.manu.radiov2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProgramAdapter extends ArrayAdapter <Integer>{

    private Context mContext;
    private int mResource;

    static class ViewHolder{
        ImageView img;
    }

    public ProgramAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Integer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    private void LoadImage(int imageurl, ImageView img) {
        Picasso.with(mContext).load(imageurl).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int title = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.img = (ImageView) convertView.findViewById(R.id.PIMG);

        //LoadImage(title,viewHolder.img);

        Picasso.with(mContext).load(title).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.img);

        return convertView;
    }
}
