package com.example.td1_plantes.utils.database.FirebaseObjects;

import com.example.td1_plantes.utils.database.FirebaseObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class User extends FirebaseObject {
    public static final String COLLECTION_NAME = "Users";

    private final String email;
    private final String username;

    public User(String email, String username) {
        this(Long.valueOf(System.currentTimeMillis()).toString(), email, username);
    }

    public User(String id, String email, String username) {
        super(id);
        this.email = email;
        this.username = username;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", this.getObjectId());
        result.put("email", email);
        result.put("username", username);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
