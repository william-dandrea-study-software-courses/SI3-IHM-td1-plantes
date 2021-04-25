package com.example.td1_plantes.model;

import com.example.td1_plantes.model.appobjects.Contribution;
import com.example.td1_plantes.model.appobjects.News;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;
import com.example.td1_plantes.model.appobjects.UserAndPlant;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;

import java.util.ArrayList;
import java.util.List;

public class Mocks {

    public static final String descriptionBanal = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas hendrerit magna orci, eget sollicitudin dui dictum a. Suspendisse quis sollicitudin sem. Integer fermentum aliquam semper. Aliquam erat volutpat. Aliquam justo nunc, sodales et semper non, congue nec eros. Nunc sed pellentesque nibh. Duis lobortis quis tellus et bibendum. Vivamus luctus, orci sed dignissim fermentum, mi tortor elementum nulla, et tristique ex lectus quis felis. Vestibulum sit amet porta enim. Sed fermentum hendrerit turpis, posuere posuere nulla mattis quis. Sed lacinia consequat metus, vitae ullamcorper augue semper tristique.";

    public static final News[] LIST_OF_NEWS = {
            new News("La biologie, une science patriarcale et « viriliste » ?",
                    "Pour le philosophe Jean-François Braunstein, les discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel. Pour le philosophe Jean-François Braunstein, les discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel.",
                    "18 / 12 / 2020",
                    "https://unsplash.com/photos/oRWRlTgBrPo/download?force=true&w=1920",
                    "George DesMarsais"
            ),
            new News("La biologie, une science patriarcale et « viriliste » ?",
                    "Pour le philosophe Jean-François Braunstein, les discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel. Pour le philosophe Jean-François Braunstein, les discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel.",
                    "13 / 12 / 1999",
                    "https://unsplash.com/photos/oRWRlTgBrPo/download?force=true&w=1920",
                    "George DesMarsais"
            )
    };

    public static final User user1 = new User("Jean", "Delaroche", "15/09/1987", StatusUser.PASSIONATE);
    public static final User user2 = new User("Robert", "Marsais", "12/04/1939", StatusUser.PASSIONATE);
    public static final User user3 = new User("Marine", "Dumarchant", "30/01/1997", StatusUser.PASSIONATE);
    public static final User user4 = new User("Louis", "Fanzi", "30/01/1952", StatusUser.EXPERT);
    public static final User user5 = new User("Yvette", "Lamarche", "26/03/1957", StatusUser.EXPERT);
    public static final User user6 = new User("Robertette", "Delavier", "02/07/1982", StatusUser.EXPERT);
    public static final User user7 = new User("Roger", "Lavalant", "01/02/1943", StatusUser.EXPERT);

