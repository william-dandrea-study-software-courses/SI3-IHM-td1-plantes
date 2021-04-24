package com.example.td1_plantes.model.database;

import com.example.td1_plantes.model.database.FirebaseFactories.UserFactory;
import com.example.td1_plantes.model.database.FirebaseObjects.Plante;
import com.example.td1_plantes.model.database.FirebaseObjects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Exemples {

    public static void RetrieveData() {
        UserFactory factory = new UserFactory();
        factory.getAll(r -> {
            // What to do with datas
            // ...
            Optional<User> user = Arrays.stream(r).filter(u -> u.getUsername().equals("Toto")).findAny();
            System.out.println(user.toString());
        }, err -> {
            // Show error
            System.err.println(err.getMessage());
        });
    }

    public static void SaveANewPlant() {
        Plante plante = new Plante("Rose", new ArrayList<>());
        plante.save(() -> {
            // Data save successfully
            System.out.println("Data saved");
        }, err -> {
            // Do somthing with the error
            System.err.println(err.getMessage());
        });
    }
}
