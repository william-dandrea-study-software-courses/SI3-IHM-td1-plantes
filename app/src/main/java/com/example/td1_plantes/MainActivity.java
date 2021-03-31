package com.example.td1_plantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.td1_plantes.fragments.MyBottomBarFragment;
import com.example.td1_plantes.fragments.homefragments.NewsDivHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author D'Andrea William
 */
public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        // GENERATE THE NEWS DIV FRAGMENT
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_layout_main, new NewsDivHomeFragment());
        ft.commit();


        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft2.commit();

    }



}