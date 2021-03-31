package com.example.td1_plantes.utils.database.FirebaseObjects;

import android.location.Location;

import com.example.td1_plantes.utils.database.FirebaseObject;
import com.example.td1_plantes.utils.database.IEventHandler;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Plante extends FirebaseObject {
    private final String id;
    private final String name;
    private final List<String> photos;
    private final Location location;


    private static final CollectionReference db = FirebaseFirestore.getInstance().collection("Plantes");

    public Plante(String name, List<String> photos, Location location) {
        this.id = Long.valueOf(System.currentTimeMillis()).toString();
        this.name = name;
        this.photos = photos;
        this.location = location;
    }

    /// SERIALISATION Methods

    private static Plante fromMap(Map<String, Object> serialized) {
            return new Plante(
                    (String)serialized.get("name"),
                    Arrays.asList((String[]) Objects.requireNonNull(serialized.get("photos"))),
                    new Location((String)serialized.get("location"))
            );

    }

    public Map<String, Object> toMap() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("name", this.name);
        obj.put("photos", this.photos);
        obj.put("location", this.location.getProvider());
        return obj;
    }



    /// DATABASE Methods

    public void save(IEventHandler<Object> success, IEventHandler<Throwable> failure) {
        db.document(this.id).set(toMap(), SetOptions.merge()).addOnCompleteListener(r -> {
            success.onTrigger(null);
        }).addOnFailureListener(r -> {
            failure.onTrigger(r.getCause());
        });
    }


    public static void load(String id, IEventHandler<Plante> success, IEventHandler<Throwable> failure) {
        db.document(id).get().addOnCompleteListener(res -> {
            Plante plante = fromMap(Objects.requireNonNull(Objects.requireNonNull(res.getResult()).getData()));
            success.onTrigger(plante);
        })
        .addOnFailureListener(res -> {
            failure.onTrigger(res.getCause());
        });
    }
}
