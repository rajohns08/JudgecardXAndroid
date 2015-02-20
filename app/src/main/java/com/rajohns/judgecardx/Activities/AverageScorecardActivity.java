package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        Intent intent = getIntent();
        if (intent != null) {
            leftFighter = intent.getStringExtra(FightListFragment.LEFT_FIGHTER);
            rightFighter = intent.getStringExtra(FightListFragment.RIGHT_FIGHTER);
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
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
                NotifyHelper.showGeneralErrorMsg(AverageScorecardActivity.this);
            }
        });
    }
}
