package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rajohns.judgecardx.Adapters.ScorecardAdapter;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.Model.Scorecard;
import com.rajohns.judgecardx.R;

import java.util.ArrayList;

/**
 * Created by rajohns on 1/5/15.
 *
 */
public class ScorecardActivity extends BaseActivity {
    String subtext;
    String leftFighter;
    String rightFighter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        Intent intent = getIntent();
        if (intent != null) {
            int fragmentSource = intent.getIntExtra(TabsPagerAdapter.REST_CALL_KEY, 0);
            subtext = intent.getStringExtra(FightListFragment.SUBTEXT);
            leftFighter = intent.getStringExtra(FightListFragment.LEFT_FIGHTER);
            rightFighter = intent.getStringExtra(FightListFragment.RIGHT_FIGHTER);
            setTitle(actionBarTitle(fragmentSource));
            TextView leftFighterTV = (TextView)findViewById(R.id.leftFighter);
            TextView rightFighterTV = (TextView)findViewById(R.id.rightFighter);
            leftFighterTV.setText(leftFighter);
            rightFighterTV.setText(rightFighter);
        }

        ListView listView = (ListView)findViewById(R.id.scorecardList);
        Round r1 = new Round(10, 1, 9);
        Round r2 = new Round(10, 2, 9);
        Round r3 = new Round(10, 3, 9);
        Round r4 = new Round(10, 1, 9);
        Round r5 = new Round(10, 2, 9);
        Round r6 = new Round(10, 3, 9);
        Round r7 = new Round(10, 1, 9);
        Round r8 = new Round(10, 2, 9);
        Round r9 = new Round(10, 3, 9);
        ArrayList<Round> s = new ArrayList<>();
        s.add(r1);
        s.add(r2);
        s.add(r3);
        s.add(r4);
        s.add(r5);
        s.add(r6);
        s.add(r7);
        s.add(r8);
        s.add(r9);
        Scorecard scorecard = new Scorecard(s);
        ScorecardAdapter adapter = new ScorecardAdapter(this, R.layout.row_scorecard, scorecard);
        listView.setAdapter(adapter);
    }

    private String actionBarTitle(int fragmentSource) {
        switch (fragmentSource) {
            // Intentional Fall-through
            case FightListFragment.MASTER_FIGHT_LIST_INDEX:
            case FightListFragment.MY_CARDS_INDEX:
                return "My Card";
            case FightListFragment.RECENT_CARDS_INDEX:
                return getJustUsernameFromSubtext(subtext) + "'s Card";
            default:
                return "My Card";
        }
    }

    private String getJustUsernameFromSubtext(String subtext) {
        return subtext.replace("User: ", "");
    }
}
