package com.rajohns.judgecardx;

import com.google.gson.annotations.SerializedName;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rajohns on 10/12/14.
 *
 */
public interface JudgecardXClient {
    public static final String BASE_URL = "https://secure3017.hostgator.com/~rajohns/";

    @GET("/login.php")
    void login(@Query("username") String username, @Query("password") String password, Callback<String> callback);
}
