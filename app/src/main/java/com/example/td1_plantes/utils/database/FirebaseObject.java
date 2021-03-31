package com.example.td1_plantes.utils.database;

import java.util.Map;

public abstract class FirebaseObject {
    public abstract Map<String, Object> toMap();

    public abstract void save(IEventHandler<Object> success, IEventHandler<Throwable> exception);
}
