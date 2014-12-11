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
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.CustomApplication;
import com.rajohns.judgecardx.Model.Fight;
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
 * Created by rajohns on 12/7/14.
 *
 */
public class FightListFragment extends Fragment {
    @Inject RestClient restClient;
    @Inject ObscuredSharedPreferences prefs;

    private static final int MASTER_FIGHT_LIST_INDEX = 0;
    private static final int UPCOMING_FIGHTS_INDEX = 1;
    private static final int MY_CARDS_INDEX = 2;
    private static final int RECENT_CARDS_INDEX = 3;

    private int position;

    private Callback<JsonElement> callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((CustomApplication) getActivity().getApplication()).getObjectGraph().inject(this);

        if (getArguments() != null) {
            position = getArguments().getInt(TabsPagerAdapter.REST_CALL_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_fight_list, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);
        final ArrayList<Fight> fights = new ArrayList<>();
        final FightListAdapter adapter = new FightListAdapter(getActivity(), R.layout.row_fight, fights);

        listView.setAdapter(adapter);

        callback = new Callback<JsonElement>() {
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
        };

        callAppropriateRestMethodFromIndex(position);

        return rootView;
    }

    private void callAppropriateRestMethodFromIndex(int index) {
        NotifyHelper.showLoading(getActivity());

        switch (index) {
            case MASTER_FIGHT_LIST_INDEX:
                restClient.getMasterFightList(callback);
                break;
            case UPCOMING_FIGHTS_INDEX:
                restClient.getUpcomingFights(callback);
                break;
            case MY_CARDS_INDEX:
                String username = prefs.getString(USERNAME_PREF, "");
                restClient.getMyCards(username, callback);
                break;
            case RECENT_CARDS_INDEX:
                restClient.getUpcomingFights(callback);
                break;
            default:
                NotifyHelper.hideLoading();
                break;
        }
    }
}
