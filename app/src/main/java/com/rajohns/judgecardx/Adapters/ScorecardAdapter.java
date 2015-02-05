package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rajohns.judgecardx.Activities.ScorecardActivity;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.Model.Scorecard;
import com.rajohns.judgecardx.R;


/**
 * Created by rajohns on 1/7/15.
 *
 */
public class ScorecardAdapter extends BaseAdapter {
    Context context;
    Scorecard scorecard;
    int fragmentSource;
    int rounds;
    private LayoutInflater inflater;

    public ScorecardAdapter(Context context, Scorecard scorecard, int fragmentSource, int rounds) {
        this.context = context;
        this.scorecard = scorecard;
        this.fragmentSource = fragmentSource;
        this.rounds = rounds;
        this.inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        // +1 for the footer view
        return rounds+1;
    }

    @Override
    public Object getItem(int position) {
        return scorecard.getScorecard().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // committing a great sin here by not reusing convertview (if convertview == null), but listviews small and
        // it makes handling my different types of rows (regular or footer) simple
        if (position == rounds) {
            convertView = inflater.inflate(R.layout.row_scorecard_footer, parent, false);
        }
        else {
            convertView = inflater.inflate(R.layout.row_scorecard, parent, false);

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
                        ((ScorecardActivity)context).updateTotalScores(scorecard);
                        ((ScorecardActivity)context).createOrUpdateScorecard(scorecard);
                    }
                });
                rightScoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        round.updateRightScore();
                        notifyDataSetChanged();
                        ((ScorecardActivity)context).updateTotalScores(scorecard);
                        ((ScorecardActivity)context).createOrUpdateScorecard(scorecard);
                    }
                });
            }
        }

        return convertView;
    }
}
