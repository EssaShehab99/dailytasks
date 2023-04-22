package com.example.dailytasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.dailytasks.models.User;
import com.example.dailytasks.pdo.UserPDO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    EditText firstName, lastName, email, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = findViewById(R.id.first_name_et);
        lastName = findViewById(R.id.last_name_et);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        findViewById(R.id.have_account).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.create_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(firstName.getText()))
                firstName.setError("Enter Value");

            else if (TextUtils.isEmpty(lastName.getText()))
                lastName.setError("Enter Value");

            else if (TextUtils.isEmpty(email.getText()))
                email.setError("Enter Value");

            else if (TextUtils.isEmpty(password.getText()))
                password.setError("Enter Value");
            else {
                UserPDO.user = new User("", firstName.getText().toString() + " " + lastName.getText().toString(), email.getText().toString(), password.getText().toString(), R.drawable.ic_profile_1, R.color.color_1);
                signUp();
            }

        });


    }

    public void signUp() {
        db.collection("users").document(email.getText().toString())
                .set(UserPDO.user.toMap())
                .addOnSuccessListener(aVoid -> {
//                    UserPDO.user.id=aVoid;
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    finish();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }
}