package com.rajohns.judgecardx;

import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by rajohns on 11/11/14.
 *
 */
public class SignUpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
    }
}
