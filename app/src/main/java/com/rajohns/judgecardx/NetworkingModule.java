package com.rajohns.judgecardx;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

import static com.rajohns.judgecardx.RestClient.BASE_URL;

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
        MasterFightListActivity.class
    }
)

public class NetworkingModule {
    @Provides @Singleton RestClient provideJudgecardXClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
       return restAdapter.create(RestClient.class);
    }
}
