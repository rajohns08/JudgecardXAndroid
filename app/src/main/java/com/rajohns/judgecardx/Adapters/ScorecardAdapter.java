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

import com.rajohns.judgecardx.Fragments.FightListFragment;
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
    int fragmentSource;

    public ScorecardAdapter(Context context, int layoutResourceId, Scorecard scorecard, int fragmentSource) {
        super(context, layoutResourceId, scorecard.getScorecard());
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.scorecard = scorecard;
        this.fragmentSource = fragmentSource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Button leftScoreButton = (Button)convertView.findViewById(R.id.leftFighterScore);
        TextView roundNumber = (TextView)convertView.findViewById(R.id.roundNumber);
        Button rightScoreButton = (Button)convertView.findViewById(R.id.rightFighterScore);

        final Round round = scorecard.getScorecard().get(position);

        leftScoreButton.setText(round.getLeftScore());
        int roundNum = position+1;
        roundNumber.setText("" + roundNum);
        rightScoreButton.setText(round.getRightScore());

        if (fragmentSource == FightListFragment.RECENT_CARDS_INDEX) {
            leftScoreButton.setEnabled(false);
            rightScoreButton.setEnabled(false);
        }
        else {
            leftScoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    round.updateLeftScore();
                    notifyDataSetChanged();
                }
            });
            rightScoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    round.updateRightScore();
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
