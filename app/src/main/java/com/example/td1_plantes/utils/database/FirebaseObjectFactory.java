package com.example.td1_plantes.utils.database;

import java.util.Map;

/**
 * @author Yann CLODONG
 */
public abstract class FirebaseObjectFactory<T extends FirebaseObject> {

    /**
     * Deserialize the map to the object
     * @param map The map in witch retrive datas
     * @return The object builded
     */
    public abstract T fromMap(Map<String, Object> map);
}
