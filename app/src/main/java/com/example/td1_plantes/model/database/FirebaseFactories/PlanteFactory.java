package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.database.FirebaseObjectFactory;
import com.example.td1_plantes.model.database.FirebaseObjects.Plante;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public class PlanteFactory extends FirebaseObjectFactory<Plante> {
    @Override
    protected String getCollectionName() {
        return Plante.COLLECTION_NAME;
    }

    @Override
    protected Plante fromMap(Map<String, Object> map) {
        return new Plante(
                (String)map.get("id"),
                (String)map.get("name"),
                Arrays.asList((String[]) Objects.requireNonNull(map.get("photos")))
        );
    }
}
