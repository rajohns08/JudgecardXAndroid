package com.rajohns.judgecardx.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rajohns.judgecardx.Activities.FightListsContainerActivity;
import com.rajohns.judgecardx.Activities.ScorecardActivity;
import com.rajohns.judgecardx.Adapters.FightListAdapter;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.CustomApplication;
import com.rajohns.judgecardx.Model.Fight;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static final int MASTER_FIGHT_LIST_INDEX = 0;
    public static final int UPCOMING_FIGHTS_INDEX = 1;
    public static final int MY_CARDS_INDEX = 2;
    public static final int RECENT_CARDS_INDEX = 3;

    public static final String SUBTEXT = "subtext";
    public static final String LEFT_FIGHTER = "leftFighter";
    public static final String RIGHT_FIGHTER = "rightFighter";
    public static final String ROUNDS = "rounds";
    public static final String FIGHT_DATE = "fightDate";

    private int fragmentPosition;
    public Callback<JsonElement> callback;
    public static HashMap<Integer, Boolean> serviceCallsMade = new HashMap<>();

    final ArrayList<Fight> fights = new ArrayList<>();
    FightListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((CustomApplication) getActivity().getApplication()).getObjectGraph().inject(this);

        setHasOptionsMenu(true);

        // Initially only the first page will have its service call made
        serviceCallsMade.put(MASTER_FIGHT_LIST_INDEX, true);
        serviceCallsMade.put(UPCOMING_FIGHTS_INDEX, false);
        serviceCallsMade.put(MY_CARDS_INDEX, false);
        serviceCallsMade.put(RECENT_CARDS_INDEX, false);

        if (getArguments() != null) {
            fragmentPosition = getArguments().getInt(TabsPagerAdapter.REST_CALL_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_fight_list, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);
        adapter = new FightListAdapter(getActivity(), R.layout.row_fight, fights);

        listView.setAdapter(adapter);

        // The upcoming fights section should not be clickable
        if (fragmentPosition != UPCOMING_FIGHTS_INDEX) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ScorecardActivity.class);
                    intent.putExtra(TabsPagerAdapter.REST_CALL_KEY, fragmentPosition);
                    intent.putExtra(SUBTEXT, fights.get(position).getSubtext());
                    intent.putExtra(LEFT_FIGHTER, fights.get(position).getFighter1());
                    intent.putExtra(RIGHT_FIGHTER, fights.get(position).getFighter2());
                    intent.putExtra(ROUNDS, fights.get(position).getRounds());
                    intent.putExtra(FIGHT_DATE, fights.get(position).getSubtext());
                    startActivity(intent);
                }
            });
        }

        callback = new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();
                fights.clear();
                for (JsonElement je : jsonElement.getAsJsonArray()) {
                    JsonObject object = je.getAsJsonObject();
                    String fighter1 = object.get("fighter1").getAsString();
                    String fighter2 = object.get("fighter2").getAsString();
                    String rounds = object.get("rounds").getAsString();
                    int roundsNum = Integer.parseInt(rounds);

                    String subtext;
                    if (fragmentPosition == RECENT_CARDS_INDEX) {
                        String username = object.get("username").getAsString();
                        subtext = "User: " + username;
                    }
                    else {
                        subtext = object.get("date").getAsString();
                    }

                    Fight fight = new Fight(fighter1, fighter2, subtext, roundsNum);
                    fights.add(fight);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
                NotifyHelper.showGeneralErrorMsg(getActivity());
            }
        };

        // Go ahead and make the first service call. Wait
        // to make the other service calls after their
        // tab has been navigated to.
        if (fragmentPosition == MASTER_FIGHT_LIST_INDEX) {
            callAppropriateRestMethodFromIndex(fragmentPosition);
        }

        return rootView;
    }

    public void callAppropriateRestMethodFromIndex(int index) {
        NotifyHelper.showLoading(getActivity());
        serviceCallsMade.put(index, true);

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
                restClient.getRecentScorecards(callback);
                break;
            default:
                NotifyHelper.hideLoading();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fight_list, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity().getActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query: " + query);

                //proof of concept for updating list from search
                fights.clear();
                adapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("newText: " + newText);
                return false;
            }
        });
    }
}
