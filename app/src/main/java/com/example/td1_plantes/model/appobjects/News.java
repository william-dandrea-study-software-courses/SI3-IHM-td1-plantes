package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : D'Andrea William
 */

public class News extends FirebaseObject {
    public static final String COLLECTION_NAME = "News";


    private String title;
    private String description;
    private String date;
    private String imageURL;
    private String author;

    public News(String title, String description, String date, String imageURL, String author) {
        super(UUID.randomUUID().toString());
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


    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", author='" + author + '\'' +
                '}'  + '\n';
    }

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("date", date);
        result.put("imageURL", imageURL);
        result.put("author", author);
        return result;
    }
}
