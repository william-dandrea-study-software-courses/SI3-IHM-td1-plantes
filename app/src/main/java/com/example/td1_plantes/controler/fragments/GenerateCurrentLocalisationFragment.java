package com.example.td1_plantes.controler.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.td1_plantes.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class GenerateCurrentLocalisationFragment extends Fragment {


    private TextView latitudeText, longitudeText, adressText;
    private Button button;

    FusedLocationProviderClient client;

    public GenerateCurrentLocalisationFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_current_localisation, container, false);

        // Assign variables
        button = view.findViewById(R.id.fragment_generate_current_localisation_button);
        latitudeText = view.findViewById(R.id.fragment_generate_current_localisation_lattitude);
        longitudeText = view.findViewById(R.id.fragment_generate_current_localisation_longitude);

        // Initialze location client
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // On check d'abord les autorisations
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {
                    // SI on a l'autorisation, on recupere la localisation
                    getCurrentLocation();
                } else {
                    // Quand on a pas l'autorisation, on demande la permission
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }

            }
        });


        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check condition
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            // Lorsque les permissions sont données
            getCurrentLocation();
        } else {
            // Quand les permissions sont denied, mettre un toast
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Initialisation du locationManager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Quand le service de localisation est activé, on récupère la derniere localisation

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    // Initialisation de la localisation
                    Location location = task.getResult();

                    // Check condition
                    if (location != null) {
                        // Quand la localisation est non nul, on affiche la position
                        latitudeText.setText(String.valueOf(location.getLatitude()));
                        longitudeText.setText(String.valueOf(location.getLongitude()));
                    } else {
                        // Si la localisation est null, on fait une requete pour autorisé la localisation
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                latitudeText.setText(String.valueOf(location1.getLatitude()));
                                longitudeText.setText(String.valueOf(location1.getLongitude()));
                            }
                        };

                        // Request location update
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                    }

                }
            });


        } else {
            // When Location Service is not enable : open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

}


