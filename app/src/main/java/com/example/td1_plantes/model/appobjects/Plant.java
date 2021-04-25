package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;

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
    private MyPosition myPosition;

    private List<String> sources;


    public Plant(boolean isPublic, String title, String color, String imageURL, int size, String publicationDate, String description, MyPosition myPosition, List<String> sources) {

        this.idPlant = UUID.randomUUID();

        this.imageURL = imageURL;
        this.isPublic = isPublic;
        this.title = title;
        this.color = color;
        this.size = size;
        this.publicationDate = publicationDate;
        this.description = description;
        this.myPosition = myPosition;
        this.sources = sources;


    }

    public void addSource(String source) {
        this.sources.add(source);
    }

    public boolean isSamePlant(UUID idPlantF) {
        if (this.idPlant.equals(idPlantF))
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

    public MyPosition getMyPosition() {
        return myPosition;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setIdPlant(UUID idPlant) {
        this.idPlant = idPlant;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMyPosition(MyPosition myPosition) {
        this.myPosition = myPosition;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "idPlant=" + idPlant +
                ", isPublic=" + isPublic +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", size=" + size +
                ", publicationDate='" + publicationDate + '\'' +
                ", description='" + description + '\'' +
                ", myPosition=" + myPosition +
                ", sources=" + sources +
                '}'  + '\n';
    }
}
