package com.example.manu.radiov2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manu.radiov2.Classes.Program.Item;
import com.example.manu.radiov2.Activitys.MainActivity;
import com.example.manu.radiov2.Activitys.PopActivity;
import com.example.manu.radiov2.R;

import java.util.ArrayList;

public class DrawerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;

    public DrawerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    static class ViewHolder{
        TextView TitleView;
    }




    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ProgramAdapter.ViewHolder viewHolder = new ProgramAdapter.ViewHolder();

        /*viewHolder.TitleView = (TextView) convertView.findViewById(R.id.DrawerMenuText);

        viewHolder.TitleView.setText(title);

        viewHolder.TitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext,PopActivity.class));
            }
        });*/

        return convertView;
    }
}
