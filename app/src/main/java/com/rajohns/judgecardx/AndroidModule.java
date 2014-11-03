package com.rajohns.judgecardx;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rajohns on 11/2/14.
 *
 */

@Module (
    complete = false,
    injects = {
        LoginActivity.class
    }
)

public class AndroidModule {
    private Context context;
    public String SHARED_PREFS_USERNAME_KEY = "com.rajohns.judgecardx.username";
    public String SHARED_PREFS_PASSWORD_KEY = "com.rajohns.judgecardx.password";

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton ObscuredSharedPreferences provideSharedPreferences() {
        return new ObscuredSharedPreferences(context, context.getSharedPreferences("com.rajohns.judgecardx", Context.MODE_PRIVATE));
    }
}
