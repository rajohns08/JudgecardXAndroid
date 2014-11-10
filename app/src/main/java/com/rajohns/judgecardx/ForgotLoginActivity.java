package com.rajohns.judgecardx;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by rajohns on 10/25/14.
 *
 */
public class ForgotLoginActivity extends BaseActivity {
    ArrayList<EditText> requiredEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotlogin);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.emailMeButton) void emailMe() {

    }

    @OnFocusChange(R.id.emailET) void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            KeyboardUtil.hideKeyboard(v, this);
        }
    }
}
