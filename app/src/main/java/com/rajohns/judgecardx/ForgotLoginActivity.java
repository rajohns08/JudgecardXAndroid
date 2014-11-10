package com.rajohns.judgecardx;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnFocusChange;

/**
 * Created by rajohns on 10/25/14.
 *
 */
public class ForgotLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotlogin);
        ButterKnife.inject(this);
    }

    @OnFocusChange(R.id.emailET) void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            KeyboardUtil.hideKeyboard(v, this);
        }
    }
}
