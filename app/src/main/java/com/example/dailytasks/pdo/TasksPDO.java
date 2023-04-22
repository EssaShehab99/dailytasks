package com.example.dailytasks.pdo;

import com.example.dailytasks.models.TaskModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TasksPDO {
    static public List<TaskModel> taskModelList = new ArrayList<>();

    public static void update(TaskModel taskModel, TaskCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(UserPDO.user.email).collection("tasks").document(taskModel.id)
                .update(taskModel.toMap())
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess(true);
                })
                .addOnFailureListener(callback::onFailure);
    }
    public static void delete(String id, TaskCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(UserPDO.user.email).collection("tasks").document(id)
                .delete()
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess(true);
                })
                .addOnFailureListener(callback::onFailure);
    }

}

