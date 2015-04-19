package com.rajohns.judgecardx.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 10/13/14.
 *
 */
public class NotifyHelper {
    private static ProgressWheel loadingDialog;
//    private static ProgressDialog loadingDialog;
    private static AlertDialog alertDialog;

    public static void showLoading(ProgressWheel progressWheel) {

        progressWheel.spin();
        progressWheel.setVisibility(View.VISIBLE);

//        if (loadingDialog == null) {
//            loadingDialog = new ProgressWheel(context);
//            loadingDialog.setBarColor(Color.GREEN);
//            loadingDialog.spin();

//            loadingDialog = new ProgressDialog(context);
//            loadingDialog.setTitle("Loading");
//            loadingDialog.show();
//        }
    }

    public static void hideLoading(ProgressWheel progressWheel) {
        progressWheel.stopSpinning();
        progressWheel.setVisibility(View.GONE);
//        if (loadingDialog != null) {
////            loadingDialog.dismiss();
//            loadingDialog.stopSpinning();
//            loadingDialog = null;
//        }
    }

    public static void showSingleButtonAlert(Context context, String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.ok)
                .show();
    }

    public static void showConfirmAlert(Context context, String title, String message, String actionButtonTitle, final Handler.Callback callback) {
        new AlertDialogWrapper.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(actionButtonTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        callback.handleMessage(null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public static void showGeneralErrorMsg(Context context) {
        showSingleButtonAlert(context, "Unknown Error", "An unknown error occurred");
    }
}
