package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Contribution extends FirebaseObject {
    public static final String COLLECTION_NAME = "Contributions";

    private UUID contributor;
    private UUID plant;

    private boolean positiveAdvice;

    public Contribution(UUID contributor, UUID plant, boolean positiveAdvice) {
        super(UUID.randomUUID().toString());

        this.contributor = contributor;
        this.plant = plant;
        this.positiveAdvice = positiveAdvice;
    }

    public UUID getContributor() {
        return contributor;
    }

    public UUID getPlant() {
        return plant;
    }

    public Optional<Plant> getRealPlant(List<Plant> plants) {
        return plants.stream().filter(pl -> pl.isSamePlant(this.plant)).findFirst();
    }

    public Optional<User> getRealContributor(List<User> users) {
        return users.stream().filter(us -> us.isSameUser(this.contributor)).findFirst();
    }


    public boolean isPositiveAdvice() {
        return positiveAdvice;
    }


    @Override
    public String toString() {
        return "Contribution{" +
                "contributor=" + contributor +
                ", plant=" + plant +
                ", positiveAdvice=" + positiveAdvice +
                '}'  + '\n';
    }


    public void setContributor(UUID contributor) {
        this.contributor = contributor;
    }

    public void setPlant(UUID plant) {
        this.plant = plant;
    }

    public void setPositiveAdvice(boolean positiveAdvice) {
        this.positiveAdvice = positiveAdvice;
    }

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("contributor", contributor.toString());
        result.put("plant", plant.toString());
        result.put("positiveAdvice", positiveAdvice);
        return result;
    }
}
