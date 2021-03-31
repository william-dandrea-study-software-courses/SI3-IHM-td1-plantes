package com.example.td1_plantes.utils.database;

public interface IEventHandler<T> {
    void onTrigger(T value);
}
