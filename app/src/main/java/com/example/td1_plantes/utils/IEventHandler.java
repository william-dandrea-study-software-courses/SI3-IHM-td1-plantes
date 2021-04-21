package com.example.td1_plantes.utils;

/**
 * @author Yann CLODONG
 */
public interface IEventHandler<T> {
    void onTrigger(T value);
}
