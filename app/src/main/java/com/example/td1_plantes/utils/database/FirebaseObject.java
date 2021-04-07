package com.example.td1_plantes.utils.database;

import java.util.Map;

/**
 * @author Yann CLODONG
 */
public abstract class FirebaseObject {
    private final String id;

    public FirebaseObject(String id) {
        this.id = id;
    }

    /**
     * @return the id of the object
     */
    public String getObjectId() {
        return this.id;
    }

    /**
     * @return the name of the collection
     */
    protected abstract String getCollectionName();

    /**
     * @return This object serialized into a Map<String, Object>
     */
    public abstract Map<String, Object> toMap();
}
