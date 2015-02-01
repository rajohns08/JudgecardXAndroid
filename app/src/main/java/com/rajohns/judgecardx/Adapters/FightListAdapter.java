package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rajohns.judgecardx.Model.Fight;
import com.rajohns.judgecardx.R;

import java.util.ArrayList;

/**
 * Created by rajohns on 11/23/14.
 *
 */
public class FightListAdapter extends ArrayAdapter<Fight> {
    Context context;
    int layoutResourceId;
    ArrayList<Fight> fights;
    Time today;

    public FightListAdapter(Context context, int layoutResourceId, ArrayList<Fight> fights) {
        super(context, layoutResourceId, fights);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.fights = fights;

        this.today = new Time(Time.getCurrentTimezone());
        today.setToNow();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView fightersTV = (TextView)convertView.findViewById(R.id.fightersTV);
        TextView subtextTV = (TextView)convertView.findViewById(R.id.subtextTV);
        TextView todayTV = (TextView)convertView.findViewById(R.id.todayTV);

        Fight fight = fights.get(position);
        fightersTV.setText(fight.getFighter1() + " - " + fight.getFighter2());
        subtextTV.setText(fight.getSubtext());

        String todayString = today.month+1 + "-" + today.monthDay + "-" + today.year;
        if (today.month+1 < 10) {
            // Insert a 0 so month will be 01 for january instead of just 1
            todayString = new StringBuffer(todayString).insert(0, "0").toString();
        }

        if (todayString.equals(fight.getSubtext())) {
            todayTV.setText(context.getResources().getString(R.string.today_label));
        }

        return convertView;
    }
}
