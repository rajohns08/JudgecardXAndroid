package com.rajohns.judgecardx;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

import static com.rajohns.judgecardx.JudgecardXClient.BASE_URL;

/**
 * Created by rajohns on 10/19/14.
 *
 */
@Module (injects = LoginActivity.class)
public class ApplicationModule {
    @Provides @Singleton
    public JudgecardXClient provideJudgecardXClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
       return restAdapter.create(JudgecardXClient.class);
    }
}
