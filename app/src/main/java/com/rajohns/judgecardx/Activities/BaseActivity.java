package com.rajohns.judgecardx.Activities;

import android.app.Activity;
import android.os.Bundle;

import com.rajohns.judgecardx.CustomApplication;

/**
 * Created by rajohns on 10/17/14.
 *
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CustomApplication) this.getApplication()).getObjectGraph().inject(this);
    }
}
