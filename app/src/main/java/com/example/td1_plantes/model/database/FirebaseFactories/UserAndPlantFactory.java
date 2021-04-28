package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.IEventHandler;
import com.example.td1_plantes.model.appobjects.UserAndPlant;
import com.example.td1_plantes.model.database.FirebaseObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserAndPlantFactory extends FirebaseObjectFactory<UserAndPlant> {
    @Override
    protected String getCollectionName() {
        return UserAndPlant.COLLECTION_NAME;
    }

    @Override
    protected UserAndPlant fromMap(Map<String, Object> map) {
        return new UserAndPlant(
                UUID.fromString((String)map.get("user")),
                UUID.fromString((String)map.get("plant"))
        );
    }

    public void getContributorFor(UUID plant, IEventHandler<List<UUID>> callback) {
        db.collection(getCollectionName()).whereEqualTo("plant", plant.toString()).get()
                .addOnFailureListener(err -> callback.onTrigger(new ArrayList<>()))
                .addOnSuccessListener(res -> {
                    if(res.isEmpty()) callback.onTrigger(new ArrayList<>());
                    else {
                        List<UUID> users = new ArrayList<>();
                        res.getDocuments().forEach(d -> users.add(fromMap(d.getData()).getUser()));
                        callback.onTrigger(users);
                    }
                });
    }

    public void getPlantsFor(UUID contributor, IEventHandler<List<UUID>> callback) {
        db.collection(getCollectionName()).whereEqualTo("user", contributor.toString()).get()
                .addOnFailureListener(err -> callback.onTrigger(new ArrayList<>()))
                .addOnSuccessListener(res -> {
                    List<UUID> plants = new ArrayList<>();
                    res.getDocuments().forEach(d -> plants.add(fromMap(d.getData()).getPlant()));
                    callback.onTrigger(plants);
                });
    }
}
