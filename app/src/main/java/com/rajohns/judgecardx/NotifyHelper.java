package com.rajohns.judgecardx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by rajohns on 10/13/14.
 *
 */
public class NotifyHelper {
//    public static final String BAD_CREDENTIALS_TITLE = "Invalid";
//    public static final String BAD_CREDENTIALS_MSG = "Invalid username or password";
//    public static final String GENERIC_ERROR_TITLE = "Unknown Error";
//    public static final String GENERIC_ERROR_MSG = "An unknown error has occurred. Please try again later.";
//    public static final String MISSING_LOGIN_FIELD_TITLE = "Enter Login Info";
//    public static final String MISSING_LOGIN_FIELD_MSG = "Please enter both your username and your password to login.";
//    public static final String MISSING_EMAIL_FORGOT_TITLE = "Email Missing";
//    public static final String MISSING_EMAIL_FORGOT_MSG = "Please enter your email address";
//    public static final String EMAIL_SENT_TITLE = "Email Sent";
//    public static final String EMAIL_SENT_MSG = "An email has been sent to your address. You have 1 hour to reset your password.";

    private static ProgressDialog loadingDialog;
    private static AlertDialog alertDialog;

    public static void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(context);
            loadingDialog.setTitle("Loading");
            loadingDialog.show();
        }
    }

    public static void hideLoading() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }

    public static void showSingleButtonAlert(Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
