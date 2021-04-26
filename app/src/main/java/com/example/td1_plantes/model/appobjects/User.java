package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;
import com.example.td1_plantes.model.database.FirebaseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User extends FirebaseObject {
    public static final String COLLECTION_NAME = "Users";

    //private UUID userId;

    private String name;
    private String surname;
    private String dateOfBirth;
    private StatusUser statusUser;
    private String avatar;
    private List<String> photos;


    public User(String name, String surname, String dateOfBirth, StatusUser statusUser, String avatar, List<String> photos) {
        super(UUID.randomUUID().toString());

        //this.userId = UUID.fromString(getObjectId());
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.statusUser = statusUser;
        this.avatar = avatar;
        this.photos = photos;
    }



    public boolean isSameUser(UUID idPlant) {
        if (UUID.fromString(this.getObjectId()).equals(idPlant))
            return true;
        return false;
    }

    public UUID getUserId() {
        return UUID.fromString(getObjectId());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    public int getPhotoCount() {
        return this.photos.size();
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + getObjectId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", statusUser=" + statusUser +
                '}' + '\n';
    }

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("surname", surname);
        result.put("dateOfBirth", dateOfBirth);
        result.put("statusUser", statusUser.getId());
        result.put("avatar", avatar);
        result.put("photos", photos);
        return result;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public List<String> getPhotos() {
        return this.photos;
    }
}
