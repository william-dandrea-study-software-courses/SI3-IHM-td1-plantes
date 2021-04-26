package com.example.td1_plantes.model.appobjects.smallelements;

public enum Fiability {

    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int id;
    private Fiability(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static Fiability fromId(int id) {
        switch (id) {
            case 0: return LOW;
            case 1: return MEDIUM;
            case 2: return HIGH;
            default: throw new RuntimeException("Unexpected fiability");
        }
    }
}
