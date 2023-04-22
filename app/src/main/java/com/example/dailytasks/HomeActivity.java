package com.example.dailytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dailytasks.adapters.TasksAdapter;
import com.example.dailytasks.models.TaskModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<TaskModel> taskList = new ArrayList<TaskModel>();
        taskList.add(new TaskModel("001", "Task 1", new Date(), true));
        taskList.add(new TaskModel("002", "Task 2", new Date(), false));
        taskList.add(new TaskModel("003", "Task 3", new Date(), true));
        TasksAdapter myAdapter = new TasksAdapter(this, taskList);

        recyclerView.setAdapter(myAdapter);

    }
}