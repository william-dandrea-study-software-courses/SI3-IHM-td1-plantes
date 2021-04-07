package com.example.td1_plantes.utils.database.FirebaseObjects;

import com.example.td1_plantes.utils.database.FirebaseObject;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class Plante extends FirebaseObject {
    public static final String COLLECTION_NAME = "Plantes";

    private final String name;
    private final List<String> photos;

    private static final CollectionReference db = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);;

    public Plante(String name, List<String> photos) {
        this(Long.valueOf(System.currentTimeMillis()).toString(), name, photos);
    }

    public Plante(String id, String name, List<String> photos) {
        super(id);
        this.name = name;
        this.photos = photos;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("id", this.getObjectId());
        obj.put("name", this.name);
        obj.put("photos", this.photos);
        return obj;
    }
}
