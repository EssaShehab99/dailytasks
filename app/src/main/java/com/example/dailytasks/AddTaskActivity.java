package com.example.dailytasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.dailytasks.models.TaskModel;
import com.example.dailytasks.pdo.UserPDO;
import com.example.dailytasks.shared.Shared;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    EditText taskName;
    EditText startTime;
    EditText taskDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskName=findViewById(R.id.task_name_et);
        startTime =findViewById(R.id.start_date_et);
        taskDate=findViewById(R.id.date_et);



        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (vi, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate(myCalendar);
        };
        taskDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        Calendar startCalendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener startTimePicker = (view, hourOfDay, minute) -> {
            // TODO Auto-generated method stub
            startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startCalendar.set(Calendar.MINUTE, minute);
            setStartTime(startCalendar);
        };

        startTime.setOnClickListener(v -> {
            new TimePickerDialog(this, startTimePicker, startCalendar
                    .get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), false).show();
        });


        findViewById(R.id.create_task_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(taskName.getText()))
                taskName.setError("Enter value");
            else if (TextUtils.isEmpty(taskDate.getText()))
                taskDate.setError("Enter value");

            else if (TextUtils.isEmpty(startTime.getText()))
                startTime.setError("Enter value");

            else{
                Time startTime = new Time(startCalendar.getTime().getTime());


                TaskModel taskModel = new TaskModel("", taskName.getText().toString(), startTime,myCalendar.getTime(), false);
                Log.d("TaskModel", taskModel.toString());

                @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(UserPDO.user.email).collection("tasks")
                        .add(taskModel.toMap())
                        .addOnSuccessListener(aVoid -> {
                            Shared.showSnackBar(v, "Task inserted done");
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Shared.showSnackBar(v, "Error writing document");

                            }
                        });
            }
        });
    }

    private void setStartTime(Calendar calendar) {
        String format = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startTime.setText(sdf.format(calendar.getTime()));
    }


    private void setDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        taskDate.setText(sdf.format(calendar.getTime()));
    }
}