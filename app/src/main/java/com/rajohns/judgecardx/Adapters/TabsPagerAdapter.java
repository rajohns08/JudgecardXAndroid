package com.rajohns.judgecardx.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rajohns.judgecardx.Fragments.MasterFightListFragment;
import com.rajohns.judgecardx.Fragments.MyCardsFragment;
import com.rajohns.judgecardx.Fragments.RecentScorecardsFragment;
import com.rajohns.judgecardx.Fragments.UpcomingFragment;

/**
 * Created by rajohns on 12/7/14.
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new MasterFightListFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new MyCardsFragment();
            case 3:
                return new RecentScorecardsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
