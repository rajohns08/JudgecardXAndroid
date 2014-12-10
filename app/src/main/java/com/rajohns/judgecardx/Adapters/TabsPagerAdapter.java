package com.rajohns.judgecardx.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rajohns.judgecardx.Fragments.FightListFragment;

/**
 * Created by rajohns on 12/7/14.
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public static final String REST_CALL_KEY = "judgecardxRestCallKey";

    // 1. Master fight list, 2. upcoming fights, 3. my cards, 4. recent
    public static final int NUM_FRAGMENT_SCREENS = 4;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        FightListFragment fightListFragment = new FightListFragment();
        Bundle args = new Bundle();
        args.putInt(REST_CALL_KEY, index);
        fightListFragment.setArguments(args);
        return fightListFragment;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENT_SCREENS;
    }
}
