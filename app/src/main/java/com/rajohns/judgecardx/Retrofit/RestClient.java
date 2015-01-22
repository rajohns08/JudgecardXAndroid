package com.rajohns.judgecardx.Retrofit;

import com.google.gson.JsonElement;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by rajohns on 10/12/14.
 *
 */
public interface RestClient {
    public static final String BASE_URL = "https://secure3017.hostgator.com/~rajohns/";
    public static final String LOGIN_SUCCESS = "com.judgecard.successfulLogin";
    public static final String LOGIN_FAILURE = "com.judgecard.badCredentials";
    public static final String FORGOT_LOGIN_SUCCESS = "com.judgecard.sendingEmail";
    public static final String EMAIL_NOT_FOUND = "com.judgecard.userNotFound";
    public static final String SIGNUP_SUCCESS = "com.judgecard.userAvailable";
    public static final String SIGNUP_FAILURE = "com.judgecard.userAlreadyExists";

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";
    public static final String FIGHTER1_KEY = "fighter1";
    public static final String FIGHTER2_KEY = "fighter2";

    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field(USERNAME_KEY) String username, @Field(PASSWORD_KEY) String password, Callback<String> callback);

    @FormUrlEncoded
    @POST("/forgotLoginSSL.php")
    void sendEmail(@Field(EMAIL_KEY) String email, Callback<String> callback);

    @FormUrlEncoded
    @POST("/signup.php")
    void signup(@Field(USERNAME_KEY) String username, @Field(EMAIL_KEY) String email, @Field(PASSWORD_KEY) String password, Callback<String> callback);

    @GET("/fightList.php")
    void getMasterFightList(Callback<JsonElement> callback);

    @GET("/upcomingFights.php")
    void getUpcomingFights(Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/myCards.php")
    void getMyCards(@Field(USERNAME_KEY) String username, Callback<JsonElement> callback);

    @GET("/recentScorecards.php")
    void getRecentScorecards(Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/fetchScorecard.php")
    void getScorecardDetail(@Field(USERNAME_KEY) String username, @Field(FIGHTER1_KEY) String fighter1, @Field(FIGHTER2_KEY) String fighter2, Callback<JsonElement> callback);
}
