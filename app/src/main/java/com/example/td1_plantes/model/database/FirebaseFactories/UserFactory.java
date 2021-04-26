package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.IEventHandler;
import com.example.td1_plantes.model.appobjects.User;
import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;
import com.example.td1_plantes.model.database.FirebaseObjectFactory;
import com.google.firebase.firestore.FieldPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserFactory extends FirebaseObjectFactory<User> {
    @Override
    protected String getCollectionName() {
        return User.COLLECTION_NAME;
    }

    @Override
    protected User fromMap(Map<String, Object> map) {
        return new User(
                (String)map.get("name"),
                (String)map.get("surname"),
                (String)map.get("dateOfBirth"),
                StatusUser.fromId((int)(long)map.get("statusUser")),
                (String)map.get("avatar"),
                (List<String>)map.get("photos")
        );
    }

    public void getMany(List<UUID> ids, IEventHandler<List<User>> callback) {
        if(ids.size() == 0) {
            callback.onTrigger(new ArrayList<>());
            return;
        }
        db.collection(getCollectionName()).whereIn(FieldPath.documentId(), ids.stream().map(UUID::toString).collect(Collectors.toList())).get()
                .addOnSuccessListener(users -> {
                    callback.onTrigger(users.getDocuments().stream().map(d -> fromMapWithId(d.getData(), d.getId())).collect(Collectors.toList()));
                }).addOnFailureListener(err -> {
                    callback.onTrigger(new ArrayList<>());
                });
    }

    public void getBySurname(String username, IEventHandler<User> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).whereEqualTo("surname", username).get()
                .addOnFailureListener(err -> {
                    failure.onTrigger(err.getCause());
                }).addOnSuccessListener(res -> {
                    if(res.isEmpty()) failure.onTrigger(new RuntimeException("No user found"));
                    else success.onTrigger(fromMapWithId(res.getDocuments().get(0).getData(), res.getDocuments().get(0).getId()));
                });
    }
}
