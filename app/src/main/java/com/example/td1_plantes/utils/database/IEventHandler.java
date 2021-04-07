package com.example.td1_plantes.utils.database;

/**
 * @author Yann CLODONG
 */
public interface IEventHandler<T> {
    void onTrigger(T value);
}
