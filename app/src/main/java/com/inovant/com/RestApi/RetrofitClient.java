package com.inovant.com.RestApi;

import com.google.android.gms.common.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "http://admin-dev.get360fit.com/ws/";
    private static Retrofit retrofit = null;
    private static RetrofitClient mInstance;

    private RetrofitClient(){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public  static  synchronized RetrofitClient getInstance(){
        if(mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }
    public APIService getApi(){
        return retrofit.create(APIService.class);
    }
}
