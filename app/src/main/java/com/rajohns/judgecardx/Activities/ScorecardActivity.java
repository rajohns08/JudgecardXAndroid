package com.rajohns.judgecardx.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Adapters.ScorecardAdapter;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.Model.Scorecard;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.Utils.ObscuredSharedPreferences.USERNAME_PREF;

/**
 * Created by rajohns on 1/5/15.
 *
 */
public class ScorecardActivity extends BaseActivity {
    @Inject RestClient restClient;
    @Inject ObscuredSharedPreferences prefs;

    String subtext;
    String leftFighter;
    String rightFighter;
    int rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        int fragmentSource = 0;
        Intent intent = getIntent();
        if (intent != null) {
            fragmentSource = intent.getIntExtra(TabsPagerAdapter.REST_CALL_KEY, 0);
            subtext = intent.getStringExtra(FightListFragment.SUBTEXT);
            leftFighter = intent.getStringExtra(FightListFragment.LEFT_FIGHTER);
            rightFighter = intent.getStringExtra(FightListFragment.RIGHT_FIGHTER);
            rounds = intent.getIntExtra(FightListFragment.ROUNDS, 0);
            setTitle(actionBarTitle(fragmentSource));
            TextView leftFighterTV = (TextView)findViewById(R.id.leftFighter);
            TextView rightFighterTV = (TextView)findViewById(R.id.rightFighter);
            leftFighterTV.setText(leftFighter);
            rightFighterTV.setText(rightFighter);
        }

        final ListView listView = (ListView)findViewById(R.id.scorecardList);
        final TextView leftTotalTV = (TextView)findViewById(R.id.leftFighterTotalScore);
        final TextView rightTotalTV = (TextView)findViewById(R.id.rightFighterTotalScore);

        NotifyHelper.showLoading(this);

        String username;
        if (fragmentSource == FightListFragment.RECENT_CARDS_INDEX) {
            username = getJustUsernameFromSubtext(subtext);
        }
        else {
            username = prefs.getString(USERNAME_PREF, "");
        }

        restClient.getScorecardDetail(username, leftFighter, rightFighter, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();

                JsonObject object = jsonElement.getAsJsonObject();

                List<String> leftScoresList = Arrays.asList(
                    object.get("f1r1").getAsString(),
                    object.get("f1r2").getAsString(),
                    object.get("f1r3").getAsString(),
                    object.get("f1r4").getAsString(),
                    object.get("f1r5").getAsString(),
                    object.get("f1r6").getAsString(),
                    object.get("f1r7").getAsString(),
                    object.get("f1r8").getAsString(),
                    object.get("f1r9").getAsString(),
                    object.get("f1r10").getAsString(),
                    object.get("f1r11").getAsString(),
                    object.get("f1r12").getAsString(),
                    object.get("f1r13").getAsString(),
                    object.get("f1r14").getAsString(),
                    object.get("f1r15").getAsString()
                );
                List<String> rightScoresList = Arrays.asList(
                        object.get("f2r1").getAsString(),
                        object.get("f2r2").getAsString(),
                        object.get("f2r3").getAsString(),
                        object.get("f2r4").getAsString(),
                        object.get("f2r5").getAsString(),
                        object.get("f2r6").getAsString(),
                        object.get("f2r7").getAsString(),
                        object.get("f2r8").getAsString(),
                        object.get("f2r9").getAsString(),
                        object.get("f2r10").getAsString(),
                        object.get("f2r11").getAsString(),
                        object.get("f2r12").getAsString(),
                        object.get("f2r13").getAsString(),
                        object.get("f2r14").getAsString(),
                        object.get("f2r15").getAsString()
                );

                int leftTotal = 0;
                int rightTotal = 0;
                ArrayList<Round> roundsList = new ArrayList<>();
                for (int i = 0; i < rounds; i++) {
                    Round round = new Round(leftScoresList.get(i), rightScoresList.get(i));
                    roundsList.add(round);
                    leftTotal += Integer.parseInt(leftScoresList.get(i));
                    rightTotal += Integer.parseInt(rightScoresList.get(i));
                }

                leftTotalTV.setText(Integer.toString(leftTotal));
                rightTotalTV.setText(Integer.toString(rightTotal));

                Scorecard scorecard = new Scorecard(roundsList);
                ScorecardAdapter adapter = new ScorecardAdapter(ScorecardActivity.this, R.layout.row_scorecard, scorecard);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();

                AlertDialog alertDialog;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScorecardActivity.this);
                alertDialogBuilder.setTitle("Unknown Error");
                alertDialogBuilder.setMessage("An unknown error occurred. Either your internet is down, or we are having server issues.");
                alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
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
