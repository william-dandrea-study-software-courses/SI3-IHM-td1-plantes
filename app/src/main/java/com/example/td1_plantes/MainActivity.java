package com.example.td1_plantes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
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

        //GestionDatabase.loadUser("Delaroche", this::refresh);


        TextView welcomePhrase = (TextView) findViewById(R.id.home_welcome);
        welcomePhrase.setText("Bonjour, " + GestionDatabase.getCurrentUser().getSurname());


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


        Button switchUserPassionateOneButton = findViewById(R.id.activity_main_switch_user_passionate_1);
        Button switchUserExpertOneButton = findViewById(R.id.activity_main_switch_user_expert_1);
        Button switchUserPassionateTwoButton = findViewById(R.id.activity_main_switch_user_passionate_2);
        Button switchUserExpertTwoButton = findViewById(R.id.activity_main_switch_user_expert_2);

        switchUserPassionateOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.loadUser("Dumarchant", () -> refresh());
            }
        });

        switchUserPassionateTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.loadUser("Delaroche", () -> refresh());
            }
        });

        switchUserExpertOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.loadUser("Fanzi", () -> refresh());
            }
        });

        switchUserExpertOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.loadUser("Lavalant", () -> refresh());
            }
        });

        ((Button)findViewById(R.id.refresh_button)).setOnClickListener((v) -> refresh());




        // publicPlantsForHome = new Plant[Mocks.plants.size()];
        // publicPlantsForHome = Mocks.plants.toArray(new Plant[0]);

        // transaction.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));


        // GENERATE THE NEWS DIV FRAGMENT
        transaction.add(R.id.frame_layout_homepage_news, new NewsDivHomeFragment(newsListOnHome));

        // frame_layout_homepage_plants
        transaction.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));


        transaction.add(R.id.frame_layout_homepage_map_title, new TitleYellowDescriberDivFragment("Plantes proche"));

        //bottom_app_bar
        transaction.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));

        transaction.commit();




        GestionDatabase.getAllPublicPlants(plantsPublic -> {
            GestionDatabase.getAllPrivatePlants(plantsPrivate -> {

                List<Plant> goodPlants = plantsPublic;
                goodPlants.addAll(plantsPrivate);

                List<OverlayItem> pointsOnMap = new ArrayList<>();

                for (Plant plant : goodPlants) {

                    pointsOnMap.add(new OverlayItem(plant.getTitle(), plant.getPublicationDate(), new GeoPoint(plant.getMyPosition().getLattitude(), plant.getMyPosition().getLongitude())));

                }

                FragmentManager managerIntern = getSupportFragmentManager();
                FragmentTransaction transactionIntern = managerIntern.beginTransaction();
                transactionIntern.add(R.id.frame_layout_homepage_map, new OpenStreetMapFragment(pointsOnMap));
                transactionIntern.commit();

            });
        });


        /*
        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", user -> {
            TextView welcome = findViewById(R.id.home_welcome);
            welcome.setText("Bienvenue, " + user.getUsername() + "!");
        }, error -> {

        });

         */
    }

    private void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }



}