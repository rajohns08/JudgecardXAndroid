package com.rajohns.judgecardx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rajohns on 11/19/14.
 *
 */
public class MasterFightListActivity extends BaseActivity {
    @Inject RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_fight_list);
        ButterKnife.inject(this);

        final ListView listView = (ListView) findViewById(R.id.listview);
        final ArrayList<Fight> fights = new ArrayList<Fight>();
        final MasterFightListAdapter adapter = new MasterFightListAdapter(this, R.layout.row_fight, fights);

        listView.setAdapter(adapter);

        NotifyHelper.showLoading(this);
        restClient.getMasterFightList(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();
                for (JsonElement je : jsonElement.getAsJsonArray()) {
                    JsonObject object = je.getAsJsonObject();
                    String fighter1 = object.get("fighter1").getAsString();
                    String fighter2 = object.get("fighter2").getAsString();
                    String date = object.get("date").getAsString();

                    Fight fight = new Fight(fighter1, fighter2, date);
                    fights.add(fight);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
            }
        });
    }
}
