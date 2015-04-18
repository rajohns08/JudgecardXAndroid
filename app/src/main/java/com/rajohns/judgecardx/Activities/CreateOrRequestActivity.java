package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.DateUtil;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;

import java.sql.Date;

import javax.inject.Inject;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.Utils.ObscuredSharedPreferences.USERNAME_PREF;

/**
 * Created by rajohns on 3/9/15.
 *
 */
public class CreateOrRequestActivity extends BaseActivity {
    @Inject RestClient restClient;
    @Inject ObscuredSharedPreferences prefs;
    private String numRounds;
    private int type;
    private ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private);

        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);

        SegmentedGroup roundSegment = (SegmentedGroup)findViewById(R.id.numRoundsPicker);
        final EditText fighter1ET = (EditText)findViewById(R.id.fighter1ET);
        final EditText fighter2ET = (EditText)findViewById(R.id.fighter2ET);
        final EditText dateET = (EditText)findViewById(R.id.dateET);
        Button createButton = (Button)findViewById(R.id.createButton);

        type = getIntent().getIntExtra(FightListFragment.CREATE_OR_REQUEST, 0);
        if (type != FightListFragment.REQUEST_TYPE) {
            dateET.setVisibility(View.GONE);
            createButton.setText("Create Scorecard");
            setTitle("Create Private Scorecard");
        }

        roundSegment.setTintColor(getResources().getColor(R.color.base_blue));
        roundSegment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rounds3:
                        numRounds = "3";
                        break;
                    case R.id.rounds4:
                        numRounds = "4";
                        break;
                    case R.id.rounds5:
                        numRounds = "5";
                        break;
                    case R.id.rounds6:
                        numRounds = "6";
                        break;
                    case R.id.rounds8:
                        numRounds = "8";
                        break;
                    case R.id.rounds10:
                        numRounds = "10";
                        break;
                    case R.id.rounds12:
                        numRounds = "12";
                        break;
                    case R.id.rounds15:
                        numRounds = "15";
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
                    NotifyHelper.showSingleButtonAlert(CreateOrRequestActivity.this, "Enter fighter 1", "Please enter a name for fighter 1");
                } else if (fighter2ET.getText().toString().equals("")) {
                    NotifyHelper.showSingleButtonAlert(CreateOrRequestActivity.this, "Enter fighter 2", "PLease enter a name for fighter 2");
                } else if (dateET.getVisibility()==View.VISIBLE && dateET.getText().toString().equals("")) {
                    NotifyHelper.showSingleButtonAlert(CreateOrRequestActivity.this, "Enter date", "Please enter a date in mm/dd/yyyy format");
                } else if (numRounds == null) {
                    NotifyHelper.showSingleButtonAlert(CreateOrRequestActivity.this, "Select # of Rounds", "Please select the number of rounds for the fight");
                } else {
                    String fighter1 = fighter1ET.getText().toString();
                    String fighter2 = fighter2ET.getText().toString();
                    String username = prefs.getString(USERNAME_PREF, "");

                    NotifyHelper.showLoading(progressWheel);
                    if (dateET.getVisibility()==View.VISIBLE) {
                        String date = dateET.getText().toString();
                        requestPublicCard(fighter1, fighter2, date, username);
                    } else {
                        Date date = DateUtil.sqlDateFromString("09-09-9999");
                        createPrivateCard(fighter1, fighter2, date, username);
                    }
                }
            }
        });
    }

    private void createPrivateCard(String fighter1, String fighter2, Date date, String username) {
        restClient.createPrivateScorecard(fighter1, fighter2, numRounds, date, username, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                NotifyHelper.hideLoading(progressWheel);

                if (s.equals(RestClient.SCORECARD_CREATED)) {
                    FightListFragment.serviceCallsMade.put(FightListFragment.MY_CARDS_INDEX, false);
                    Intent intent = getIntent();
                    intent.putExtra(FightListsContainerActivity.PRIVATE_CARD_JUST_CREATED, true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (s.equals(RestClient.SCORECARD_ALREADY_EXISTS)) {
                    NotifyHelper.showSingleButtonAlert(CreateOrRequestActivity.this, "Already Exists", "You already have a scorecard with these fighters.");
                } else {
                    NotifyHelper.showGeneralErrorMsg(CreateOrRequestActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading(progressWheel);
                NotifyHelper.showGeneralErrorMsg(CreateOrRequestActivity.this);
            }
        });
    }

    private void requestPublicCard(String fighter1, String fighter2, String date, String username) {
        restClient.requestPublicScorecard(fighter1, fighter2, numRounds, date, username, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                NotifyHelper.hideLoading(progressWheel);
                if (s.equals(RestClient.REQUEST_SUCCESS)) {
                    Intent intent = getIntent();
                    intent.putExtra(FightListsContainerActivity.SCORECARD_JUST_REQUESTED_SUCCESS, true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    NotifyHelper.showGeneralErrorMsg(CreateOrRequestActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading(progressWheel);
                NotifyHelper.showGeneralErrorMsg(CreateOrRequestActivity.this);
            }
        });
    }
}
