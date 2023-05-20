package com.example.dailytasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.dailytasks.models.TaskModel;
import com.example.dailytasks.pdo.UserPDO;
import com.example.dailytasks.shared.Shared;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    EditText taskName;
    EditText startDate;
    EditText taskDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskName=findViewById(R.id.task_name_et);
        startDate =findViewById(R.id.start_date_et);
        taskDate=findViewById(R.id.date_et);



        Calendar myCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener startDatePicker = (vi, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setStartDate(startCalendar);
        };
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
        startDate.setOnClickListener(v -> {
            new DatePickerDialog(this, startDatePicker, startCalendar
                    .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        findViewById(R.id.create_task_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(taskName.getText()))
                taskName.setError("Enter value");
            else if (TextUtils.isEmpty(taskDate.getText()))
                taskDate.setError("Enter value");

            else if (TextUtils.isEmpty(startDate.getText()))
                startDate.setError("Enter value");

            else{


                TaskModel taskModel = new TaskModel("", taskName.getText().toString(), myCalendar.getTime(),startCalendar.getTime(), false);
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

    private void setStartDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startDate.setText(sdf.format(calendar.getTime()));
    }

    private void setDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        taskDate.setText(sdf.format(calendar.getTime()));
    }
}