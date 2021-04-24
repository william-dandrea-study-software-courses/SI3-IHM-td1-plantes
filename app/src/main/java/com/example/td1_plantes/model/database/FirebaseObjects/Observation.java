package com.example.td1_plantes.model.database.FirebaseObjects;

import android.location.Location;

import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class Observation extends FirebaseObject {
    public static final String COLLECTION_NAME = "Observations";

    private String author;
    private String plante;
    private Location location;
    private List<String> photos;

    public Observation(String author, String plante, Location location, List<String> photos) {
        this(Long.valueOf(System.currentTimeMillis()).toString(), author, plante, location, photos);
    }

    public Observation(String id, String author, String plante, Location location, List<String> photos) {
        super(id);
        this.author = author;
        this.plante = plante;
        this.location = location;
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
        result.put("author", this.author);
        result.put("plante", this.plante);
        result.put("location", this.location.getProvider());
        result.put("photos", this.photos);
        return result;
    }
}
