package com.example.td1_plantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TakePictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        // MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.profil_page:
                        startActivity(new Intent(getApplicationContext(), UserProfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.map_page:
                        startActivity(new Intent(getApplicationContext(), MapSearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.take_picture:
                        startActivity(new Intent(getApplicationContext(), TakePictureActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.repository:
                        startActivity(new Intent(getApplicationContext(), PersonnalFolderActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        BottomNavigationView bottomNavigationView2 = findViewById(R.id.bottomNavigationView);
        bottomNavigationView2.setBackground(null);
        bottomNavigationView2.getMenu().getItem(2).setEnabled(false);

        bottomNavigationView2.getMenu().getItem(3).setChecked(true);


        // Floating Action Button qui retourne vers la page d'acceuil
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0,0);
            }
        });
    }
}