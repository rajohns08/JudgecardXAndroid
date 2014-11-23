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


        final ArrayAdapter<Fight> adapter = new ArrayAdapter<Fight>(this, android.R.layout.simple_list_item_2, android.R.id.text1, fights) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                Fight fight = fights.get(position);

                text1.setText(fight.getFighter1() + " - " + fight.getFighter2());
                text2.setText(fight.getFightDate());

                return view;
            }
        };

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        //MAKE SERVICE CALL AND GET FIGHTS
        NotifyHelper.showLoading(this);
        restClient.getMasterFightList(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();
                for (JsonElement je : jsonElement.getAsJsonArray()) {
                    Log.d("tag", je.toString());
                    JsonObject object = je.getAsJsonObject();
                    JsonElement e = object.get("fighter1");
                    String fighter1 = e.getAsString();
                    JsonElement f = object.get("fighter2");
                    String fighter2 = f.getAsString();
                    JsonElement g = object.get("date");
                    String date = g.getAsString();

                    Fight fight = new Fight(fighter1, fighter2, date);
                    fights.add(fight);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
            }
        });
    }
}
