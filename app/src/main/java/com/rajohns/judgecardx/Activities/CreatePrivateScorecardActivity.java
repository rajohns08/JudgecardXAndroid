package com.rajohns.judgecardx.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;

import javax.inject.Inject;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by rajohns on 3/9/15.
 *
 */
public class CreatePrivateScorecardActivity extends BaseActivity {
    @Inject RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private);

        SegmentedGroup roundSegment = (SegmentedGroup)findViewById(R.id.numRoundsPicker);
        roundSegment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rounds3:
                        int x = 1;
                        Log.d("tag", "hfdaklf;djafk dal;fjdakl");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
