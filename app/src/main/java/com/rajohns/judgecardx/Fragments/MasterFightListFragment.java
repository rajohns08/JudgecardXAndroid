package com.rajohns.judgecardx.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Adapters.FightListAdapter;
import com.rajohns.judgecardx.CustomApplication;
import com.rajohns.judgecardx.Model.Fight;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rajohns on 12/7/14.
 *
 */
public class MasterFightListFragment extends Fragment {
    @Inject RestClient restClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((CustomApplication) getActivity().getApplication()).getObjectGraph().inject(this);

        View rootView = inflater.inflate(R.layout.fragment_fight_list, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);
        final ArrayList<Fight> fights = new ArrayList<>();
        final FightListAdapter adapter = new FightListAdapter(getActivity(), R.layout.row_fight, fights);

        listView.setAdapter(adapter);

        NotifyHelper.showLoading(getActivity());
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

        return rootView;
    }
}
