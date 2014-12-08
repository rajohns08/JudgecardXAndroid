package com.rajohns.judgecardx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
