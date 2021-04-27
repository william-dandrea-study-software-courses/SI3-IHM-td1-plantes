package com.example.td1_plantes.controler.fragments;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.td1_plantes.R;

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

    private double centerLongitude;
    private double centerLatitude;

    public OpenStreetMapFragment() {}


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

        // open_street_map_fragment_map


        // 1 // SETUP DE LA MAP
        mapView = getView().findViewById(R.id.open_street_map_fragment_map);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);


        CustomZoomButtonsController zoomController = mapView.getZoomController();
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);


        // 2 // POSITIONNEMENT DE LA VUE
        centerLatitude = 43.700000;
        centerLongitude = 7.250000;
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


        /*
        mapView.addMapListener(new MapListener() {

            @Override
            public boolean onScroll(ScrollEvent event) {
                mapController.animateTo(new GeoPoint(centerLatitude + event.getX(), centerLongitude + event.getY()));
                mapView.invalidate();
                return true;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                mapController.setZoom(event.getZoomLevel());
                mapView.invalidate();
                return true;
            }
        });

         */




        mapView.getOverlays().add(overlaysOnMap);
        mapView.invalidate();










    }




}