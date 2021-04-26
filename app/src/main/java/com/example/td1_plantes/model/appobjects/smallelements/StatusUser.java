package com.example.td1_plantes.model.appobjects.smallelements;

public enum StatusUser {

    PASSIONATE(0),
    EXPERT(1);

    private final int id;
    private StatusUser(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StatusUser fromId(int id) {
        switch (id) {
            case 0: return PASSIONATE;
            case 1: return EXPERT;
            default: throw new RuntimeException("Unexpected user status");
        }
    }
}
