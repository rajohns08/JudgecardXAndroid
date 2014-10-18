package com.rajohns.judgecardx;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by rajohns on 10/17/14.
 *
 */
public class BaseActivity extends Activity {
    protected JudgecardXClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CustomApplication) this.getApplication()).initRestClient();
        restClient = ((CustomApplication) this.getApplication()).getRestClient();
    }
}
