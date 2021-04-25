package com.example.td1_plantes.controler.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.utils.recyclerlists.NewsRecyclerAdapter;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class OpenStreetMapFragment extends Fragment {


    private MapView mapView;
    private List<OverlayItem> pointsOnMap;
    private MyPosition centerPosition;

    public OpenStreetMapFragment(List<OverlayItem> pointsOnMap, MyPosition centerPosition) {
        this.pointsOnMap = pointsOnMap;
        this.centerPosition = centerPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_street_map, container, false);
    }

    // fragment_open_street_map_main_layout
    // fragment_open_street_map_mapId
    // getView().findViewById
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));
        Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());
        getActivity().setContentView(R.layout.fragment_open_street_map);

        // MAP
        mapView = getView().findViewById(R.id.fragment_open_street_map_mapId);
        mapView.setTileSource(TileSourceFactory.MAPNIK);    // Render
        mapView.setBuiltInZoomControls(true);               // Zoomable

        GeoPoint startPoint = new GeoPoint(this.centerPosition.getLattitude(), this.centerPosition.getLongitude());
        IMapController mapController = mapView.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getContext(), this.pointsOnMap, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        mapView.getOverlays().add(mOverlay);

    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
