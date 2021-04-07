package com.example.td1_plantes.utils.database;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public abstract class FirebaseObjectFactory<T extends FirebaseObject> {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    protected abstract String getCollectionName();

    /**
     * Deserialize the map to the object
     * @param map The map in witch retrive datas
     * @return The object builded
     */
    protected abstract T fromMap(Map<String, Object> map);

    /**
     * Load the object from firebase
     * @param id Id of the object you searching for
     * @param success callback when success
     * @param failure callback when failure
     */
    public void loadFromFirebase(String id, IEventHandler<T> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).document(id).get().addOnCompleteListener(res -> {
            Map<String, Object> content = Objects.requireNonNull(Objects.requireNonNull(res.getResult()).getData());
            success.onTrigger(fromMap(content));
        })
        .addOnFailureListener(res -> {
            failure.onTrigger(res.getCause());
        });
    }
}
