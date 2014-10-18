package com.rajohns.judgecardx;

import android.app.Application;

import retrofit.RestAdapter;

import static com.rajohns.judgecardx.JudgecardXClient.BASE_URL;

/**
 * Created by rajohns on 10/17/14.
 *
 */
public class CustomApplication extends Application {
    private JudgecardXClient restClient = null;

    public JudgecardXClient getRestClient() {
        return restClient;
    }

    public void initRestClient() {
        if (restClient == null) {
            restClient = new RestAdapter.Builder().setEndpoint(BASE_URL).build().create(JudgecardXClient.class);
        }
    }
}
