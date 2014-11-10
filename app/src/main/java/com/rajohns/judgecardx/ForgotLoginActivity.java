package com.rajohns.judgecardx;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.RestClient.*;
import static com.rajohns.judgecardx.NotifyHelper.*;

/**
 * Created by rajohns on 10/25/14.
 *
 */
public class ForgotLoginActivity extends BaseActivity {
    @Inject RestClient restClient;
    @InjectView(R.id.emailET) EditText emailET;

    @InjectViews({R.id.emailET})
    List<EditText> requiredEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotlogin);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.emailMeButton) void emailMe() {
        if (EditTextUtil.hasEmptyRequiredTextField(requiredEditTexts)) {
            NotifyHelper.showSingleButtonAlert(this, MISSING_EMAIL_FORGOT_TITLE, MISSING_EMAIL_FORGOT_MSG);
            return;
        }

        // service call
        NotifyHelper.showLoading(this);
        restClient.sendEmail(emailET.getText().toString(), new Callback<String>() {
            @Override
            public void success(String responseString, Response response) {
                NotifyHelper.hideLoading();
                if (responseString.equals(FORGOT_LOGIN_SUCCESS)) {
                    NotifyHelper.showSingleButtonAlert(ForgotLoginActivity.this, EMAIL_SENT_TITLE, EMAIL_SENT_MSG);
                }
                else if (responseString.equals(EMAIL_NOT_FOUND)) {
                    NotifyHelper.showSingleButtonAlert(ForgotLoginActivity.this, getResources().getString(R.string.emailNotFoundTitle), getResources().getString(R.string.emailNotFoundMsg));
                }
                else {
                    NotifyHelper.showSingleButtonAlert(ForgotLoginActivity.this, getResources().getString(R.string.unknownErrorTitle), getResources().getString(R.string.unknownErrorMsg));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
            }
        });

    }

    @OnFocusChange(R.id.emailET) void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            KeyboardUtil.hideKeyboard(v, this);
        }
    }
}
