package com.wuc.jetpack_paging.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wuc
 * @date 2018/6/27
 */
public class RetrofitManager {

    private static final RetrofitManager ourInstance = new RetrofitManager();
    private final ApiServiceCore apiService;

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder().addInterceptor(new HttpLoggingInterceptor()).build())
                .build();
        apiService = retrofit.create(ApiServiceCore.class);
    }

    public ApiServiceCore getApiService() {
        return apiService;
    }

}
