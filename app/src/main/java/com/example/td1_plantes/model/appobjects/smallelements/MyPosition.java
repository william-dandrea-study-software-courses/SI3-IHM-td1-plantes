package com.example.td1_plantes.model.appobjects.smallelements;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

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

    @NonNull
    @Override
    public String toString() {
        return this.lattitude + "," + this.longitude + "," + this.nameCity;
    }

    public static MyPosition parse(String value) {
        Log.d("MyPosition", value);
        /*Pattern pattern = Pattern.compile("\\{[ ,\t]*lattitude:[ ,\t]*(?<lattitude>[0-9,\\.]*),[ ,\t]*longitude:[ ,\t](?<longitude>[0-9,\\.]*),[ ,\t]*nameCity:[ ,\t]*\"(?<cityName>.*)\"[ ,\t]*\\}");
        Matcher matcher = pattern.matcher("value");*/
        String[] splitted = value.split(",");
        if(splitted.length != 2) {
            double lattitude, longitude;
            try {
                lattitude = Double.parseDouble(Objects.requireNonNull(splitted[0]));
            } catch(Throwable e) {
                throw new RuntimeException("Can't convert the given lattitude to a double");
            }
            try {
                longitude = Double.parseDouble(Objects.requireNonNull(splitted[1]));
            } catch(Throwable e) {
                throw new RuntimeException("Can't convert the given longitude to a double");
            }

            String cityName = splitted[2];
            return new MyPosition(lattitude, longitude, cityName);
        } else {
            throw  new RuntimeException("Pattern MyPosition doesn't fit the passed object.");
        }
    }
}
