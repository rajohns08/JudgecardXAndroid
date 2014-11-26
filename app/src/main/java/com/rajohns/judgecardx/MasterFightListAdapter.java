package com.rajohns.judgecardx;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rajohns on 11/23/14.
 *
 */
public class MasterFightListAdapter extends ArrayAdapter<Fight> {
    Context context;
    int layoutResourceId;
    ArrayList<Fight> fights;
    Time today;

    public MasterFightListAdapter(Context context, int layoutResourceId, ArrayList<Fight> fights) {
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
        TextView dateTV = (TextView)convertView.findViewById(R.id.dateTV);
        TextView todayTV = (TextView)convertView.findViewById(R.id.todayTV);

        Fight fight = fights.get(position);
        fightersTV.setText(fight.fighter1 + " - " + fight.fighter2);
        dateTV.setText(fight.fightDate);

        String todayString = today.month+1 + "-" + today.monthDay + "-" + today.year;

        if (todayString.equals(fight.fightDate)) {
            todayTV.setText(context.getResources().getString(R.string.today_label));
        }

        return convertView;
    }
}
