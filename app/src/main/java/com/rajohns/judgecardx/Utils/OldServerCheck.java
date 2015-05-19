package com.rajohns.judgecardx.Utils;

import android.content.Context;

import com.google.gson.JsonElement;
import com.rajohns.judgecardx.R;
import com.rajohns.judgecardx.Retrofit.RestClient;

/**
 * Created by rajohns on 5/18/15.
 *
 */
public class OldServerCheck {

    public static boolean isOldServer(Context context, JsonElement jsonElement) {
        try {
            if (jsonElement.getAsString().equals(RestClient.OLD_SERVER)) {
                showOldServerMessage(context);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isOldServer(Context context, String string) {
        if (string.equals(RestClient.OLD_SERVER)) {
            showOldServerMessage(context);
            return true;
        } else {
            return false;
        }
    }

    private static void showOldServerMessage(Context context) {
        NotifyHelper.showSingleButtonAlert(context, context.getResources().getString(R.string.updateTitle), context.getResources().getString(R.string.updateMessage));
    }

}
