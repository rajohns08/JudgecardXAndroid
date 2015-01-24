package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        Intent intent = getIntent();
        if (intent != null) {
            int fragmentSource = intent.getIntExtra(TabsPagerAdapter.REST_CALL_KEY, 0);
            subtext = intent.getStringExtra(FightListFragment.SUBTEXT);
            leftFighter = intent.getStringExtra(FightListFragment.LEFT_FIGHTER);
            rightFighter = intent.getStringExtra(FightListFragment.RIGHT_FIGHTER);
            setTitle(actionBarTitle(fragmentSource));
            TextView leftFighterTV = (TextView)findViewById(R.id.leftFighter);
            TextView rightFighterTV = (TextView)findViewById(R.id.rightFighter);
            leftFighterTV.setText(leftFighter);
            rightFighterTV.setText(rightFighter);
        }

        ListView listView = (ListView)findViewById(R.id.scorecardList);


        NotifyHelper.showLoading(this);
        String username = prefs.getString(USERNAME_PREF, "");
        restClient.getScorecardDetail(username, leftFighter, rightFighter, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();

                JsonObject object = jsonElement.getAsJsonObject();
                String f1r1 = object.get("f1r1").getAsString();
                String f1r2 = object.get("f1r2").getAsString();
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
            }
        });





        Round r1 = new Round(10, 9);
        Round r2 = new Round(10, 9);
        Round r3 = new Round(10, 9);
        Round r4 = new Round(10, 9);
        Round r5 = new Round(10, 9);
        Round r6 = new Round(10, 9);
        Round r7 = new Round(10, 9);
        Round r8 = new Round(10, 9);
        Round r9 = new Round(10, 9);
        ArrayList<Round> s = new ArrayList<>();
        s.add(r1);
        s.add(r2);
        s.add(r3);
        s.add(r4);
        s.add(r5);
        s.add(r6);
        s.add(r7);
        s.add(r8);
        s.add(r9);
        Scorecard scorecard = new Scorecard(s);
        ScorecardAdapter adapter = new ScorecardAdapter(this, R.layout.row_scorecard, scorecard);
        listView.setAdapter(adapter);
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
