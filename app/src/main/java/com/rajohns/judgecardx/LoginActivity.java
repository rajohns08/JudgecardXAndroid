package com.rajohns.judgecardx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.JudgecardXClient.*;
import static com.rajohns.judgecardx.NotifyHelper.*;

public class LoginActivity extends Activity {
    public EditText usernameET;
    public EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText)findViewById(R.id.usernameET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        final JudgecardXClient restClient = new RestAdapter.Builder().setEndpoint(BASE_URL).build().create(JudgecardXClient.class);

        Button signInButton = (Button)findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }
}
