package com.example.td1_plantes.controler.activities.personnalfolder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.utils.recyclerlists.PlantRecyclerAdapter;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;

/**
 * @author D'Andrea William
 */
public class PersonnalFolderActivity extends AppCompatActivity {


    User currentUser;

    RecyclerView recyclerViewOne;
    PlantRecyclerAdapter adapterOne;
    Plant[] vos_plantes;

    RecyclerView recyclerViewTwo;
    PlantRecyclerAdapter adapterTwo;
    Plant[] public_plants;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnal_folder);

        currentUser = GestionDatabase.getCurrentUser();

        GestionDatabase.getAllPrivatePlantsFromOneUser(currentUser.getUserId(), plants -> {
            System.out.println("USER :: " + currentUser.getUserId().toString());
            System.out.println("PLANTS YOOOO : " + plants.toString());

            vos_plantes = plants.toArray(new Plant[0]);

            // "VOS PLANTES" Recycler View
            recyclerViewOne = findViewById(R.id.recycler_view_vos_plantes);
            recyclerViewOne.setLayoutManager(new LinearLayoutManager(this));

            adapterOne = new PlantRecyclerAdapter(vos_plantes, this,true);
            recyclerViewOne.setAdapter(adapterOne);
        });



        GestionDatabase.getAllPublicPlants(plants -> {
            public_plants = plants.toArray(new Plant[0]);

            // "REPERTOIRE PUBLIC" Recycler View
            recyclerViewTwo = findViewById(R.id.recycler_view_repertoire_public);
            recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this));
            adapterTwo = new PlantRecyclerAdapter(public_plants, this,false);
            recyclerViewTwo.setAdapter(adapterTwo);
        });






        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft2.commit();
    }
}