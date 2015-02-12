package com.rajohns.judgecardx.Dagger;

import android.content.Context;

import com.rajohns.judgecardx.Activities.LoginActivity;
import com.rajohns.judgecardx.Fragments.FightListFragment;
import com.rajohns.judgecardx.Utils.ObscuredSharedPreferences;

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
        LoginActivity.class,
        FightListFragment.class
    }
)

public class AndroidModule {
    private Context context;

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    ObscuredSharedPreferences provideSharedPreferences() {
        return new ObscuredSharedPreferences(context, context.getSharedPreferences("com.rajohns.judgecardx", Context.MODE_PRIVATE));
    }
}
