package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 1/5/15.
 *
 */
public class ScorecardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        Intent intent = getIntent();
        if (intent != null) {
            int fragmentSource = intent.getIntExtra(TabsPagerAdapter.REST_CALL_KEY, 0);
            setTitle(actionBarTitle(fragmentSource));
        }
    }

    private String actionBarTitle(int fragmentSource) {
        switch (fragmentSource) {

            // Intentional Fall-through
            case 0:
            case 2:
                return "My Card";
            case 3:
                return "otherusers card";
            default:
                return "My Card";
        }
    }
}
