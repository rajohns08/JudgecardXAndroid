package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 1/5/15.
 *
 */
public class ScorecardActivity extends BaseActivity {
    String subtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        Intent intent = getIntent();
        if (intent != null) {
            int fragmentSource = intent.getIntExtra(TabsPagerAdapter.REST_CALL_KEY, 0);
            subtext = intent.getStringExtra(FightListFragment.SUBTEXT);
            setTitle(actionBarTitle(fragmentSource));
        }
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
