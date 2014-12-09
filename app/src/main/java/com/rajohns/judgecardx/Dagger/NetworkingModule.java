package com.rajohns.judgecardx.Dagger;

import com.rajohns.judgecardx.Activities.FightListsContainerActivity;
import com.rajohns.judgecardx.Activities.ForgotLoginActivity;
import com.rajohns.judgecardx.Activities.LoginActivity;
import com.rajohns.judgecardx.Fragments.MasterFightListFragment;
import com.rajohns.judgecardx.Fragments.UpcomingFragment;
import com.rajohns.judgecardx.Retrofit.RestClient;
import com.rajohns.judgecardx.Activities.SignUpActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

import static com.rajohns.judgecardx.Retrofit.RestClient.BASE_URL;

/**
 * Created by rajohns on 10/19/14.
 *
 */
@Module (
    includes = {
        AndroidModule.class
    },
    injects = {
        LoginActivity.class,
        ForgotLoginActivity.class,
        SignUpActivity.class,
        FightListsContainerActivity.class,
        MasterFightListFragment.class,
        UpcomingFragment.class
    }
)

public class NetworkingModule {
    @Provides @Singleton
    RestClient provideJudgecardXClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
       return restAdapter.create(RestClient.class);
    }
}
