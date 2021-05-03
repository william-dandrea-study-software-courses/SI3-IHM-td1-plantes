package com.example.td1_plantes.controler.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.td1_plantes.MainActivity;
import com.example.td1_plantes.controler.activities.WichActivitiesWeAre;
import com.example.td1_plantes.controler.activities.mapsearch.MapSearchActivity;
import com.example.td1_plantes.controler.activities.personnalfolder.PersonnalFolderActivity;
import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.takepicture.TakePictureActivity;
import com.example.td1_plantes.controler.activities.userprofil.UserProfilActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigationrail.NavigationRailView;

/**
 * @author D'Andrea William
 */
public class MyBottomBarFragment extends Fragment {

    private View view;
    private WichActivitiesWeAre selectedItem;

    public MyBottomBarFragment() {
        // Required empty public constructor
    }

    public MyBottomBarFragment(WichActivitiesWeAre selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_bottom_bar, container, false);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            menuInitializationTablet(selectedItem, view);
        }else{
            menuInitializationPhone(selectedItem, view);
        }




        return view;
    }




    private void menuInitializationPhone(WichActivitiesWeAre selectedItem, View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.profil_page:
                        startActivity(new Intent(getActivity().getApplicationContext(), UserProfilActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.map_page:
                        startActivity(new Intent(getActivity().getApplicationContext(), MapSearchActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.take_picture:
                        startActivity(new Intent(getActivity().getApplicationContext(), TakePictureActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.repository:
                        startActivity(new Intent(getActivity().getApplicationContext(), PersonnalFolderActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        BottomNavigationView bottomNavigationView2 = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView2.setBackground(null);
        bottomNavigationView2.getMenu().getItem(2).setEnabled(false);
        //R.id.profil_page

        switch (selectedItem) {
            case NOTHING: bottomNavigationView2.getMenu().getItem(2).setChecked(true); break;
            case HOME_PAGE: bottomNavigationView2.getMenu().getItem(2).setChecked(true); break;
            case USER_PAGE: bottomNavigationView2.getMenu().getItem(0).setChecked(true); break;
            case MAP_SEARCH_PAGE: bottomNavigationView2.getMenu().getItem(1).setChecked(true); break;
            case REPOSITORY_PAGE: bottomNavigationView2.getMenu().getItem(4).setChecked(true); break;
            case TAKE_PICTURE_PAGE: bottomNavigationView2.getMenu().getItem(3).setChecked(true); break;
            default:break;
        }




        // Floating Action Button qui retourne vers la page d'acceuil
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().overridePendingTransition(0,0);
            }
        });
    }


    private void menuInitializationTablet(WichActivitiesWeAre selectedItem, View view) {
        NavigationRailView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);




        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationRailView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_homepage:
                        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.profil_page:
                        startActivity(new Intent(getActivity().getApplicationContext(), UserProfilActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.map_page:
                        startActivity(new Intent(getActivity().getApplicationContext(), MapSearchActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.take_picture:
                        startActivity(new Intent(getActivity().getApplicationContext(), TakePictureActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                    case R.id.repository:
                        startActivity(new Intent(getActivity().getApplicationContext(), PersonnalFolderActivity.class));
                        getActivity().overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }


        });


        //R.id.profil_page



        switch (selectedItem) {
            case NOTHING: {
                for (int i = 0; i <= 4; i++) {
                    bottomNavigationView.getMenu().getItem(i).setChecked(false);
                }

                break;
            }
            case HOME_PAGE: bottomNavigationView.getMenu().getItem(0).setChecked(true); break;
            case USER_PAGE: bottomNavigationView.getMenu().getItem(1).setChecked(true); break;
            case MAP_SEARCH_PAGE: bottomNavigationView.getMenu().getItem(2).setChecked(true); break;
            case REPOSITORY_PAGE: bottomNavigationView.getMenu().getItem(4).setChecked(true); break;
            case TAKE_PICTURE_PAGE: bottomNavigationView.getMenu().getItem(3).setChecked(true); break;
            default:break;
        }



    }
}