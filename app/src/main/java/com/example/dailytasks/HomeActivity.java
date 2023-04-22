package com.example.dailytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dailytasks.adapters.TasksAdapter;
import com.example.dailytasks.models.TaskModel;
import com.example.dailytasks.pdo.TasksPDO;
import com.example.dailytasks.pdo.TaskCallback;
import com.example.dailytasks.pdo.UserPDO;
import com.example.dailytasks.shared.Shared;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TasksAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            startActivity(new Intent(this, AddTaskActivity.class));

        });
        fetchNews();
//        UserPDO.user.email="essashehab99@gmail.com";
    }

    void fetchNews() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("users").document(UserPDO.user.email).collection("tasks");

        ref.addSnapshotListener((snapshots, e) -> {

            if (e != null) {
                Shared.showSnackBar(findViewById(android.R.id.content), e.getCode() + "|" + e.getMessage());
                return;
            }

            try {
                assert snapshots != null;
                for (DocumentChange dc : snapshots.getDocumentChanges()) {

                    switch (dc.getType()) {
                        case ADDED:
                            TasksPDO.taskModelList.add(TaskModel.fromMap(dc.getDocument().getData(), dc.getDocument().getId()));
                            break;
                        case MODIFIED:
                            TasksPDO.taskModelList.remove(indexOf(dc.getDocument().getId()));
                            TasksPDO.taskModelList.add(TaskModel.fromMap(dc.getDocument().getData(), dc.getDocument().getId()));
                            break;
                        case REMOVED:
                            TasksPDO.taskModelList.remove(indexOf(dc.getDocument().getId()));
                            break;
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    myAdapter = new TasksAdapter(this, TasksPDO.taskModelList);
                    myAdapter.setOnDeleteListener(this::delete);
                    myAdapter.setOnDoneListener(this::update);
                    recyclerView.setAdapter(myAdapter);
                }
            } catch (Exception exception) {
                Shared.showSnackBar(findViewById(android.R.id.content), exception.getMessage());
            }

        });
    }

    int indexOf(String docId) {
        for (int i = 0; i < TasksPDO.taskModelList.size(); i++) {
            if (TasksPDO.taskModelList.get(i).id.equals(docId))
                return TasksPDO.taskModelList.indexOf(TasksPDO.taskModelList.get(i));
        }
        return 0;
    }

    void update(TaskModel taskModel) {
        taskModel.status = !taskModel.status;
        TasksPDO.update(taskModel, new TaskCallback() {
            @Override
            public void onSuccess(boolean success) {
                Shared.showSnackBar(findViewById(android.R.id.content), "task updated");
            }

            @Override
            public void onFailure(Exception e) {
                Shared.showSnackBar(findViewById(android.R.id.content), e.getMessage());
            }
        });
    }
    void delete(TaskModel taskModel) {
        TasksPDO.delete(taskModel.id, new TaskCallback() {
            @Override
            public void onSuccess(boolean success) {
                Shared.showSnackBar(findViewById(android.R.id.content), "task Deleted");
            }

            @Override
            public void onFailure(Exception e) {
                Shared.showSnackBar(findViewById(android.R.id.content), e.getMessage());
            }
        });
    }
}