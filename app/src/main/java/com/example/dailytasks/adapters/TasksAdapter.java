package com.example.dailytasks.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytasks.R;
import com.example.dailytasks.models.TaskModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {
    List<TaskModel> taskModelList;
    private Context context;
    private static OnDeleteButtonClickListener onDeleteButtonClickListener;
    private static OnDoneButtonClickListener onDoneButtonClickListener;

    public TasksAdapter(Context context, List<TaskModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName;
        private TaskModel taskModel;
        private final TextView taskDate;
        private final TextView deleteButton;
        private final TextView doneButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskDate = itemView.findViewById(R.id.task_date);
            deleteButton = itemView.findViewById(R.id.delete_button);
            doneButton = itemView.findViewById(R.id.done_button);
        }

        void setName(String value) {
            taskName.setText(value);
        }

        void setDate(Date value) {
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            taskDate.setText(formatter.format(value));
        }

        void setStatus(boolean status) {
            if (status) {
                taskName.setPaintFlags(taskDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                taskDate.setPaintFlags(taskDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                doneButton.setText("Reopen");
            }
        }

        void setTaskModel(TaskModel taskModel) {

            this.taskModel = taskModel;
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
        holder.setDate(taskModel.date);
        holder.setTaskModel(taskModel);
        holder.setStatus(taskModel.status);
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteButtonClickListener != null) {
                onDeleteButtonClickListener.onDeleteButtonClick(taskModel);
            }
        });
        holder.doneButton.setOnClickListener(v -> {
            if (onDoneButtonClickListener != null) {
                onDoneButtonClickListener.onDoneButtonClick(taskModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public void setOnDeleteListener(OnDeleteButtonClickListener onDeleteListener) {
        TasksAdapter.onDeleteButtonClickListener = onDeleteListener;
    }

    public void setOnDoneListener(OnDoneButtonClickListener onDoneListener) {
        TasksAdapter.onDoneButtonClickListener = onDoneListener;
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(TaskModel taskModel);
    }

    public interface OnDoneButtonClickListener {
        void onDoneButtonClick(TaskModel taskModel);
    }

}
