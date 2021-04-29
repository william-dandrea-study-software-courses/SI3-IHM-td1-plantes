package com.example.td1_plantes.controler.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class OpenStreetMapFragment extends Fragment {

    private MapView mapView;
    private IMapController mapController;


    private boolean setCenterInUserLocation = true;


    private double centerLongitude;
    private double centerLatitude;

    public OpenStreetMapFragment() {}



    public OpenStreetMapFragment(MyPosition centerPosition) {
        this.setCenterInUserLocation = false;
        this.centerLongitude = centerPosition.getLongitude();
        this.centerLatitude = centerPosition.getLattitude();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_street_map, container, false);


      

        Button right = view.findViewById(R.id.open_street_map_fragment_map_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         

                centerLongitude -= 0.1;

                mapController.animateTo(new GeoPoint(centerLatitude, centerLongitude));
            }
        });

        Button left = view.findViewById(R.id.open_street_map_fragment_map_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           

                centerLongitude += 0.1;
                mapController.animateTo(new GeoPoint(centerLatitude, centerLongitude));
            }
        });

        Button top = view.findViewById(R.id.open_street_map_fragment_map_haut);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             

                centerLatitude -= 0.1;
                mapController.animateTo(new GeoPoint(centerLatitude, centerLongitude));
            }
        });

        Button bottom = view.findViewById(R.id.open_street_map_fragment_map_bas);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        

                centerLatitude += 0.1;
                mapController.animateTo(new GeoPoint(centerLatitude, centerLongitude));
            }
        });



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (setCenterInUserLocation) {
            generateAutorisationAndGeneratePosition();
        } else {
            dispMap();
        }


    }


    private void dispMap() {
        // 1 // SETUP DE LA MAP
        mapView = getView().findViewById(R.id.open_street_map_fragment_map);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);


        CustomZoomButtonsController zoomController = mapView.getZoomController();
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);



        // 2 // POSITIONNEMENT DE LA VUE
        mapController = mapView.getController(); // Le controlleur est le truc qui permet de faire des animations, des trucs sympa sur les elements de la map
        mapController.setZoom(10.0);
        mapController.setCenter(new GeoPoint(centerLatitude, centerLongitude));


        // 3 // AJOUT DES OVERLAYS : Les marqueurs ...

        // => Ajout de l'échelle de distance
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(scaleBarOverlay);
        mapView.invalidate();

        // => Ajout des points sur la map
        List<OverlayItem> tempOverlaysPoints = new ArrayList<OverlayItem>() {{
            add(new OverlayItem("overlay1", "overlay1desc", new GeoPoint(43.6, 7.25)));
            add(new OverlayItem("overlay1", "overlay1desc", new GeoPoint(43.7, 7.26)));
        }};

        // Ajout réel des points sur la map + affectation des actions a faire si on clique sur un overlay.
        ItemizedOverlay<OverlayItem> overlaysOnMap = new ItemizedIconOverlay<OverlayItem>(getContext(), tempOverlaysPoints, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });


        mapView.setFlingEnabled(true);


        mapView.getOverlays().add(overlaysOnMap);
        mapView.invalidate();
    }























































    FusedLocationProviderClient client;



    private void generateAutorisationAndGeneratePosition() {

        client = LocationServices.getFusedLocationProviderClient(getActivity());

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

                        centerLatitude = location.getLatitude();
                        centerLongitude = location.getLongitude();

                        dispMap();
                    } else {
                        // Si la localisation est null, on fait une requete pour autorisé la localisation
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                centerLatitude = location1.getLatitude();
                                centerLongitude = location1.getLongitude();

                                dispMap();

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