package com.rajohns.judgecardx;

import com.google.gson.annotations.SerializedName;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by rajohns on 10/12/14.
 *
 */
public interface JudgecardXClient {
    public static final String BASE_URL = "https://secure3017.hostgator.com/~rajohns/";
    public static final String LOGIN_SUCCESS = "com.judgecard.successfulLogin";
    public static final String LOGIN_FAILURE = "com.judgecard.badCredentials";

    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field("username") String username, @Field("password") String password, Callback<String> callback);
}
