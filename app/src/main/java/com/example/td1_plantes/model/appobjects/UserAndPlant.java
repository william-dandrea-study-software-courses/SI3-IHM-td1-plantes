package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserAndPlant extends FirebaseObject {
    public static final String COLLECTION_NAME = "UserAndPlants";

    private UUID user;
    private UUID plant;

    public UserAndPlant(UUID user, UUID plant) {
        super(UUID.randomUUID().toString());
        this.user = user;
        this.plant = plant;
    }

    public UUID getUser() {
        return user;
    }

    public UUID getPlant() {
        return plant;
    }

    public Optional<User> getRealUser(List<User> users) {
        return users.stream().filter(us -> us.isSameUser(this.user)).findFirst();
    }

    public Optional<Plant> getRealPlant(List<Plant> plants) {
        return plants.stream().filter(pl -> pl.isSamePlant(this.plant)).findFirst();
    }


    @Override
    public String toString() {
        return "UserAndPlant{" +
                "user=" + user +
                ", plant=" + plant +
                '}'  + '\n';
    }

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("user", user.toString());
            put("plant", plant.toString());
        }};
    }
}
