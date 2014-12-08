package com.rajohns.judgecardx.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 12/7/14.
 *
 */
public class UpcomingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upcoming_fights, container, false);
        return rootView;
    }
}
