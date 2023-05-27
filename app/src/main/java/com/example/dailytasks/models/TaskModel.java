package com.example.dailytasks.models;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.sql.Time;
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
    public Time startDate;
    public Date date;
   public boolean status;

    public TaskModel(String id, String name, Time startDate, Date date, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.date = date;
    }

    public static TaskModel fromMap(Map<String, Object> map, String id) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        return new TaskModel(
                id,
                Objects.requireNonNull(map.getOrDefault("name", "")).toString(),
                new Time(timeFormatter.parse(Objects.requireNonNull(map.getOrDefault("startDate", "00:00:00")).toString()).getTime()),
                formatter.parse(Objects.requireNonNull(map.getOrDefault("date", "1")).toString()),
                (Boolean) Objects.requireNonNull(map.getOrDefault("status", false))
        );
    }

    public final Map<String, Object> toMap() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        return new HashMap<String, Object>() {{
            put("name", name);
            put("startDate", timeFormatter.format(startDate));
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