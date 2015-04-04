package com.rajohns.judgecardx.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.rajohns.judgecardx.CustomApplication;

/**
 * Created by rajohns on 10/17/14.
 *
 */
public class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CustomApplication) this.getApplication()).getObjectGraph().inject(this);
    }
}
