package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Adapters.AvgScorecardAdapter;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Model.AvgRound;
import com.rajohns.judgecardx.Model.AvgScorecard;
import com.rajohns.judgecardx.Model.Round;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rajohns on 2/14/15.
 *
 */
public class AverageScorecardActivity extends BaseActivity {
    @Inject RestClient restClient;

    private String leftFighter;
    private String rightFighter;
    private int rounds;
    TextView leftTotalTV;
    TextView rightTotalTV;
    AvgScorecardAdapter adapter;
    List<String> leftConfidenceList;
    List<String> rightConfidenceList;
    int numOfCards;
    MenuItem numPeopleMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        final ListView listView = (ListView)findViewById(R.id.scorecardList);
        leftTotalTV = (TextView)findViewById(R.id.leftFighterTotalScore);
        rightTotalTV = (TextView)findViewById(R.id.rightFighterTotalScore);
        TextView leftFighterTV = (TextView)findViewById(R.id.leftFighter);
        TextView rightFighterTV = (TextView)findViewById(R.id.rightFighter);

        Intent intent = getIntent();
        if (intent != null) {
            leftFighter = intent.getStringExtra(FightListFragment.LEFT_FIGHTER);
            rightFighter = intent.getStringExtra(FightListFragment.RIGHT_FIGHTER);
            leftFighterTV.setText(leftFighter);
            rightFighterTV.setText(StringUtils.removeNumbers(rightFighter));
            rounds = intent.getIntExtra(FightListFragment.ROUNDS, 0);
        }

        NotifyHelper.showLoading(this);
        restClient.getAvgScorecard(leftFighter, rightFighter, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();

                JsonObject object = jsonElement.getAsJsonObject();

                numOfCards = object.get("numOfCards").getAsInt();
                numPeopleMenuItem.setTitle("" + numOfCards);

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
                leftConfidenceList = Arrays.asList(
                        object.get("f1r1conf").getAsString(),
                        object.get("f1r2conf").getAsString(),
                        object.get("f1r3conf").getAsString(),
                        object.get("f1r4conf").getAsString(),
                        object.get("f1r5conf").getAsString(),
                        object.get("f1r6conf").getAsString(),
                        object.get("f1r7conf").getAsString(),
                        object.get("f1r8conf").getAsString(),
                        object.get("f1r9conf").getAsString(),
                        object.get("f1r10conf").getAsString(),
                        object.get("f1r11conf").getAsString(),
                        object.get("f1r12conf").getAsString(),
                        object.get("f1r13conf").getAsString(),
                        object.get("f1r14conf").getAsString(),
                        object.get("f1r15conf").getAsString()
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
                rightConfidenceList = Arrays.asList(
                        object.get("f2r1conf").getAsString(),
                        object.get("f2r2conf").getAsString(),
                        object.get("f2r3conf").getAsString(),
                        object.get("f2r4conf").getAsString(),
                        object.get("f2r5conf").getAsString(),
                        object.get("f2r6conf").getAsString(),
                        object.get("f2r7conf").getAsString(),
                        object.get("f2r8conf").getAsString(),
                        object.get("f2r9conf").getAsString(),
                        object.get("f2r10conf").getAsString(),
                        object.get("f2r11conf").getAsString(),
                        object.get("f2r12conf").getAsString(),
                        object.get("f2r13conf").getAsString(),
                        object.get("f2r14conf").getAsString(),
                        object.get("f2r15conf").getAsString()
                );

                int leftTotal = 0;
                int rightTotal = 0;
                ArrayList<AvgRound> roundsList = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    AvgRound round = new AvgRound(leftScoresList.get(i), leftConfidenceList.get(i), rightScoresList.get(i), rightConfidenceList.get(i));
                    roundsList.add(round);
                    leftTotal += Integer.parseInt(leftScoresList.get(i));
                    rightTotal += Integer.parseInt(rightScoresList.get(i));
                }

                leftTotalTV.setText(Integer.toString(leftTotal));
                rightTotalTV.setText(Integer.toString(rightTotal));

                AvgScorecard avgScorecard = new AvgScorecard(roundsList);
                adapter = new AvgScorecardAdapter(AverageScorecardActivity.this, avgScorecard, rounds);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
                NotifyHelper.showGeneralErrorMsg(AverageScorecardActivity.this);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String leftConfString = leftConfidenceList.get(position);
                String rightConfString = rightConfidenceList.get(position);
                double leftConf = Double.parseDouble(leftConfString) * 100;
                double rightConf = Double.parseDouble(rightConfString) * 100;
                NotifyHelper.showSingleButtonAlert(AverageScorecardActivity.this, "Round " + (position+1), String.format("%.0f", leftConf) + "% gave " + leftFighter + " a 10\n" + String.format("%.0f", rightConf) + "% gave " + StringUtils.removeNumbers(rightFighter) + " a 10");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.avg_scorecard, menu);
        numPeopleMenuItem = menu.findItem(R.id.num_people);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.num_people) {
            String person = this.getResources().getQuantityString(R.plurals.person, numOfCards);
            String is = this.getResources().getQuantityString(R.plurals.is, numOfCards);
            String has = this.getResources().getQuantityString(R.plurals.has, numOfCards);
            NotifyHelper.showSingleButtonAlert(this, "Number of judges", numOfCards + " " + person + " " + has + " scored or " + is + " scoring this fight.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
