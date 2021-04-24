package com.example.td1_plantes.model.appobjects;

import java.util.Calendar;

/**
 * @author : D'Andrea William
 */

public class News {

    private String title;
    private String description;
    private String date;
    private String imageURL;
    private String author;

    public News(String title, String description, String date, String imageURL, String author) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageURL = imageURL;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAuthor() {
        return author;
    }
}
