package com.rajohns.judgecardx.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rajohns.judgecardx.Fragments.FightListFragment;

import java.util.HashMap;

/**
 * Created by rajohns on 12/7/14.
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public static final String REST_CALL_KEY = "judgecardxRestCallKey";
    public static HashMap<Integer, FightListFragment> listFragments = new HashMap<>();

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
        listFragments.put(index, fightListFragment);
        return fightListFragment;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENT_SCREENS;
    }

    public static FightListFragment getFragment(int index) {
        return listFragments.get(index);
    }
}
