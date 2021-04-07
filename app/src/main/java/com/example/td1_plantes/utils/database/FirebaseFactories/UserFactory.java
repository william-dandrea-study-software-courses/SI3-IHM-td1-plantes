package com.example.td1_plantes.utils.database.FirebaseFactories;

import com.example.td1_plantes.utils.database.FirebaseObjectFactory;
import com.example.td1_plantes.utils.database.FirebaseObjects.User;

import java.util.Map;

/**
 * @author Yann CLODONG
 */
public class UserFactory extends FirebaseObjectFactory<User> {
    @Override
    public User fromMap(Map<String, Object> map) {
        return new User(
                (String)map.get("id"),
                (String)map.get("email"),
                (String)map.get("username")
        );
    }
}
