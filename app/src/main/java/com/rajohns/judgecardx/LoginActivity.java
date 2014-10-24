package com.rajohns.judgecardx;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.RestClient.*;
import static com.rajohns.judgecardx.NotifyHelper.*;

public class LoginActivity extends BaseActivity {
    @Inject RestClient restClient;
    @InjectView(R.id.usernameET) EditText usernameET;
    @InjectView(R.id.passwordET) EditText passwordET;

    @OnClick(R.id.signInButton) void signIn() {
        NotifyHelper.showLoading(LoginActivity.this);
        restClient.login(usernameET.getText().toString(), passwordET.getText().toString(), new Callback<String>() {
            @Override
            public void success(String responseString, Response response) {
                NotifyHelper.hideLoading();
                if (responseString.equals(LOGIN_SUCCESS)) {
                }
                else if (responseString.equals(LOGIN_FAILURE)) {
                    NotifyHelper.showSingleButtonAlert(LoginActivity.this, BAD_CREDENTIALS_TITLE, BAD_CREDENTIALS_MSG);
                }
                else {
                    NotifyHelper.showSingleButtonAlert(LoginActivity.this, GENERIC_ERROR_TITLE, GENERIC_ERROR_MSG);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                NotifyHelper.hideLoading();
                NotifyHelper.showSingleButtonAlert(LoginActivity.this, GENERIC_ERROR_TITLE, GENERIC_ERROR_MSG);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }
}
