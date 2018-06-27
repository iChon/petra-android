package com.wuc.jetpack_paging.datasource;


import android.arch.paging.TiledDataSource;

import com.wuc.jetpack_paging.entity.Student;
import com.wuc.jetpack_paging.net.RetrofitManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuc
 * @date 2018/6/27
 */
public class StudentDataSource extends TiledDataSource<Student> {

    @Override
    public int countItems() {
        return 1000;
    }

    @Override
    public List<Student> loadRange(int startPosition, int count) {
        try {
            return RetrofitManager.getInstance().getApiService().getData(count, startPosition / count - 1).execute().body();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}
