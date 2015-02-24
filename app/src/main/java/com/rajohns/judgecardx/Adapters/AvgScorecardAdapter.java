package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rajohns.judgecardx.Model.AvgRound;
import com.rajohns.judgecardx.Model.AvgScorecard;
import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 2/20/15.
 *
 */
public class AvgScorecardAdapter extends BaseAdapter {
    Context context;
    int rounds;
    LayoutInflater inflater;
    AvgScorecard avgScorecard;

    public AvgScorecardAdapter(Context context, AvgScorecard avgScorecard, int rounds) {
        this.context = context;
        this.avgScorecard = avgScorecard;
        this.rounds = rounds;
        this.inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public Object getItem(int position) {
        return avgScorecard.getAvgScorecard().get(position);
    }

    @Override
    public int getCount() {
        return rounds;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_avg_scorecard, parent, false);
        }

        TextView leftScore = (TextView)convertView.findViewById(R.id.leftFighterScore);
        TextView roundNumber = (TextView)convertView.findViewById(R.id.roundNumber);
        TextView rightScore = (TextView)convertView.findViewById(R.id.rightFighterScore);

        AvgRound avgRound = (AvgRound)getItem(position);

        leftScore.setText(avgRound.getLeftScore());
        int roundNum = position+1;
        roundNumber.setText("" + roundNum);
        rightScore.setText(avgRound.getRightScore());

        return convertView;
    }
}
