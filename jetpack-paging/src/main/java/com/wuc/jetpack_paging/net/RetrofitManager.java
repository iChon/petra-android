package com.wuc.jetpack_paging.net;

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
        final Retrofit retrofit = (new Retrofit.Builder())
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceCore.class);
    }

    public ApiServiceCore getApiService() {
        return apiService;
    }

}
