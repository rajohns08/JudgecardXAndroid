package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Fragments.FightListFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

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
                for (int i = 0; i < 15; i++) {
                    Round round = new Round(leftScoresList.get(i), rightScoresList.get(i));
                    roundsList.add(round);
                    leftTotal += Integer.parseInt(leftScoresList.get(i));
                    rightTotal += Integer.parseInt(rightScoresList.get(i));
                }

                leftTotalTV.setText(Integer.toString(leftTotal));
                rightTotalTV.setText(Integer.toString(rightTotal));
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
                NotifyHelper.showGeneralErrorMsg(AverageScorecardActivity.this);
            }
        });
    }
}
