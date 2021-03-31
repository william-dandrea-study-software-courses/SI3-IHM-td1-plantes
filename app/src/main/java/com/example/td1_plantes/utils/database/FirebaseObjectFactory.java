package com.example.td1_plantes.utils.database;

import java.util.Map;

public abstract class FirebaseObjectFactory<T extends FirebaseObject> {
    abstract T fromMap(Map<String, Object> map);
}
