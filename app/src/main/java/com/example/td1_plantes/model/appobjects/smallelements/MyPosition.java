package com.example.td1_plantes.model.appobjects.smallelements;

public class MyPosition {

    private double lattitude;
    private double longitude;
    private String nameCity;

    public MyPosition(double lattitude, double longitude, String nameCity) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.nameCity = nameCity;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNameCity() {
        return nameCity;
    }
}
