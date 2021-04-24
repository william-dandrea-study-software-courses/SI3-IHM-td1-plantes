package com.example.td1_plantes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.td1_plantes.MainActivity;
import com.example.td1_plantes.activities.mapsearch.MapSearchActivity;
import com.example.td1_plantes.activities.personnalfolder.PersonnalFolderActivity;
import com.example.td1_plantes.R;
import com.example.td1_plantes.activities.takepicture.TakePictureActivity;
import com.example.td1_plantes.activities.userprofil.UserProfilActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author D'Andrea William
 */
public class MyBottomBarFragment extends Fragment {

    private View view;
    private int selectedItem;

    public MyBottomBarFragment() {
        // Required empty public constructor
    }

    public MyBottomBarFragment(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_bottom_bar, container, false);
        menuInitialization(selectedItem, view);
        return view;
    }




    private void menuInitialization(int selectedItem, View view) {
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
        bottomNavigationView2.getMenu().getItem(selectedItem).setChecked(true);


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
}