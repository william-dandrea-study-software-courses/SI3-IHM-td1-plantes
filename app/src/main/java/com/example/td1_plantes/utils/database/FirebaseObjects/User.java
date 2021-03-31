package com.example.td1_plantes.utils.database.FirebaseObjects;

import com.example.td1_plantes.utils.database.FirebaseObject;
import com.example.td1_plantes.utils.database.IEventHandler;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;
import java.util.Objects;

public class User extends FirebaseObject {
    private final String id;
    private final String email;
    private final String username;

    private static CollectionReference collection = FirebaseFirestore.getInstance().collection("Users");

    public User(String email, String username) {
        this.id = Long.valueOf(System.currentTimeMillis()).toString();
        this.email = email;
        this.username = username;
    }


    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    public static User fromMap(Map<String, Object> map) {
        return null;
    }

    @Override
    public void save(IEventHandler<Object> success, IEventHandler<Throwable> failure) {
        collection.whereEqualTo("email", email).get().addOnCompleteListener((r) -> {
            if(Objects.requireNonNull(r.getResult()).getDocuments().size() > 0) {
                failure.onTrigger(new RuntimeException("User allready exist !"));
            } else {
                collection.document(id).set(toMap(), SetOptions.merge()).addOnCompleteListener(re -> {
                    success.onTrigger(null);
                }).addOnFailureListener(re -> {
                    failure.onTrigger(re.getCause());
                });
            }
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }

    public static void load(String email, IEventHandler<User> success, IEventHandler<Throwable> failure) {
        collection.whereEqualTo("email", email).get().addOnSuccessListener(r -> {
            if(r.getDocuments().size() > 0) {
                User user = fromMap(r.getDocuments().get(0).getData());
                success.onTrigger(user);
            } else {
                failure.onTrigger(new RuntimeException("Internal Error"));
            }
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }
}
