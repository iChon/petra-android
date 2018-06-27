package com.wuc.jetpack_paging;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wuc.jetpack_paging.adapter.StudentAdapter;
import com.wuc.jetpack_paging.datasource.StudentDataSource;
import com.wuc.jetpack_paging.entity.Student;

/**
 * @author wuc
 * @date 2018/6/27
 */
public class PagingExampleActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging_example);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();

        LivePagedListBuilder<Integer, Student> builder = new LivePagedListBuilder<>(
                new DataSource.Factory<Integer, Student>() {
                    @Override
                    public DataSource<Integer, Student> create() {
                        return new StudentDataSource();
                    }
                }, config);

        StudentAdapter adapter = new StudentAdapter();
        adapter.submitList(builder.build().getValue());
        recyclerView.setAdapter(adapter);

    }

}
