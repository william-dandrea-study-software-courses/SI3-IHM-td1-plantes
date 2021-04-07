package com.example.td1_plantes.utils.database.FirebaseFactories;

import android.location.Location;

import com.example.td1_plantes.utils.database.FirebaseObjectFactory;
import com.example.td1_plantes.utils.database.FirebaseObjects.Observation;

import java.util.List;
import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class ObservationFactory extends FirebaseObjectFactory<Observation> {
    @Override
    public Observation fromMap(Map<String, Object> map) {
        return new Observation(
                (String)map.get("id"),
                (String)map.get("author"),
                (String)map.get("plante"),
                new Location((String)map.get("location")),
                (List<String>)map.get("photos")
        );
    }
}
