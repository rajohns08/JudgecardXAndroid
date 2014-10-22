package com.rajohns.judgecardx;

import android.app.Activity;
import android.os.Bundle;

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
