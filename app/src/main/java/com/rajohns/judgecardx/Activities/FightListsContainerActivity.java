package com.rajohns.judgecardx.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Adapters.TabsPagerAdapter;

import javax.inject.Inject;

/**
 * Created by rajohns on 11/19/14.
 *
 */
public class FightListsContainerActivity extends FragmentActivity implements ActionBar.TabListener {
    @Inject
    RestClient restClient;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Fights", "Upcoming", "My Fights", "Recent" };

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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        final ListView listView = (ListView) findViewById(R.id.listview);
//        final ArrayList<Fight> fights = new ArrayList<Fight>();
//        final MasterFightListAdapter adapter = new MasterFightListAdapter(this, R.layout.row_fight, fights);
//
//        listView.setAdapter(adapter);
//
//        NotifyHelper.showLoading(this);
//        restClient.getMasterFightList(new Callback<JsonElement>() {
//            @Override
//            public void success(JsonElement jsonElement, Response response) {
//                NotifyHelper.hideLoading();
//                for (JsonElement je : jsonElement.getAsJsonArray()) {
//                    JsonObject object = je.getAsJsonObject();
//                    String fighter1 = object.get("fighter1").getAsString();
//                    String fighter2 = object.get("fighter2").getAsString();
//                    String date = object.get("date").getAsString();
//
//                    Fight fight = new Fight(fighter1, fighter2, date);
//                    fights.add(fight);
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                NotifyHelper.hideLoading();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
