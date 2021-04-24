package com.example.td1_plantes.model.appobjects.smallelements;

public class Position {

    private double lattitude;
    private double longitude;

    public Position(double lattitude, double longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
