package com.rajohns.judgecardx.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 10/13/14.
 *
 */
public class NotifyHelper {
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public static void showSingleButtonAlert(Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showGeneralErrorMsg(Context context) {
        showSingleButtonAlert(context, "Unknown Error", "An unknown error occurred");
    }
}
