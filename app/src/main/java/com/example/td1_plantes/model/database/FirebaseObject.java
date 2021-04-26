package com.example.td1_plantes.model.database;

import com.example.td1_plantes.model.IEventHandler;
import com.example.td1_plantes.model.IVoidEventHandler;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

/**
 * @author Yann CLODONG
 */
public abstract class FirebaseObject {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id;

    public FirebaseObject(String id) {
        this.id = id;
    }

    /**
     * @return the id of the object
     */
    public String getObjectId() {
        return this.id;
    }

    void setObjectId(String id) {
        this.id = id;
    }

    /**
     * @return the name of the collection
     */
    protected abstract String getCollectionName();


    /**
     * @return This object serialized into a Map<String, Object>
     */
    public abstract Map<String, Object> toMap();

    /**
     * Save the object into the database, if an object with the same id exist : it is overwritten
     * @param success callback when success
     * @param failure callback when failure
     */
    public void save(IVoidEventHandler success, IEventHandler<Throwable> failure) {
        db
        .collection(getCollectionName())
        .document(getObjectId())
        .set(toMap(), SetOptions.merge())
        .addOnCompleteListener(r -> {
            success.onTrigger();
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }
}
