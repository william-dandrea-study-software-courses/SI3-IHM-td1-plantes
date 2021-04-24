package com.example.td1_plantes.model.database.FirebaseObjects;

import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class User extends FirebaseObject {
    public static final String COLLECTION_NAME = "Users";

    private final String email;
    private final String username;
    private final String avatar;
    private final List<String> photos;


    public User(String email, String username, String avatar, List<String> photos) {
        this(Long.valueOf(System.currentTimeMillis()).toString(), email, username, avatar, photos);
    }

    public User(String id, String email, String username, String avatar, List<String> photos) {
        super(id);
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.photos = photos;

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
        result.put("avatar", avatar);
        result.put("photos", photos);


        return result;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public int getPhotosCount() {
        if(photos == null) return 0;
        return photos.size();
    }

    public List<String> getPhotos() {
        return this.photos;
    }

}
