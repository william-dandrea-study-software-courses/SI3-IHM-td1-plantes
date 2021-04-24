package com.example.td1_plantes.model.appobjects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Contribution {


    private UUID contributor;
    private UUID plant;

    private boolean positiveAdvice;

    public Contribution(UUID contributor, UUID plant, boolean positiveAdvice) {
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
}
