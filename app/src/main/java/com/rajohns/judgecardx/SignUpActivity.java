package com.rajohns.judgecardx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.rajohns.judgecardx.RestClient.*;

/**
 * Created by rajohns on 11/11/14.
 *
 */
public class SignUpActivity extends BaseActivity {
    @Inject RestClient restClient;
    @InjectView(R.id.emailET) EditText emailET;
    @InjectView(R.id.usernameET) EditText usernameET;
    @InjectView(R.id.passwordET) EditText passwordET;
    @InjectView(R.id.confirmPasswordET) EditText confirmPasswordET;

    @InjectViews({R.id.usernameET, R.id.emailET, R.id.passwordET, R.id.confirmPasswordET})
    List<EditText> requiredEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.signUpButton) void signupTapped() {
        if (EditTextUtil.hasEmptyRequiredTextField(requiredEditTexts)) {
            NotifyHelper.showSingleButtonAlert(this, getResources().getString(R.string.missing_required_field_title), getResources().getString(R.string.missing_required_field_msg));
            return;
        }

        if (!passwordET.getText().toString().equals(confirmPasswordET.getText().toString())) {
            NotifyHelper.showSingleButtonAlert(this, getResources().getString(R.string.mismatched_passwords_title), getResources().getString(R.string.mismatched_passwords_msg));
            return;
        }

        NotifyHelper.showLoading(this);
        restClient.signup(usernameET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString(), new Callback<String>() {
            @Override
            public void success(String responseString, Response response) {
                NotifyHelper.hideLoading();
                if (responseString.equals(SIGNUP_SUCCESS)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
                    alertDialogBuilder.setTitle(getResources().getString(R.string.success));
                    alertDialogBuilder.setMessage(getResources().getString(R.string.signup_success_msg));
                    alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if (responseString.equals(SIGNUP_FAILURE)) {
                    NotifyHelper.showSingleButtonAlert(SignUpActivity.this, getResources().getString(R.string.already_exists_title), getResources().getString(R.string.already_exists_msg));
                }
                else {
                    NotifyHelper.showSingleButtonAlert(SignUpActivity.this, getResources().getString(R.string.unknownErrorTitle), getResources().getString(R.string.unknownErrorMsg));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                NotifyHelper.hideLoading();
            }
        });
    }
}
