package com.example.iui.wordpuzzlegamemock2.selectgame;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;

import java.util.List;

/**
 * Created by Admin on 28/02/2018.
 */

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<String> lstSize;

    public CustomSpinnerAdapter(Context context, List<String> lstSize) {
        this.context = context;
        this.lstSize = lstSize;
    }

    @Override
    public int getCount() {
        return lstSize.size();
    }

    @Override
    public Object getItem(int i) {
        return lstSize.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(lstSize.get(position));
        txt.setTextColor(context.getResources().getColor(android.R.color.black));
        return txt;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_spinner_drop_down, 0);
        txt.setText(lstSize.get(position));
        txt.setTextColor(context.getResources().getColor(android.R.color.black));
        return txt;
    }
}
