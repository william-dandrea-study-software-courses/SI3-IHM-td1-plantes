package com.example.td1_plantes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.controler.fragments.OpenStreetMapFragment;
import com.example.td1_plantes.controler.fragments.TitleYellowDescriberDivFragment;
import com.example.td1_plantes.controler.fragments.homefragments.NewsDivHomeFragment;
import com.example.td1_plantes.controler.fragments.homefragments.PlantListHomePageFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.Mocks;
import com.example.td1_plantes.model.appobjects.News;
import com.example.td1_plantes.model.appobjects.Plant;

import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * @author D'Andrea William
 */
public class MainActivity extends AppCompatActivity {


    News[] newsListOnHome = Mocks.LIST_OF_NEWS;

    Plant[] publicPlantsForHome;
    List<OverlayItem> locationsPointsMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // gpsGenerateCurrentLocation = new GpsGenerateCurrentLocation(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Mocks.PushOnDatabase();

        GestionDatabase.loadUser("");



        GestionDatabase.getAllPublicPlants(plants -> {
            List<Plant> publicPlantsTemp = plants.subList(0, Math.min(plants.size(), 2));
            publicPlantsForHome = new Plant[publicPlantsTemp.size()];
            publicPlantsTemp.toArray(publicPlantsForHome);

            // frame_layout_homepage_plants

            FragmentManager managerIntern = getSupportFragmentManager();
            FragmentTransaction transactionIntern = managerIntern.beginTransaction();
            transactionIntern.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));
            transactionIntern.commit();
        });




        GestionDatabase.getAllPublicPlantsOnMap(plants -> {
            locationsPointsMap = plants;
        });







        // publicPlantsForHome = new Plant[Mocks.plants.size()];
        // publicPlantsForHome = Mocks.plants.toArray(new Plant[0]);

        // transaction.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));


        // GENERATE THE NEWS DIV FRAGMENT
        transaction.add(R.id.frame_layout_homepage_news, new NewsDivHomeFragment(newsListOnHome));

        // frame_layout_homepage_plants
        transaction.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));

        transaction.add(R.id.frame_layout_homepage_map, new OpenStreetMapFragment());

        transaction.add(R.id.frame_layout_homepage_map_title, new TitleYellowDescriberDivFragment("Plantes proche"));

        //bottom_app_bar
        transaction.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));

        transaction.commit();



        /*
        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", user -> {
            TextView welcome = findViewById(R.id.home_welcome);
            welcome.setText("Bienvenue, " + user.getUsername() + "!");
        }, error -> {

        });

         */
    }



}