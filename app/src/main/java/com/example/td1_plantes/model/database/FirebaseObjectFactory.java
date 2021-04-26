package com.example.td1_plantes.model.database;

import com.example.td1_plantes.model.IEventHandler;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    protected T fromMapWithId(Map<String, Object> map, String id) {
        T t = fromMap(map);
        t.setObjectId(id);
        return t;
    }

    /**
     * Load the object from firebase
     * @param id Id of the object you searching for
     * @param success callback when success
     * @param failure callback when failure
     */
    public void loadFromFirebase(String id, IEventHandler<T> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).document(id).get().addOnCompleteListener(res -> {
            if(res.getResult() == null || res.getResult().getData() == null) {
                failure.onTrigger(new RuntimeException("Document not found"));
                return;
            }
            Map<String, Object> content = res.getResult().getData();
            T result = fromMap(content);
            result.setObjectId(id);
            success.onTrigger(result);
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
    public void getAll(IEventHandler<List<T>> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).get().addOnCompleteListener(r -> {
            QuerySnapshot res = r.getResult();
            if(res == null) {
                failure.onTrigger(new RuntimeException("Something happens"));
                return;
            }
            List<DocumentSnapshot> docs = res.getDocuments();

            success.onTrigger(docs.stream().map(d -> fromMapWithId(d.getData(), d.getId())).collect(Collectors.toList()));
        }).addOnFailureListener(failure::onTrigger);
    }
}
