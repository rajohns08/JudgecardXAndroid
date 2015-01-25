package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rajohns.judgecardx.Model.Fight;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.Model.Scorecard;
import com.rajohns.judgecardx.R;

import java.util.ArrayList;

/**
 * Created by rajohns on 1/7/15.
 *
 */
public class ScorecardAdapter extends ArrayAdapter<Round> {
    Context context;
    int layoutResourceId;
    Scorecard scorecard;

    public ScorecardAdapter(Context context, int layoutResourceId, Scorecard scorecard) {
        super(context, layoutResourceId, scorecard.getScorecard());
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.scorecard = scorecard;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Button leftScoreButton = (Button)convertView.findViewById(R.id.leftFighterScore);
        TextView roundNumber = (TextView)convertView.findViewById(R.id.roundNumber);
        Button rightScoreButton = (Button)convertView.findViewById(R.id.rightFighterScore);

        Round round = scorecard.getScorecard().get(position);

        leftScoreButton.setText(round.getLeftScore());
        int roundNum = position+1;
        roundNumber.setText("" + roundNum);
        rightScoreButton.setText(round.getRightScore());

        return convertView;
    }
}
