package com.rajohns.judgecardx.Activities;

import android.app.Activity;
import android.os.Bundle;

import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;

import javax.inject.Inject;

/**
 * Created by rajohns on 3/9/15.
 *
 */
public class CreatePrivateScorecardActivity extends BaseActivity {
    @Inject RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private);
    }
}
