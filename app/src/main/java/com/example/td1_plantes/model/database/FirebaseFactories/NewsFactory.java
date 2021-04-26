package com.example.td1_plantes.model.database.FirebaseFactories;

import com.example.td1_plantes.model.appobjects.News;
import com.example.td1_plantes.model.database.FirebaseObjectFactory;

import java.util.Map;

public class NewsFactory extends FirebaseObjectFactory<News> {
    @Override
    protected String getCollectionName() {
        return News.COLLECTION_NAME;
    }

    @Override
    protected News fromMap(Map<String, Object> map) {
        return new News(
                (String)map.get("title"),
                (String)map.get("description"),
                (String)map.get("date"),
                (String)map.get("imageURL"),
                (String)map.get("author")
        );
    }
}
