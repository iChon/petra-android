package com.wuc.jetpack_paging.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuc.jetpack_paging.R;
import com.wuc.jetpack_paging.entity.Student;

/**
 * @author wuc
 * @date 2018/6/27
 */
public class StudentAdapter extends PagedListAdapter<Student, StudentAdapter.VH> {

    private static DiffUtil.ItemCallback<Student> diffCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return oldItem.equals(newItem);
        }
    };

    public StudentAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_student, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(getItem(position));
    }

    public class VH extends RecyclerView.ViewHolder {

        private final TextView tvContent;

        public VH(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bind(Student s) {
            tvContent.setText(s.getName());
        }

    }

}
