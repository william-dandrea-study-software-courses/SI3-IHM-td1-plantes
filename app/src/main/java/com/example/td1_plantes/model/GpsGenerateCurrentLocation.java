package com.example.td1_plantes.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsGenerateCurrentLocation {


    FusedLocationProviderClient fusedLocationProviderClient;
    Context context;

    private double lattitude;
    private double longitude;
    private String locality;

    public GpsGenerateCurrentLocation(Context context) {

        this.context = context;

        this.lattitude = 0.0;
        this.longitude = 0.0;
        this.locality = "";

    }


    public MyPosition getUserCurrentPosition() {
        getTheUserLocation();
        return new MyPosition(lattitude, longitude, locality);
    }

    synchronized void getTheUserLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();

                    if (location != null) {

                        lattitude = location.getLatitude();
                        longitude = location.getLongitude();
                        locality = "Je sais pas";

                        System.out.println(lattitude);
                        System.out.println(longitude);
                        /*
                        try {


                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    1
                            );


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                         */

                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }





}
