package com.rajohns.judgecardx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.RestClient.*;
import static com.rajohns.judgecardx.NotifyHelper.*;
import static com.rajohns.judgecardx.ObscuredSharedPreferences.*;

public class LoginActivity extends BaseActivity {
    @Inject RestClient restClient;
    @Inject ObscuredSharedPreferences prefs;
    @InjectView(R.id.usernameET) EditText usernameET;
    @InjectView(R.id.passwordET) EditText passwordET;
    @InjectView(R.id.rememberMeSwitch) Switch rememberMeSwitch;

    @InjectViews({R.id.usernameET, R.id.passwordET})
    List<EditText> requiredEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        restoreLoginFieldsIfUserWantsThemRemembered();
    }

    @OnClick(R.id.signInButton) void signIn() {
        if (EditTextUtil.hasEmptyRequiredTextField(requiredEditTexts)) {
            NotifyHelper.showSingleButtonAlert(this, MISSING_LOGIN_FIELD_TITLE, MISSING_LOGIN_FIELD_MSG);
            return;
        }

        saveLoginInfoIfUserWantsItRemembered();

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

    @OnClick(R.id.forgotLoginButton) void forgotLoginTapped() {
        startActivity(new Intent(this, ForgotLoginActivity.class));
    }

    @OnCheckedChanged(R.id.rememberMeSwitch) void onChecked(boolean checked) {
        prefs.edit().putBoolean(REMEMBER_ME_PREF, checked).commit();
        if (!checked) {
            prefs.edit().remove(USERNAME_PREF).commit();
            prefs.edit().remove(PASSWORD_PREF).commit();
        }
    }

    @OnFocusChange(R.id.usernameET) void usernameFocusChanged(View v, boolean hasFocus) {
        if (!hasFocus) {
            KeyboardUtil.hideKeyboard(v, this);
        }
    }

    @OnFocusChange(R.id.passwordET) void passwordFocusChanged(View v, boolean hasFocus) {
        if (!hasFocus) {
            KeyboardUtil.hideKeyboard(v, this);
        }
    }

    private void saveLoginInfoIfUserWantsItRemembered() {
        if (prefs.getBoolean(REMEMBER_ME_PREF, false)) {
            prefs.edit().putString(USERNAME_PREF, usernameET.getText().toString()).commit();
            prefs.edit().putString(PASSWORD_PREF, passwordET.getText().toString()).commit();
        }
    }

    private void restoreLoginFieldsIfUserWantsThemRemembered() {
        if (prefs.getBoolean(REMEMBER_ME_PREF, false)) {
            rememberMeSwitch.setChecked(true);
            usernameET.setText(prefs.getString(USERNAME_PREF, ""));
            passwordET.setText(prefs.getString(PASSWORD_PREF, ""));
        }
    }
}
