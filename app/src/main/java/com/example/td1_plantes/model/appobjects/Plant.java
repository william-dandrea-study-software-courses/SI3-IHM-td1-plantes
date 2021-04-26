package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Plant extends FirebaseObject {
    public static final String COLLECTION_NAME = "Plants";

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
        super(UUID.randomUUID().toString());

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
        if (UUID.fromString(this.getObjectId()).equals(idPlantF))
            return true;
        return false;
    }


    public String getImageURL() {
        return imageURL;
    }

    public UUID getIdPlant() {
        return UUID.fromString(getObjectId());
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

    /*public void setIdPlant(UUID idPlant) {
        this.idPlant = idPlant;
    }*/

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
                "idPlant=" + getObjectId() +
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

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("isPublic", isPublic);
        result.put("title", title);
        result.put("color", color);
        result.put("imageURL", imageURL);
        result.put("size2", size);
        result.put("publicationDate", publicationDate);
        result.put("description", description);
        result.put("myPosition", myPosition.toString());
        result.put("sources", sources);
        return  result;
    }
}
