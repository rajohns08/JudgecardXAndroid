package com.rajohns.judgecardx;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by rajohns on 10/17/14.
 *
 */
public class CustomApplication extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new NetworkingModule(), new AndroidModule(getApplicationContext()));
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
