package com.example.td1_plantes.utils.database;

import com.example.td1_plantes.utils.database.FirebaseFactories.ObservationFactory;
import com.example.td1_plantes.utils.database.FirebaseFactories.PlanteFactory;
import com.example.td1_plantes.utils.database.FirebaseFactories.UserFactory;
import com.example.td1_plantes.utils.database.FirebaseObjects.Observation;
import com.example.td1_plantes.utils.database.FirebaseObjects.Plante;
import com.example.td1_plantes.utils.database.FirebaseObjects.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public class FirebaseFactory {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Map<String, FirebaseObjectFactory> factories;

    public FirebaseFactory() {
        this.factories = new HashMap<>();

        factories.put(Plante.COLLECTION_NAME, new PlanteFactory());
        factories.put(Observation.COLLECTION_NAME, new ObservationFactory());
        factories.put(User.COLLECTION_NAME, new UserFactory());
    }

    /**
     * Retreive Object in the database for you
     * @param collection Collection name of the object you search for: There is constant inside each objects
     * @param id id Of the object you search for
     * @param success callback when success
     * @param failure callback if the request failed
     * @param <T> Object type
     */
    public <T extends FirebaseObject> void load(String collection, String id, IEventHandler<T> success, IEventHandler<Throwable> failure) {
        db.collection(collection).document(id).get().addOnCompleteListener(res -> {
            Map<String, Object> content = Objects.requireNonNull(Objects.requireNonNull(res.getResult()).getData());
            success.onTrigger((T)factories.get(collection).fromMap(content));
        })
        .addOnFailureListener(res -> {
            failure.onTrigger(res.getCause());
        });
    }

    /**
     * Save the object you builded
     * @param object object to save
     * @param success callback when the object saved
     * @param failure callback if an error occured
     * @param <T>   type of the object to save
     */
    public <T extends FirebaseObject> void save(T object, IEventHandler<Object> success, IEventHandler<Throwable> failure) {
        db
        .collection(object.getCollectionName())
        .document(object.getObjectId())
        .set(this.factories.get(object.getCollectionName()), SetOptions.merge()).addOnCompleteListener(r -> {
            success.onTrigger(null);
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }
}
