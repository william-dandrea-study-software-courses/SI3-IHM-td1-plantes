package com.example.td1_plantes.controler.activities.plantpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.td1_plantes.MainActivity;
import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;

import java.util.UUID;

public class PlantPageActivity extends AppCompatActivity {

    Plant currentPlant;
    User currentUser;


    // Pour créer la nouvelle activité et passer un paramètres, utiliser ces lignes :
    // Intent i = new Intent(this, PlantPageActivity.class);
    // i.putExtra("plantID", id); -> id doit etre egal a l'UUID de la plante
    // startActivity(i);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);

        // Récuperation du paramètre plantID && du currentUser
        Bundle bundle = getIntent().getExtras();
        UUID plantID = (UUID) bundle.get("plantID");
        currentPlant = GestionDatabase.getRealPlant(plantID).get();

        currentUser = GestionDatabase.getCurrentUser();








        //bottom_app_bar
        FragmentManager fm3 = getSupportFragmentManager();
        FragmentTransaction ft3 = fm3.beginTransaction();
        ft3.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft3.commit();
    }



}