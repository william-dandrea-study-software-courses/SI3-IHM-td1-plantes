package com.example.td1_plantes.utils.database;

import com.example.td1_plantes.utils.IEventHandler;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public abstract class FirebaseObjectFactory<T extends FirebaseObject> {
    protected final FirebaseFirestore db = FirebaseFirestore.getInstance();


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

    /**
     * Return all objects of the collection
     * @param success callback when success
     * @param failure callback when failure
     */
    public void getAll(IEventHandler<T[]> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).get().addOnCompleteListener(r -> {
            QuerySnapshot res = r.getResult();
            if(res == null) {
                failure.onTrigger(new RuntimeException("Something happens"));
                return;
            }
            List<DocumentSnapshot> docs = res.getDocuments();
            
            T[] result = (T[])new Object[docs.size()];
            for(int i = 0; i < docs.size(); i++)
                result[i] = fromMap(docs.get(i).getData());

            success.onTrigger(result);
        }).addOnFailureListener(failure::onTrigger);
    }
}
