package com.example.td1_plantes.controler.activities.mapsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * @author D'Andrea William
 */
public class MapSearchActivity extends AppCompatActivity {

    private MapView map;
    private ArrayList<OverlayItem> pointsOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setPointsOnMap();
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map_search);

        // MAP
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);    // Render
        map.setBuiltInZoomControls(true);               // Zoomable


        GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
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
                engageTheInfoBottomAppBar();
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);



        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(1));
        ft2.commit();


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




   void setPointsOnMap() {

       /*UserFactory userFactory = new UserFactory();
       userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", user -> {
           TextView welcome = findViewById(R.id.home_welcome);
           welcome.setText("Bienvenue, " + user.getUsername() + "!");
       }, error -> {

       });
        */

       this.pointsOnMap = new ArrayList<>();
       this.pointsOnMap.add(new OverlayItem("My Title", "My SubTittle", new GeoPoint(43.65020, 7.00517)));
       this.pointsOnMap.add(new OverlayItem("Resto", "Chez babar", new GeoPoint(43.6495, 7.00517)));


   }


   void engageTheInfoBottomAppBar() {
       BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapSearchActivity.this, R.style.BottomSheetDialogTheme);
       View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetContainer));

       bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Toast.makeText(MapSearchActivity.this, "Share ...", Toast.LENGTH_SHORT).show();
               bottomSheetDialog.dismiss();
           }
       });

       bottomSheetDialog.setContentView(bottomSheetView);
       bottomSheetDialog.show();
   }


}