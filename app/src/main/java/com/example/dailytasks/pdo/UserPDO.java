package com.example.dailytasks.pdo;

import com.example.dailytasks.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserPDO {
    static public User user;

    static public void updateColorUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.email).update(new HashMap<String, Object>() {{
            put("color", user.color);
        }});
    }
    static public void updateImageUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.email).update(new HashMap<String, Object>() {{
            put("image", user.image);
        }});
    }
}
