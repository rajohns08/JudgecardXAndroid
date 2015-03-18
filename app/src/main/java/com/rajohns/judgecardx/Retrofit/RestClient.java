package com.rajohns.judgecardx.Retrofit;

import com.google.gson.JsonElement;

import java.sql.Date;

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
    public static final String SCORECARD_CREATED = "com.judgecard.scorecardCreated";
    public static final String SCORECARD_ALREADY_EXISTS = "com.judgecard.scorecardAlreadyExists";
    public static final String DELETE_SUCCESS = "com.judgecard.scorecardDeleted";
    public static final String DELETE_FAILED = "com.judgecard.deleteFailed";
    public static final String REQUEST_SUCCESS = "com.judgecard.emailsent";

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";
    public static final String FIGHTER1_KEY = "fighter1";
    public static final String FIGHTER2_KEY = "fighter2";
    public static final String FIGHT_DATE_KEY = "date";
    public static final String FIGHT_FIGHT_DATE_KEY = "fightdate";
    public static final String ROUNDS_KEY = "rounds";
    public static final String SCORECARD_ID_KEY = "id";

    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field(USERNAME_KEY) String username,
               @Field(PASSWORD_KEY) String password,
               Callback<String> callback);

    @FormUrlEncoded
    @POST("/forgotLoginSSL.php")
    void sendEmail(@Field(EMAIL_KEY) String email, Callback<String> callback);

    @FormUrlEncoded
    @POST("/signup.php")
    void signup(@Field(USERNAME_KEY) String username,
                @Field(EMAIL_KEY) String email,
                @Field(PASSWORD_KEY) String password,
                Callback<String> callback);

    @GET("/fightList.php")
    void getMasterFightList(Callback<JsonElement> callback);

    @GET("/upcomingFights.php")
    void getUpcomingFights(Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/myCards.php")
    void getMyCards(@Field(USERNAME_KEY) String username,
                    Callback<JsonElement> callback);

    @GET("/recentScorecards.php")
    void getRecentScorecards(Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/fetchScorecard.php")
    void getScorecardDetail(@Field(USERNAME_KEY) String username,
                            @Field(FIGHTER1_KEY) String fighter1,
                            @Field(FIGHTER2_KEY) String fighter2,
                            Callback<JsonElement> callback);

    /* ******************************************************************* */
    @FormUrlEncoded
    @POST("/createOrUpdateScorecard.php")
    void createOrUpdateScorecard(@Field(USERNAME_KEY) String username,
                                 @Field(FIGHTER1_KEY) String fighter1,
                                 @Field(FIGHTER2_KEY) String fighter2,
                                 @Field(FIGHT_DATE_KEY) java.sql.Date fightDate,
                                 @Field(ROUNDS_KEY) String rounds,
                                 @Field("f1r1") String f1r1,
                                 @Field("f1r2") String f1r2,
                                 @Field("f1r3") String f1r3,
                                 @Field("f1r4") String f1r4,
                                 @Field("f1r5") String f1r5,
                                 @Field("f1r6") String f1r6,
                                 @Field("f1r7") String f1r7,
                                 @Field("f1r8") String f1r8,
                                 @Field("f1r9") String f1r9,
                                 @Field("f1r10") String f1r10,
                                 @Field("f1r11") String f1r11,
                                 @Field("f1r12") String f1r12,
                                 @Field("f1r13") String f1r13,
                                 @Field("f1r14") String f1r14,
                                 @Field("f1r15") String f1r15,
                                 @Field("f2r1") String f2r1,
                                 @Field("f2r2") String f2r2,
                                 @Field("f2r3") String f2r3,
                                 @Field("f2r4") String f2r4,
                                 @Field("f2r5") String f2r5,
                                 @Field("f2r6") String f2r6,
                                 @Field("f2r7") String f2r7,
                                 @Field("f2r8") String f2r8,
                                 @Field("f2r9") String f2r9,
                                 @Field("f2r10") String f2r10,
                                 @Field("f2r11") String f2r11,
                                 @Field("f2r12") String f2r12,
                                 @Field("f2r13") String f2r13,
                                 @Field("f2r14") String f2r14,
                                 @Field("f2r15") String f2r15,
                                 Callback<JsonElement> callback);
    /* ******************************************************************* */

    @FormUrlEncoded
    @POST("/deleteScorecardNew.php")
    void deleteScorecard(@Field(SCORECARD_ID_KEY) String scorecardId,
                         @Field(USERNAME_KEY) String username,
                         Callback<String> callback);

    @FormUrlEncoded
    @POST("/getAverageScorecard.php")
    void getAvgScorecard(@Field(FIGHTER1_KEY) String fighter1,
                         @Field(FIGHTER2_KEY) String fighter2,
                         Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/createPrivateScorecard.php")
    void createPrivateScorecard(@Field(FIGHTER1_KEY) String fighter1,
                                @Field(FIGHTER2_KEY) String fighter2,
                                @Field(ROUNDS_KEY) String rounds,
                                @Field(FIGHT_DATE_KEY) Date date,
                                @Field(USERNAME_KEY) String username,
                                Callback<String> callback);

    @FormUrlEncoded
    @POST("/requestScorecardNew.php")
    void requestPublicScorecard(@Field(FIGHTER1_KEY) String fighter1,
                                @Field(FIGHTER2_KEY) String fighter2,
                                @Field(ROUNDS_KEY) String rounds,
                                @Field(FIGHT_FIGHT_DATE_KEY) String date,
                                @Field(USERNAME_KEY) String username,
                                Callback<String> callback);
}
