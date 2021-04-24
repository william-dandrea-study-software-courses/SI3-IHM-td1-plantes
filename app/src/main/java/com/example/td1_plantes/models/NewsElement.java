package com.example.td1_plantes.models;

import java.util.Calendar;
import java.util.Date;

public class NewsElement {

    private final String title;
    private final String description;
    private final String imageURL;
    private final Calendar publicationDate;

    public NewsElement(String title, String description, String imageURL, Calendar publicationDate) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }
}
