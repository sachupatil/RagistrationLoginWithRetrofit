package com.inovant.com.RestApi;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {



    @FormUrlEncoded
    @POST("user-register")
    Call<User> createUser(
            @Field("app_version") String app_version,
            @Field("device_model") String device_model,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("email") String email,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("os_version") String os_version,
            @Field("password") String password,
            @Field("referral_code") String referral_code);

    @FormUrlEncoded
    @POST("login?lang=en")
    Call<User> getLogin(
            @Field("email") String email,
            @Field("password") String password);

}
