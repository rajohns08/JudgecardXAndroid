package com.rajohns.judgecardx.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;

import javax.inject.Inject;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by rajohns on 3/9/15.
 *
 */
public class CreatePrivateScorecardActivity extends BaseActivity {
    @Inject RestClient restClient;
    private int numRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private);

        SegmentedGroup roundSegment = (SegmentedGroup)findViewById(R.id.numRoundsPicker);
        final EditText fighter1ET = (EditText)findViewById(R.id.fighter1ET);
        final EditText fighter2ET = (EditText)findViewById(R.id.fighter2ET);
        Button createButton = (Button)findViewById(R.id.createButton);

        roundSegment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rounds3:
                        numRounds = 3;
                        break;
                    case R.id.rounds4:
                        numRounds = 4;
                        break;
                    case R.id.rounds5:
                        numRounds = 5;
                        break;
                    case R.id.rounds6:
                        numRounds = 6;
                        break;
                    case R.id.rounds8:
                        numRounds = 8;
                        break;
                    case R.id.rounds10:
                        numRounds = 10;
                        break;
                    case R.id.rounds12:
                        numRounds = 12;
                        break;
                    case R.id.rounds15:
                        numRounds = 15;
                        break;
                    default:
                        break;
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fighter1ET.getText().toString().equals("")) {
                    NotifyHelper.showSingleButtonAlert(CreatePrivateScorecardActivity.this, "Enter fighter 1", "Please enter a name for fighter 1");
                } else if (fighter2ET.getText().toString().equals("")) {
                    NotifyHelper.showSingleButtonAlert(CreatePrivateScorecardActivity.this, "Enter fighter 2", "PLease enter a name for fighter 2");
                } else if (numRounds == 0) {
                    NotifyHelper.showSingleButtonAlert(CreatePrivateScorecardActivity.this, "Select # of Rounds", "Please select the number of rounds for the fight");
                }
            }
        });
    }
}
