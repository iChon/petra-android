package com.wuc.jetpack_paging.net;

import com.wuc.jetpack_paging.entity.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author wuc
 * @date 2018/6/27
 */
public interface ApiServiceCore {

    @GET("data/Android/{size}/{page}")
    Call<List<Student>> getData(@Path("size") Integer size, @Path("page") Integer page);

}
