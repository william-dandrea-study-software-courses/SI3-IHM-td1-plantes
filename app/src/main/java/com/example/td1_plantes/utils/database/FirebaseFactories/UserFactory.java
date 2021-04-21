package com.example.td1_plantes.utils.database.FirebaseFactories;

import com.example.td1_plantes.utils.IEventHandler;
import com.example.td1_plantes.utils.database.FirebaseObjectFactory;
import com.example.td1_plantes.utils.database.FirebaseObjects.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yann CLODONG
 */
public class UserFactory extends FirebaseObjectFactory<User> {
    @Override
    protected String getCollectionName() {
        return User.COLLECTION_NAME;
    }

    @Override
    protected User fromMap(Map<String, Object> map) {
        return new User(
                (String)map.get("id"),
                (String)map.get("email"),
                (String)map.get("username"),
                (String)map.get("avatar"),
                (List<String>)map.get("photos")
        );
    }

    public void getFromUsername(String username, IEventHandler<User> success, IEventHandler<Throwable> failure) {
        db.collection(getCollectionName()).whereEqualTo("username", username).get().addOnSuccessListener(r -> {
            if(r.isEmpty()) {
                failure.onTrigger(new RuntimeException("No user founded"));
                return;
            }
            User user = fromMap(Objects.requireNonNull(r.getDocuments().get(0).getData()));
            success.onTrigger(user);
        }).addOnFailureListener(failure::onTrigger);
    }
}
