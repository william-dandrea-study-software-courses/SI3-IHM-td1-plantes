package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.Fiability;
import com.example.td1_plantes.model.appobjects.smallelements.Position;

import java.util.Arrays;

public class Plant {

    private boolean isPublic;
    private String title;
    private String color;
    private int size;
    private User author;
    private String publicationDate;
    private String description;
    private Position position;

    private String[] sources;
    private Contribution[] contributions;

    private int numberOfContributors;
    private int numberOfGoodReview;
    private Fiability fiability;



    private void setIndicators() {

        this.numberOfContributors = contributions.length;
        this.numberOfGoodReview = (int) Arrays.stream(contributions).filter(Contribution::isPositiveAdvice).count();

        if ((double) numberOfGoodReview / (double) numberOfContributors <= 0.5) {
            this.fiability = Fiability.LOW;
        } else if ((double) numberOfGoodReview / (double) numberOfContributors <= 0.75) {
            this.fiability = Fiability.MEDIUM;
        } else {
            this.fiability = Fiability.HIGH;
        }
    }
}
