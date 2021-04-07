package com.example.td1_plantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.td1_plantes.fragments.MyBottomBarFragment;

/**
 * @author D'Andrea William
 */
public class MapSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);

        // MENU
        //menuInitialization();

        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(1));
        ft2.commit();

    }
}