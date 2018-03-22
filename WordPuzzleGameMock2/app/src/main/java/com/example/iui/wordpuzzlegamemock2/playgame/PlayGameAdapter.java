package com.example.iui.wordpuzzlegamemock2.playgame;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;

import java.util.ArrayList;

/**
 * Created by CNTT on 3/2/2018.
 */

public class PlayGameAdapter extends ArrayAdapter<Play> {
    @NonNull
    private Activity context;
    private int resource;
    private ArrayList<Play> objects;

    public PlayGameAdapter(@NonNull Activity context, int resource, ArrayList<Play> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        ImageView img=row.findViewById(R.id.imgPlay);
        TextView txt=row.findViewById(R.id.txtPlay);
        Play play=this.objects.get(position);
        img.setImageResource(play.getImg());
        txt.setText(play.getLocate());
        if(play.getLocate().equals("")){
            row.setVisibility(View.GONE);
        }
        return row;
    }
}
