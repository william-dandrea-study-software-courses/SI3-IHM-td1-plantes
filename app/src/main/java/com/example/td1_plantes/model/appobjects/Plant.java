package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.Fiability;
import com.example.td1_plantes.model.appobjects.smallelements.Position;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Plant {

    private UUID idPlant;

    private boolean isPublic;
    private String title;
    private String color;
    private String imageURL;
    private int size;
    private String publicationDate;
    private String description;
    private Position position;

    private List<String> sources;


    public Plant(boolean isPublic, String title, String color, String imageURL, int size, String publicationDate, String description, Position position, List<String> sources) {

        this.idPlant = UUID.randomUUID();

        this.imageURL = imageURL;
        this.isPublic = isPublic;
        this.title = title;
        this.color = color;
        this.size = size;
        this.publicationDate = publicationDate;
        this.description = description;
        this.position = position;
        this.sources = sources;


    }

    public void addSource(String source) {
        this.sources.add(source);
    }

    public boolean isSamePlant(UUID idPlant) {
        if (this.idPlant.equals(idPlant))
            return true;
        return false;
    }


    public String getImageURL() {
        return imageURL;
    }

    public UUID getIdPlant() {
        return idPlant;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }



    public String getPublicationDate() {
        return publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public Position getPosition() {
        return position;
    }

    public List<String> getSources() {
        return sources;
    }


}
