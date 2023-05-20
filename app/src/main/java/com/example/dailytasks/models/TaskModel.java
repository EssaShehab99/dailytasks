package com.example.dailytasks.models;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskModel {
    public  String id;
    public String name;
    public Date startDate;
    public Date date;
   public boolean status;

    public TaskModel(String id, String name, Date startDate, Date date, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.date = date;
    }

    static   public TaskModel fromMap(Map<String, Object> map, String id) throws ParseException {
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new TaskModel(
                id,
                Objects.requireNonNull(map.getOrDefault("name", "")).toString(),
                formatter.parse(Objects.requireNonNull(map.getOrDefault("date", "1")).toString()),
                formatter.parse(Objects.requireNonNull(map.getOrDefault("startDate", "1")).toString()),
                (Boolean) Objects.requireNonNull(map.getOrDefault("status", false))
        );
    }

    public final Map<String, Object> toMap() {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new HashMap<String, Object>() {{
            put("name", name);
            put("startDate", formatter.format(startDate));
            put("date", formatter.format(date));
            put("status", status);
        }};
    }
    @NonNull
    @Override
    public String toString() {
        return "TaskModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", startDate=" + startDate +
                ", status=" + status +
                '}';
    }
}