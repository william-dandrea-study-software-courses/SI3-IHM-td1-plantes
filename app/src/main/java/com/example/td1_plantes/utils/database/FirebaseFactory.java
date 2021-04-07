package com.example.td1_plantes.utils.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

/**
 * @author Yann CLODONG
 */
public class FirebaseFactory {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Save the object you builded
     * @param object object to save
     * @param success callback when the object saved
     * @param failure callback if an error occured
     * @param <T>   type of the object to save
     */
    public static <T extends FirebaseObject> void save(T object, IEventHandler<Object> success, IEventHandler<Throwable> failure) {
        db
        .collection(object.getCollectionName())
        .document(object.getObjectId())
        .set(object.toMap(), SetOptions.merge())
        .addOnCompleteListener(r -> {
            success.onTrigger(null);
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }
}
