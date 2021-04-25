package com.example.td1_plantes.model.appobjects;

import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;

import java.util.List;
import java.util.UUID;

public class User {

    private UUID userId;

    private String name;
    private String surname;
    private String dateOfBirth;
    private StatusUser statusUser;


    public User(String name, String surname, String dateOfBirth, StatusUser statusUser) {

        this.userId = UUID.randomUUID();

        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.statusUser = statusUser;

    }



    public boolean isSameUser(UUID idPlant) {
        if (this.userId.equals(idPlant))
            return true;
        return false;
    }

    public UUID getUserId() {
        return userId;
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


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", statusUser=" + statusUser +
                '}' + '\n';
    }
}
