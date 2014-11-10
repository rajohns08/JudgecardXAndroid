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
public interface RestClient {
    public static final String BASE_URL = "https://secure3017.hostgator.com/~rajohns/";
    public static final String LOGIN_SUCCESS = "com.judgecard.successfulLogin";
    public static final String LOGIN_FAILURE = "com.judgecard.badCredentials";
    public static final String FORGOT_LOGIN_SUCCESS = "com.judgecard.sendingEmail";
    public static final String EMAIL_NOT_FOUND = "com.judgecard.userNotFound";

    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field("username") String username, @Field("password") String password, Callback<String> callback);

    @FormUrlEncoded
    @POST("/forgotLoginSSL.php")
    void sendEmail(@Field("email") String email, Callback<String> callback);
}
