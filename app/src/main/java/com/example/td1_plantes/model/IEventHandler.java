package com.example.td1_plantes.model;

/**
 * @author Yann CLODONG
 */
public interface IEventHandler<T> {
    void onTrigger(T value);
}
