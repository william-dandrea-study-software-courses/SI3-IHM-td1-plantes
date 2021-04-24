package com.example.td1_plantes.model.appobjects;

public class Contribution {


    private User contributor;
    private Plant plant;

    private boolean positiveAdvice;


    public User getContributor() {
        return contributor;
    }

    public Plant getPlant() {
        return plant;
    }

    public boolean isPositiveAdvice() {
        return positiveAdvice;
    }
}
