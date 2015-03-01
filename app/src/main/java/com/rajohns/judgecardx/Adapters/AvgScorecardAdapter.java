package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
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
    int confidenceBarMaxWidth;

    // Kind of trial and error magic number for how wide a
    // confidence bar should be relative to total screen
    // width. Dividing by 2 would mean the confidence bar
    // would be half the total screen width but after accounting
    // for margins, etc it adds another 0.3 or so.
    double confidenceBarDivisor = 2.3;

    public AvgScorecardAdapter(Context context, AvgScorecard avgScorecard, int rounds) {
        this.context = context;
        this.avgScorecard = avgScorecard;
        this.rounds = rounds;
        this.inflater = ((Activity) context).getLayoutInflater();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        this.confidenceBarMaxWidth = (int)((double)display.getWidth()/confidenceBarDivisor);
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
        View leftConfidenceBar = convertView.findViewById(R.id.leftConfidenceBar);
        View rightConfidenceBar = convertView.findViewById(R.id.rightConfidenceBar);

        AvgRound avgRound = (AvgRound)getItem(position);

        leftScore.setText(avgRound.getLeftScore());
        int roundNum = position+1;
        roundNumber.setText("" + roundNum);
        rightScore.setText(avgRound.getRightScore());

        int leftScoreInt = Integer.parseInt(avgRound.getLeftScore());
        int rightScoreInt = Integer.parseInt(avgRound.getRightScore());

        if (leftScoreInt > rightScoreInt) {
            leftScore.setTextColor(convertView.getResources().getColor(R.color.green));
            rightScore.setTextColor(convertView.getResources().getColor(R.color.red));
        }
        else if (rightScoreInt > leftScoreInt) {
            leftScore.setTextColor(convertView.getResources().getColor(R.color.red));
            rightScore.setTextColor(convertView.getResources().getColor(R.color.green));
        }
        else {
            leftScore.setTextColor(convertView.getResources().getColor(R.color.black));
            rightScore.setTextColor(convertView.getResources().getColor(R.color.black));
        }

        double leftMultiplier = avgRound.getLeftConfidence();
        double rightMultiplier = avgRound.getRightConfidence();

        int newLeftWidth = (int)(confidenceBarMaxWidth * leftMultiplier);
        int newRightWidth = (int)(confidenceBarMaxWidth * rightMultiplier);

        leftConfidenceBar.getLayoutParams().width = newLeftWidth;
        rightConfidenceBar.getLayoutParams().width = newRightWidth;

        return convertView;
    }
}
