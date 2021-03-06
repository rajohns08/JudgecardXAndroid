package com.rajohns.judgecardx.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import uk.co.androidalliance.edgeeffectoverride.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.rajohns.judgecardx.Adapters.ScorecardAdapter;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.Model.Scorecard;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.DateUtil;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;
import com.rajohns.judgecardx.Utils.OldServerCheck;
import com.rajohns.judgecardx.Utils.StringUtils;

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
    String fightDateString;
    java.sql.Date sqlFightDate;
    int rounds;
    TextView leftTotalTV;
    TextView rightTotalTV;
    ScorecardAdapter adapter;
    private ProgressWheel progressWheel;

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
            fightDateString = intent.getStringExtra(FightListFragment.FIGHT_DATE);
            setTitle(actionBarTitle(fragmentSource));
            TextView leftFighterTV = (TextView)findViewById(R.id.leftFighter);
            TextView rightFighterTV = (TextView)findViewById(R.id.rightFighter);
            leftFighterTV.setText(leftFighter);
            rightFighterTV.setText(StringUtils.removeNumbers(rightFighter));
        }

        sqlFightDate = DateUtil.sqlDateFromString(fightDateString);

        final ListView listView = (ListView)findViewById(R.id.scorecardList);
        leftTotalTV = (TextView)findViewById(R.id.leftFighterTotalScore);
        rightTotalTV = (TextView)findViewById(R.id.rightFighterTotalScore);
        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);

        NotifyHelper.showLoading(progressWheel);

        String username;
        if (fragmentSource == FightListFragment.RECENT_CARDS_INDEX) {
            username = getJustUsernameFromSubtext(subtext);
        }
        else {
            username = prefs.getString(USERNAME_PREF, "");
        }

        final int finalFragmentSource = fragmentSource;
        restClient.getScorecardDetail(username, leftFighter, rightFighter, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading(progressWheel);

                if (OldServerCheck.isOldServer(ScorecardActivity.this, jsonElement)) return;

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

                // Need to replace quotes (which is what \" does) because this toString method
                // obviously inserts more quotes into an already existing string
                String id = object.get("id").toString().replace("\"", "");

                int leftTotal = 0;
                int rightTotal = 0;
                ArrayList<Round> roundsList = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    Round round = new Round(leftScoresList.get(i), rightScoresList.get(i));
                    roundsList.add(round);
                    leftTotal += Integer.parseInt(leftScoresList.get(i));
                    rightTotal += Integer.parseInt(rightScoresList.get(i));
                }

                leftTotalTV.setText(Integer.toString(leftTotal));
                rightTotalTV.setText(Integer.toString(rightTotal));

                Scorecard scorecard = new Scorecard(roundsList, id);
                adapter = new ScorecardAdapter(ScorecardActivity.this, scorecard, finalFragmentSource, rounds);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading(progressWheel);

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

    public void updateTotalScores(Scorecard scorecard) {
        leftTotalTV.setText(Integer.toString(scorecard.getLeftTotal()));
        rightTotalTV.setText(Integer.toString(scorecard.getRightTotal()));
    }

    public void createOrUpdateScorecard(final Scorecard scorecard) {
        String username = prefs.getString(USERNAME_PREF, "");
        List<Round> myRounds = scorecard.getScorecard();
        restClient.createOrUpdateScorecard( username,
                                            leftFighter,
                                            rightFighter,
                                            sqlFightDate,
                                            Integer.toString(rounds),
                                            myRounds.get(0).getLeftScore(),
                                            myRounds.get(1).getLeftScore(),
                                            myRounds.get(2).getLeftScore(),
                                            myRounds.get(3).getLeftScore(),
                                            myRounds.get(4).getLeftScore(),
                                            myRounds.get(5).getLeftScore(),
                                            myRounds.get(6).getLeftScore(),
                                            myRounds.get(7).getLeftScore(),
                                            myRounds.get(8).getLeftScore(),
                                            myRounds.get(9).getLeftScore(),
                                            myRounds.get(10).getLeftScore(),
                                            myRounds.get(11).getLeftScore(),
                                            myRounds.get(12).getLeftScore(),
                                            myRounds.get(13).getLeftScore(),
                                            myRounds.get(14).getLeftScore(),
                                            myRounds.get(0).getRightScore(),
                                            myRounds.get(1).getRightScore(),
                                            myRounds.get(2).getRightScore(),
                                            myRounds.get(3).getRightScore(),
                                            myRounds.get(4).getRightScore(),
                                            myRounds.get(5).getRightScore(),
                                            myRounds.get(6).getRightScore(),
                                            myRounds.get(7).getRightScore(),
                                            myRounds.get(8).getRightScore(),
                                            myRounds.get(9).getRightScore(),
                                            myRounds.get(10).getRightScore(),
                                            myRounds.get(11).getRightScore(),
                                            myRounds.get(12).getRightScore(),
                                            myRounds.get(13).getRightScore(),
                                            myRounds.get(14).getRightScore(),
                                            new Callback<JsonElement>() {
                                                @Override
                                                public void success(JsonElement jsonElement, Response response) {

                                                    if (OldServerCheck.isOldServer(ScorecardActivity.this, jsonElement)) return;

                                                    if (jsonElement != null) {
                                                        JsonObject object = jsonElement.getAsJsonObject();
                                                        String created = object.get("msg").getAsString();

                                                        if (created.equals(RestClient.SCORECARD_CREATED)) {
                                                            scorecard.setId(object.get("id").getAsString());
                                                            adapter.notifyDataSetChanged();
                                                            NotifyHelper.showSingleButtonAlert(ScorecardActivity.this, "Scorecard Created", "This scorecard has been saved to 'My Cards'");
                                                            FightListFragment.serviceCallsMade.put(FightListFragment.MY_CARDS_INDEX, false);
                                                            FightListFragment.serviceCallsMade.put(FightListFragment.RECENT_CARDS_INDEX, false);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    // Don't do anything here because this is a silent network call
                                                }
                                            });
    }

    public void deleteScorecard(final Scorecard scorecard) {
        NotifyHelper.showLoading(progressWheel);
        String username = prefs.getString(USERNAME_PREF, "");
        restClient.deleteScorecard(scorecard.getId(), username, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                NotifyHelper.hideLoading(progressWheel);

                if (OldServerCheck.isOldServer(ScorecardActivity.this, s)) return;

                switch (s) {
                    case RestClient.DELETE_SUCCESS:
                        finish();
                        FightListFragment.serviceCallsMade.put(FightListFragment.MY_CARDS_INDEX, false);
                        FightListFragment.serviceCallsMade.put(FightListFragment.RECENT_CARDS_INDEX, false);
                        break;
                    case RestClient.DELETE_FAILED:
                        NotifyHelper.showSingleButtonAlert(ScorecardActivity.this, "Error", "Cannot delete scorecard at this time.");
                        break;
                    default:
                        NotifyHelper.showGeneralErrorMsg(ScorecardActivity.this);
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading(progressWheel);
                NotifyHelper.showGeneralErrorMsg(ScorecardActivity.this);
            }
        });
    }

    public void resetScorecard(Scorecard scorecard) {
        for (Round round : scorecard.getScorecard()) {
            round.setLeftScore("0");
            round.setRightScore("0");
        }

        createOrUpdateScorecard(scorecard);
        leftTotalTV.setText("0");
        rightTotalTV.setText("0");
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scorecard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.avg_scorecard) {
            Intent intent = new Intent(this, AverageScorecardActivity.class);
            intent.putExtra(FightListFragment.LEFT_FIGHTER, leftFighter);
            intent.putExtra(FightListFragment.RIGHT_FIGHTER, rightFighter);
            intent.putExtra(FightListFragment.ROUNDS, rounds);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
