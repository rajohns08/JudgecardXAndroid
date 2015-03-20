package com.rajohns.judgecardx.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;
import com.rajohns.judgecardx.Utils.NotifyHelper;

import javax.inject.Inject;

/**
 * Created by rajohns on 11/19/14.
 *
 */
public class FightListsContainerActivity extends FragmentActivity implements ActionBar.TabListener {
    @Inject RestClient restClient;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private SearchView searchView;
    // Tab titles
    private String[] tabs = { "Fights", "Upcoming", "My Fights", "Recent" };
    public static final int PRIVATE_SCORECARD_CREATED = 111;
    public static final int SCORECARD_JUST_REQUESTED = 222;
    public static final String PRIVATE_CARD_JUST_CREATED = "com.rajohns.judgecardx.privatecardcreated";
    public static final String SCORECARD_JUST_REQUESTED_SUCCESS = "com.rajohns.judgecardx.scorecardrequested";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_lists_container);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                // Only make the service call for the list if it hasn't already been made
                if (!FightListFragment.serviceCallsMade.get(position)) {
                    mAdapter.getFragment(position).callAppropriateRestMethodFromIndex(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // Using onRestart to reload certain tabs if coming back to them after deleting from detail
    // Don't want to use onStart because don't want to load these tabs before we have ever
    // scrolled to them.
    @Override
    protected void onRestart() {
        super.onRestart();

        clearActionBarSearch();

        switch (actionBar.getSelectedNavigationIndex()) {
            case FightListFragment.MY_CARDS_INDEX:
                if (!FightListFragment.serviceCallsMade.get(FightListFragment.MY_CARDS_INDEX)) {
                    mAdapter.getFragment(FightListFragment.MY_CARDS_INDEX).callAppropriateRestMethodFromIndex(FightListFragment.MY_CARDS_INDEX);
                }
                break;
            case FightListFragment.RECENT_CARDS_INDEX:
                if (!FightListFragment.serviceCallsMade.get(FightListFragment.RECENT_CARDS_INDEX)) {
                    mAdapter.getFragment(FightListFragment.RECENT_CARDS_INDEX).callAppropriateRestMethodFromIndex(FightListFragment.RECENT_CARDS_INDEX);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PRIVATE_SCORECARD_CREATED) {
            if (resultCode == RESULT_OK) {
                boolean privateCardJustCreated = data.getBooleanExtra(PRIVATE_CARD_JUST_CREATED, false);
                if (privateCardJustCreated) {
                    actionBar.setSelectedNavigationItem(FightListFragment.MY_CARDS_INDEX);
                }
            }
        } else if (requestCode == SCORECARD_JUST_REQUESTED) {
            if (resultCode == RESULT_OK) {
                boolean scorecardRequestedSuccess = data.getBooleanExtra(SCORECARD_JUST_REQUESTED_SUCCESS, false);
                if (scorecardRequestedSuccess) {
                    NotifyHelper.showSingleButtonAlert(this, "Success", "Your request has been successfully submitted.");
                }
            }
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
        clearActionBarSearch();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    private void clearActionBarSearch() {
        if (this.searchView != null) {
            this.searchView.setQuery("", false);
            this.searchView.clearFocus();
        }
    }
}
