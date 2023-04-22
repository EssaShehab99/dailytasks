package com.example.dailytasks.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytasks.R;
import com.example.dailytasks.models.TaskModel;

import java.util.Date;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {
    List<TaskModel> taskModelList;
    private Context context;
    private static ClickListener clickListener;

    public TasksAdapter(Context context, List<TaskModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView taskName;
        private final TextView taskDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            taskName = itemView.findViewById(R.id.task_name);
            taskDate = itemView.findViewById(R.id.task_date);
        }


        @Override
        public void onClick(View v) {
            try {
                clickListener.onItemClick(getAdapterPosition(), v);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        void setName(String value) {
            taskName.setText(value);
        }

        void setDaysTV(Date value) {

          taskDate.setText(String.valueOf(value.getDate()-((new Date(System.currentTimeMillis())).getDate())));
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        return new MyViewHolder(listViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel taskModel = taskModelList.get(position);
        holder.setName(taskModel.name);
        holder.setDaysTV(taskModel.date);
//        holder.setProgressBar(task.progress);
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TasksAdapter.clickListener = clickListener;
    }
}