    public static final Plant plant1 = new Plant(true, "Plaquetia", "Vert", "https://unsplash.com/photos/8mqOw4DBBSg/download?force=true&w=640",20, "20/04/2021", descriptionBanal, new MyPosition(43.6961, 7.27178, "Nice - France"), new ArrayList<String>() {{add("Wikipedia"); add("GeoFrance"); add("SuperMondePlante");}});
    public static final Plant plant2 = new Plant(true, "Cornemusa", "Vert", "https://unsplash.com/photos/eA2z1JSzZFI/download?force=true&w=640",25, "20/02/2021", descriptionBanal, new MyPosition(43.6965, 7.27189, "Antibes - France"), new ArrayList<String>() {{add("FranceMonde"); add("SuperTulipe"); add("Wikipedia");}});
    public static final Plant plant3 = new Plant(true, "Plaquetirum", "Vert", "https://unsplash.com/photos/wMqPJs2x0-A/download?force=true&w=640",40, "20/02/2021", descriptionBanal, new MyPosition(43.69653, 7.27195, "Grasse - France"), new ArrayList<String>() {{add("RomuLULU"); add("GeoFrance"); add("SuperMondePlante");}});
    public static final Plant plant4 = new Plant(true, "Plaquetirum", "Vert", "https://unsplash.com/photos/wMqPJs2x0-A/download?force=true&w=640",40, "20/02/2021", descriptionBanal, new MyPosition(43.6963, 7.271954, "Biot - France"), new ArrayList<String>() {{add("Wikipedia"); add("GeoFrance"); add("SuperMondePlante");}});
    public static final Plant plant5 = new Plant(false, "Turlutura", "Blanc", "https://unsplash.com/photos/f2kQaPm7muc/download?force=true&w=640",5, "20/02/2021", descriptionBanal, new MyPosition(43.6912, 7.27154, "Juan-les-Pins - France"), new ArrayList<String>() {{add("Wikipedia"); add("GeoFrance"); add("SuperMondePlante");}});
    public static final Plant plant6 = new Plant(false, "Claquira", "Jaune", "https://unsplash.com/photos/qoOLTV5A9vg/download?force=true&w=640",8, "20/02/2021", descriptionBanal, new MyPosition(43.6952, 7.271, "Cannes - France"), new ArrayList<String>() {{add("YoFlower"); add("GeoFrance"); add("SuperMondePlante");}});
    public static final Plant plant7 = new Plant(false, "Versabra", "Rose", "https://unsplash.com/photos/IIJfaPtF4J4/download?force=true&w=640",3, "20/02/2021", descriptionBanal, new MyPosition(43.69541, 7.27134, "Monaco - France"), new ArrayList<String>() {{add("Wikipedia"); add("SuperFlower"); add("SuperMondePlante");}});
    public static final Plant plant8 = new Plant(false, "Venusa", "Rose", "https://unsplash.com/photos/Ru6hBXxVph8/download?force=true&w=640",5, "20/02/2021", descriptionBanal, new MyPosition(43.69548, 7.271342, "Valbonne - France"), new ArrayList<String>() {{add("Wikipedia"); add("GeoFrance"); add("ViveLaNature");}});
    public static final Plant plant9 = new Plant(false, "Colerivana", "Violet", "https://unsplash.com/photos/DlYzHwAl32g/download?force=true&w=640",30, "20/02/2021", descriptionBanal, new MyPosition(43.69567, 7.27103, "Golfe-Juan - France"), new ArrayList<String>() {{add("BonjourTulipe"); add("GeoFrance"); add("GeoFrance");}});


    public static final List<User> users = new ArrayList<User>() {{
        add(user1);
        add(user2);
        add(user3);
        add(user4);
        add(user5);
        add(user6);
        add(user7);
    }};

    public static final List<Plant> plants = new ArrayList<Plant>() {{
        add(plant1);
        add(plant2);
        add(plant3);
        add(plant4);
        add(plant5);
        add(plant6);
        add(plant7);
        add(plant8);
        add(plant9);
    }};

    public static final List<Contribution> contributions = new ArrayList<Contribution>() {{
        add(new Contribution(user4.getUserId(), plant1.getIdPlant(), true));
        add(new Contribution(user5.getUserId(), plant1.getIdPlant(), true));
        add(new Contribution(user6.getUserId(), plant1.getIdPlant(), true));
        add(new Contribution(user7.getUserId(), plant1.getIdPlant(), true));

        add(new Contribution(user4.getUserId(), plant2.getIdPlant(), true));
        add(new Contribution(user5.getUserId(), plant2.getIdPlant(), true));
        add(new Contribution(user6.getUserId(), plant2.getIdPlant(), true));
        add(new Contribution(user7.getUserId(), plant2.getIdPlant(), false));

        add(new Contribution(user4.getUserId(), plant3.getIdPlant(), true));
        add(new Contribution(user5.getUserId(), plant3.getIdPlant(), true));
        add(new Contribution(user6.getUserId(), plant3.getIdPlant(), false));
        add(new Contribution(user7.getUserId(), plant3.getIdPlant(), false));

        add(new Contribution(user4.getUserId(), plant4.getIdPlant(), true));
        add(new Contribution(user5.getUserId(), plant4.getIdPlant(), false));
    }};


    public static final List<UserAndPlant> userAndPlants = new ArrayList<UserAndPlant>() {{
        add(new UserAndPlant(user1.getUserId(), plant5.getIdPlant()));
        add(new UserAndPlant(user2.getUserId(), plant6.getIdPlant()));
        add(new UserAndPlant(user3.getUserId(), plant7.getIdPlant()));
        add(new UserAndPlant(user1.getUserId(), plant8.getIdPlant()));
        add(new UserAndPlant(user1.getUserId(), plant9.getIdPlant()));

        add(new UserAndPlant(user4.getUserId(), plant1.getIdPlant()));
        add(new UserAndPlant(user5.getUserId(), plant2.getIdPlant()));
        add(new UserAndPlant(user6.getUserId(), plant3.getIdPlant()));
        add(new UserAndPlant(user7.getUserId(), plant4.getIdPlant()));
    }};



}
