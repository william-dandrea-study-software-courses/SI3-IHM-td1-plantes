package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.IEventHandler;
import com.example.td1_plantes.model.appobjects.Contribution;
import com.example.td1_plantes.model.database.FirebaseObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContributionFactory extends FirebaseObjectFactory<Contribution> {
    @Override
    protected String getCollectionName() {
        return Contribution.COLLECTION_NAME;
    }

    @Override
    protected Contribution fromMap(Map<String, Object> map) {
        return new Contribution(
                UUID.fromString((String)map.get("contributor")),
                UUID.fromString((String)map.get("plant")),
                (boolean)map.get("positiveAdvice")
        );
    }

    public void getContributionsForPlant(UUID plant, IEventHandler<List<Contribution>> callback) {
        db.collection(getCollectionName()).whereEqualTo("plant", plant.toString()).get()
                .addOnSuccessListener(res -> {
                    callback.onTrigger(
                            res.getDocuments().stream()
                                    .map(d -> fromMapWithId(d.getData(), d.getId()))
                                    .collect(Collectors.toList())
                    );
                }).addOnFailureListener(err -> {
                    callback.onTrigger(new ArrayList<>());
                });
    }

    public void getContributionsForContributor(UUID contributor, IEventHandler<List<Contribution>> callback) {
        db.collection(getCollectionName()).whereEqualTo("contributor", contributor.toString()).get()
                .addOnSuccessListener(res -> {
                    callback.onTrigger(
                            res.getDocuments().stream()
                                    .map(d -> fromMapWithId(d.getData(), d.getId()))
                                    .collect(Collectors.toList())
                    );
                }).addOnFailureListener(err -> {
            callback.onTrigger(new ArrayList<>());
        });
    }
}
