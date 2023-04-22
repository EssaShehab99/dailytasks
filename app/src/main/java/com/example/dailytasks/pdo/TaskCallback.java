package com.example.dailytasks.pdo;

public interface TaskCallback {
    void onSuccess(boolean success);

    void onFailure(Exception e);
}
