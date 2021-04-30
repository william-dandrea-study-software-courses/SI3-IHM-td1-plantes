package com.example.td1_plantes.controler.activities.mapsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.plantpage.PlantPageActivity;
import com.example.td1_plantes.controler.fragments.GenerateCurrentLocalisationFragment;
import com.example.td1_plantes.controler.fragments.GenerateCurrentPositionAndDispNothingFragment;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.model.DownloadImageTask;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.smallelements.Fiability;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author D'Andrea William
 */
public class MapSearchActivity extends AppCompatActivity {

    private MapView map;
    private ArrayList<OverlayItem> pointsOnMap;
    private List<Plant> plantsOnMap;

    private double currentLattitude;
    private double currentLongitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map_search);

        // MAP
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);    // Render
        map.setBuiltInZoomControls(true);               // Zoomable

        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(1));
        ft2.commit();


        this.pointsOnMap = new ArrayList<>();
        this.plantsOnMap = new ArrayList<>();

        GestionDatabase.getAllPublicPlants(plantsPublic -> {
            GestionDatabase.getAllPrivatePlants(plantsPrivate -> {

                List<Plant> goodPlants = plantsPublic;
                goodPlants.addAll(plantsPrivate);


                for (Plant plant : goodPlants) {

                    OverlayItem currentItem = new OverlayItem(plant.getTitle(), plant.getPublicationDate(), new GeoPoint(plant.getMyPosition().getLattitude(), plant.getMyPosition().getLongitude()));
                    this.pointsOnMap.add(currentItem);
                    this.plantsOnMap.add(plant);

                }
                generateAutorisationAndGeneratePosition();

            });
        });




    }


    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }










    void engageTheInfoBottomAppBar(OverlayItem item) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapSearchActivity.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetContainer));


        Plant plant = null;

        for (int i = 0; i < this.pointsOnMap.size(); i++) {
            if (this.pointsOnMap.get(i).equals(item)) {
                plant = this.plantsOnMap.get(i);
            }
        }

        if (plant != null) {

            Plant finalPlant = plant;

            // ImageView plantImageView = (ImageView) bottomSheetDialog.findViewById(R.id.layout_botom_sheet_image);
            new DownloadImageTask((ImageView) bottomSheetView.findViewById(R.id.layout_botom_sheet_image)).execute(finalPlant.getImageURL());


            TextView titleTextView = (TextView) bottomSheetView.findViewById(R.id.layout_bootom_sheet_title);
            titleTextView.setText(finalPlant.getTitle());


            TextView publicOrPrivateTextView = (TextView) bottomSheetView.findViewById(R.id.layout_bootom_sheet_public_or_private);
            LinearLayout infosLinearLayout = (LinearLayout) bottomSheetView.findViewById(R.id.layout_bootom_sheet_review_infos);

            if (finalPlant.isPublic()) {
                publicOrPrivateTextView.setText("PLANTE PUBLIQUE");
            } else {
                publicOrPrivateTextView.setText("PLANTE PRIVEE");
                infosLinearLayout.setVisibility(View.GONE);
            }

            GestionDatabase.findAuthorOfOnePlant(plant.getIdPlant(), users -> {
                TextView dateTextView = bottomSheetView.findViewById(R.id.layout_bootom_sheet_date_and_user);
                dateTextView.setText("Crée le " + finalPlant.getPublicationDate() + " par " + users.get(0).getSurname() + " " + users.get(0).getName());
            });

            GestionDatabase.getContributionsForOnePlant(finalPlant.getIdPlant(), contributions -> {

                TextView contributorsTextView = bottomSheetView.findViewById(R.id.layout_bootom_sheet_number_of_contributors_id);
                contributorsTextView.setText(String.valueOf(contributions.size()));

            });

            GestionDatabase.getNumberOfPositiveReviewForOnePlant(finalPlant.getIdPlant(), numberOfPositivReview -> {


                GestionDatabase.getContributionsForOnePlant(finalPlant.getIdPlant(), contributions -> {

                    TextView reviewTextView = bottomSheetView.findViewById(R.id.playout_bootom_sheet_positive_review_id);

                    String dispResult = (numberOfPositivReview == 0 || contributions.size() == 0) ? "0" : String.valueOf((int) (contributions.size() / numberOfPositivReview ));

                    reviewTextView.setText(dispResult);

                });
            });


            // INITIALISATION DE LA FIABILITE
            GestionDatabase.getFiabilityForOnePlant(finalPlant.getIdPlant(), fiabilityElemObj -> {
                TextView fiabilityElem = bottomSheetView.findViewById(R.id.layout_bootom_sheet_fiability_level_id);
                String fiabilityElemString = "";
                if (fiabilityElemObj == Fiability.LOW)
                    fiabilityElemString = "NON";
                if (fiabilityElemObj == Fiability.MEDIUM)
                    fiabilityElemString = "PEU";
                if (fiabilityElemObj == Fiability.HIGH)
                    fiabilityElemString = "TRES";
                fiabilityElem.setText(fiabilityElemString);
            });










            bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(MapSearchActivity.this, "Share ...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), PlantPageActivity.class);
                    i.putExtra("plantID", finalPlant.getIdPlant());
                    startActivity(i);

                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }


    }



    FusedLocationProviderClient client;



    private void generateAutorisationAndGeneratePosition() {

        client = LocationServices.getFusedLocationProviderClient(this);

        // On check d'abord les autorisations
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
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
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Initialisation du locationManager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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

                        currentLattitude = location.getLatitude();
                        currentLongitude = location.getLongitude();

                        dispMap();
                    } else {
                        // Si la localisation est null, on fait une requete pour autorisé la localisation
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                currentLattitude = location1.getLatitude();
                                currentLongitude = location1.getLongitude();

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




    public void dispMap() {



        // GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
        GeoPoint startPoint = new GeoPoint(currentLattitude, currentLongitude);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        //ArrayList<OverlayItem> items = new ArrayList<>();
        //OverlayItem home = new OverlayItem("My Title", "My SubTittle", new GeoPoint(43.65020, 7.00517));
        //Drawable m = home.getMarker(0);
        //items.add(home);
        //items.add(new OverlayItem("Resto", "Chez babar", new GeoPoint(43.6495, 7.00517)));



        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), this.pointsOnMap, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                System.out.println(index);
                engageTheInfoBottomAppBar(item);
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);

    }
















}