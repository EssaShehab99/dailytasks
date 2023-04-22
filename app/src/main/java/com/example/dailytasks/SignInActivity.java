package com.example.dailytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.dailytasks.models.User;
import com.example.dailytasks.pdo.UserPDO;
import com.example.dailytasks.shared.Shared;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    EditText email, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        findViewById(R.id.sign_in_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText()))
                email.setError("Enter value");
            else if (TextUtils.isEmpty(password.getText()))
                password.setError("Enter value");
            else {
                try {
                    db.collection("users").document(email.getText().toString()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (String.valueOf(Objects.requireNonNull(document.getData()).get("password")).equals(password.getText().toString())) {
                                    UserPDO.user = new User().fromMap(document.getData(), document.getId());
                                    startActivity(new Intent(this, HomeActivity.class));
                                    finish();
                                } else {
                                    Shared.showSnackBar(findViewById(android.R.id.content), "User name or password not correct");
                                }
                            } else {
                                Shared.showSnackBar(findViewById(android.R.id.content), "User name or password not correct");
                            }
                        } else {
                            Shared.showSnackBar(findViewById(android.R.id.content), "Connect Error");
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        findViewById(R.id.not_have_account).setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

    }
}