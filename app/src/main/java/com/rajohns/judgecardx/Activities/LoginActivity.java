package com.rajohns.judgecardx.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.rajohns.judgecardx.Utils.NotifyHelper;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Utils.EditTextUtil;
import com.rajohns.judgecardx.Utils.KeyboardUtil;

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

import static com.rajohns.judgecardx.Retrofit.RestClient.*;
import static com.rajohns.judgecardx.Utils.ObscuredSharedPreferences.*;

public class LoginActivity extends BaseActivity {
    @Inject
    RestClient restClient;
    @Inject
    ObscuredSharedPreferences prefs;
    @InjectView(R.id.usernameET) EditText usernameET;
    @InjectView(R.id.passwordET) EditText passwordET;
    @InjectView(R.id.rememberMeSwitch) SwitchCompat rememberMeSwitch;

    @InjectViews({R.id.usernameET, R.id.passwordET})
    List<EditText> requiredEditTexts;

    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);

        ButterKnife.inject(this);
        restoreLoginFieldsIfUserWantsThemRemembered();
        getSupportActionBar().setTitle("Sign In");
    }

    @OnClick(R.id.signInButton) void signIn() {
        if (EditTextUtil.hasEmptyRequiredTextField(requiredEditTexts)) {
            NotifyHelper.showSingleButtonAlert(this, getResources().getString(R.string.missing_login_field_title), getResources().getString(R.string.missing_login_field_msg));
            return;
        }

        prefs.edit().putString(USERNAME_PREF, usernameET.getText().toString()).commit();
        savePasswordInfoIfUserWantsItRemembered();

        NotifyHelper.showLoading(progressWheel);
        restClient.login(usernameET.getText().toString(), passwordET.getText().toString(), new Callback<String>() {
            @Override
            public void success(String responseString, Response response) {
                NotifyHelper.hideLoading(progressWheel);
                if (responseString.equals(LOGIN_SUCCESS)) {
                    startActivity(new Intent(LoginActivity.this, FightListsContainerActivity.class));
                } else if (responseString.equals(LOGIN_FAILURE)) {
                    NotifyHelper.showSingleButtonAlert(LoginActivity.this, getResources().getString(R.string.bad_credentials_title), getResources().getString(R.string.bad_credentials_msg));
                } else {
                    NotifyHelper.showSingleButtonAlert(LoginActivity.this, getResources().getString(R.string.generic_error_title), getResources().getString(R.string.generic_error_msg));
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                NotifyHelper.hideLoading(progressWheel);
                NotifyHelper.showSingleButtonAlert(LoginActivity.this, getResources().getString(R.string.generic_error_title), getResources().getString(R.string.generic_error_msg));
            }
        });
    }

    @OnClick(R.id.forgotLoginButton) void forgotLoginTapped() {
        startActivity(new Intent(this, ForgotLoginActivity.class));
    }

    @OnClick(R.id.signUpButton) void signUpTapped() {
        startActivity(new Intent(this, SignUpActivity.class));
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

    private void savePasswordInfoIfUserWantsItRemembered() {
        if (prefs.getBoolean(REMEMBER_ME_PREF, false)) {
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
