package com.example.td1_plantes.utils.database.FirebaseFactories;

import com.example.td1_plantes.utils.database.FirebaseObjectFactory;
import com.example.td1_plantes.utils.database.FirebaseObjects.Plante;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public class PlanteFactory extends FirebaseObjectFactory<Plante> {
    @Override
    public Plante fromMap(Map<String, Object> map) {
        return new Plante(
                (String)map.get("id"),
                (String)map.get("name"),
                Arrays.asList((String[]) Objects.requireNonNull(map.get("photos")))
        );
    }
}
