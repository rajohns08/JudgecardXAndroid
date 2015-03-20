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
import com.rajohns.judgecardx.Activities.CreateOrRequestActivity;
import com.rajohns.judgecardx.Activities.FightListsContainerActivity;
import com.rajohns.judgecardx.Activities.ScorecardActivity;
import com.rajohns.judgecardx.Adapters.CreateRequestAdapter;
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

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
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
    public static final int REQUEST_TYPE = 456;

    public static final String SUBTEXT = "subtext";
    public static final String LEFT_FIGHTER = "leftFighter";
    public static final String RIGHT_FIGHTER = "rightFighter";
    public static final String ROUNDS = "rounds";
    public static final String FIGHT_DATE = "fightDate";
    public static final String CREATE_OR_REQUEST = "createOrRequest";

    private int fragmentPosition;
    private PtrClassicFrameLayout refreshControl;
    public Callback<JsonElement> callback;
    public static HashMap<Integer, Boolean> serviceCallsMade = new HashMap<>();

    final ArrayList<Fight> fights = new ArrayList<>();
    ArrayList<Fight> filteredFights;
    FightListAdapter adapter;
    CreateRequestAdapter noResultsAdapter;
    ListView listView;

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

        // Set up pull to refresh actions
        refreshControl = (PtrClassicFrameLayout)rootView.findViewById(R.id.rotate_header_list_view_frame);
        refreshControl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                callAppropriateRestMethodFromIndex(fragmentPosition);
            }
        });

        listView = (ListView) rootView.findViewById(R.id.listview);
        adapter = new FightListAdapter(getActivity(), R.layout.row_fight, fights);

        listView.setAdapter(adapter);

        // The upcoming fights section should not be clickable
        if (fragmentPosition != UPCOMING_FIGHTS_INDEX) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listView.getAdapter() instanceof CreateRequestAdapter) {
                        if (position == 1) {
                            Intent intent = new Intent(getActivity(), CreateOrRequestActivity.class);
                            getActivity().startActivityForResult(intent, FightListsContainerActivity.PRIVATE_SCORECARD_CREATED);
                        } else if (position == 2) {
                            Intent intent = new Intent(getActivity(), CreateOrRequestActivity.class);
                            intent.putExtra(CREATE_OR_REQUEST, REQUEST_TYPE);
                            getActivity().startActivityForResult(intent, FightListsContainerActivity.SCORECARD_JUST_REQUESTED);
                        }
                    } else {
                        Fight fightTapped;
                        if (filteredFights != null) {
                            fightTapped = filteredFights.get(position);
                        } else {
                            fightTapped = fights.get(position);
                        }

                        Intent intent = new Intent(getActivity(), ScorecardActivity.class);
                        intent.putExtra(TabsPagerAdapter.REST_CALL_KEY, fragmentPosition);
                        intent.putExtra(SUBTEXT, fightTapped.getSubtext());
                        intent.putExtra(LEFT_FIGHTER, fightTapped.getFighter1());
                        intent.putExtra(RIGHT_FIGHTER, fightTapped.getFighter2());
                        intent.putExtra(ROUNDS, fightTapped.getRounds());
                        intent.putExtra(FIGHT_DATE, fightTapped.getSubtext());
                        startActivity(intent);
                    }
                }
            });
        }

        callback = new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                NotifyHelper.hideLoading();
                refreshControl.refreshComplete();
                fights.clear();
                for (JsonElement je : jsonElement.getAsJsonArray()) {
                    JsonObject object = je.getAsJsonObject();
                    String fighter1 = object.get("fighter1").getAsString();
                    String fighter2 = object.get("fighter2").getAsString();
                    String rounds = object.get("rounds").getAsString();
                    String privateCard = "NO";
                    if (fragmentPosition == MY_CARDS_INDEX) {
                        privateCard = object.get("private").getAsString();
                    }

                    int roundsNum = Integer.parseInt(rounds);

                    String subtext;
                    if (fragmentPosition == RECENT_CARDS_INDEX) {
                        String username = object.get("username").getAsString();
                        subtext = "User: " + username;
                    }
                    else {
                        subtext = object.get("date").getAsString();
                    }

                    Fight fight = new Fight(fighter1, fighter2, subtext, roundsNum, privateCard);
                    fights.add(fight);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
                NotifyHelper.showGeneralErrorMsg(getActivity());
                refreshControl.refreshComplete();
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

        // Pass the searchview back to the activity so it can handle clearing it when coming back
        ((FightListsContainerActivity) getActivity()).setSearchView(searchView);

        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    filteredFights = new ArrayList<>();
                    for (Fight f : fights) {
                        if (f.getFullSearchText().toLowerCase().contains(newText.toLowerCase())) {
                            filteredFights.add(f);
                        }
                    }

                    if (filteredFights.size() > 0) {
                        adapter = new FightListAdapter(getActivity(), R.layout.row_fight, filteredFights);
                        listView.setAdapter(adapter);
                    } else {
                        if (fragmentPosition == MASTER_FIGHT_LIST_INDEX) {
                            noResultsAdapter = new CreateRequestAdapter(getActivity(), true);
                        } else {
                            noResultsAdapter = new CreateRequestAdapter(getActivity(), false);
                        }

                        listView.setAdapter(noResultsAdapter);
                    }
                } else {
                    filteredFights = null;
                    adapter = new FightListAdapter(getActivity(), R.layout.row_fight, fights);
                    listView.setAdapter(adapter);
                }

                return false;
            }
        });
    }
}
