package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import com.rajohns.judgecardx.Utils.NotifyHelper;


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
    private static final int TYPE_REGULAR_ROW = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int NUM_TYPE_ROWS = 2;

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
    public int getItemViewType(int position) {
        if (position == getCount()-1) {
            return TYPE_FOOTER;
        }

        return TYPE_REGULAR_ROW;
    }

    @Override
    public int getViewTypeCount() {
        return NUM_TYPE_ROWS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_REGULAR_ROW:
                    convertView = inflater.inflate(R.layout.row_scorecard, parent, false);
                    break;
                case TYPE_FOOTER:
                    convertView = inflater.inflate(R.layout.row_scorecard_footer, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_REGULAR_ROW:
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
                break;
            case TYPE_FOOTER:
                final Button deleteButton = (Button)convertView.findViewById(R.id.deleteButton);
                Button resetButton = (Button)convertView.findViewById(R.id.resetButton);

                if (scorecard.getId().equals("null")) {
                    deleteButton.setEnabled(false);
                    resetButton.setEnabled(false);
                }
                else {
                    deleteButton.setEnabled(true);
                    resetButton.setEnabled(true);
                }

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NotifyHelper.showConfirmAlert(context, "Delete Scorecard", "Are you sure you want to delete this scorecard?", "Delete", new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                ((ScorecardActivity)context).deleteScorecard(scorecard);
                                return false;
                            }
                        });
                    }
                });
                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NotifyHelper.showConfirmAlert(context, "Reset Scorecard", "Are you sure you want to reset this scorecard to all 0's?", "Reset", new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                ((ScorecardActivity)context).resetScorecard(scorecard);
                                return false;
                            }
                        });
                    }
                });

                if (fragmentSource == FightListFragment.RECENT_CARDS_INDEX) {
                    deleteButton.setVisibility(View.GONE);
                    resetButton.setVisibility(View.GONE);
                }
                break;
        }

        return convertView;
    }
}
