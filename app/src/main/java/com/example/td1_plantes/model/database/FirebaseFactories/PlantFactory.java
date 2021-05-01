package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.IEventHandler;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.example.td1_plantes.model.database.FirebaseObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlantFactory extends FirebaseObjectFactory<Plant> {
    @Override
    protected String getCollectionName() {
        return Plant.COLLECTION_NAME;
    }

    @Override
    protected Plant fromMap(Map<String, Object> map) {
        return new Plant(
                (boolean)map.get("isPublic"),
                (String)map.get("title"),
                (String)map.get("color"),
                (String)map.get("imageURL"),
                (int)(long)(Long)map.get("size2"),
                (String)map.get("publicationDate"),
                (String)map.get("description"),
                MyPosition.parse((String)map.get("myPosition")),
                (List<String>)map.get("sources")
        );
    }

    public void getManyPlants(List<UUID> plants, IEventHandler<List<Plant>> onsuccess, IEventHandler<Throwable> onfailure) {
        if(plants.size() == 0) {
            onsuccess.onTrigger(new ArrayList<>());
            return;
        }
        db.collection(getCollectionName()).get().addOnSuccessListener(res -> {
            List<Plant> result = new ArrayList<>();
            res.getDocuments().stream().filter(d -> plants.stream().map(UUID::toString).collect(Collectors.toList()).contains(d.getId())).forEach(d -> result.add(fromMapWithId(d.getData(), d.getId())));
            onsuccess.onTrigger(result);
        }).addOnFailureListener(err -> onfailure.onTrigger(err.getCause()));
    }

    public void getPublicPlants(IEventHandler<List<Plant>> onsuccess, IEventHandler<Throwable> onfailure) {
        db.collection(getCollectionName()).whereEqualTo("isPublic", true).get().addOnSuccessListener(res -> {
            List<Plant> result = new ArrayList<>();
            res.getDocuments().forEach(d -> result.add(fromMapWithId(d.getData(), d.getId())));
            onsuccess.onTrigger(result);
        }).addOnFailureListener(err -> onfailure.onTrigger(err.getCause()));
    }

    public void getPrivatePlants(IEventHandler<List<Plant>> onsuccess, IEventHandler<Throwable> onfailure) {
        db.collection(getCollectionName()).whereEqualTo("isPublic", false).get().addOnSuccessListener(res -> {
            List<Plant> result = new ArrayList<>();
            res.getDocuments().forEach(d -> result.add(fromMapWithId(d.getData(), d.getId())));
            onsuccess.onTrigger(result);
        }).addOnFailureListener(err -> onfailure.onTrigger(err.getCause()));
    }
}
