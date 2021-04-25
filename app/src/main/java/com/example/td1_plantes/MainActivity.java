package com.example.td1_plantes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.controler.fragments.OpenStreetMapFragment;
import com.example.td1_plantes.controler.fragments.homefragments.NewsDivHomeFragment;
import com.example.td1_plantes.controler.fragments.homefragments.PlantListHomePageFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.GpsGenerateCurrentLocation;
import com.example.td1_plantes.model.Mocks;
import com.example.td1_plantes.model.appobjects.News;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.example.td1_plantes.model.database.FirebaseFactories.UserFactory;

import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * @author D'Andrea William
 */
public class MainActivity extends AppCompatActivity {


    News[] newsListOnHome = Mocks.LIST_OF_NEWS;

    Plant[] publicPlantsForHome;
    List<OverlayItem> locationsPointsMap;
    MyPosition myPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Plant> publicPlantsTemp = GestionDatabase.getAllPublicPlants().subList(0, Math.min(GestionDatabase.getAllPublicPlants().size(), 2));
        publicPlantsForHome = new Plant[publicPlantsTemp.size()];
        publicPlantsTemp.toArray(publicPlantsForHome);

        locationsPointsMap = GestionDatabase.getAllPublicPlantsOnMap();
        myPosition = (new GpsGenerateCurrentLocation(this)).getUserCurrentPosition();

        // myPosition = new MyPosition(0,0,"salut");

        // GENERATE THE NEWS DIV FRAGMENT
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_layout_homepage_news, new NewsDivHomeFragment(newsListOnHome));
        ft.commit();


        // frame_layout_homepage_plants
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.frame_layout_homepage_plants, new PlantListHomePageFragment(publicPlantsForHome));
        ft2.commit();


        TextView txtTemp = (TextView) findViewById(R.id.super_temporaire);
        txtTemp.setText((myPosition.getLattitude() + " " + myPosition.getLongitude()));

        // frame_layout_homepage_map
        /*
        FragmentManager fm4 = getSupportFragmentManager();
        FragmentTransaction ft4 = fm4.beginTransaction();
        ft4.add(R.id.frame_layout_homepage_map, new OpenStreetMapFragment(locationsPointsMap, myPosition));
        ft4.commit();
*/

        //bottom_app_bar
        FragmentManager fm3 = getSupportFragmentManager();
        FragmentTransaction ft3 = fm3.beginTransaction();
        ft3.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft3.commit();



        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", user -> {
            TextView welcome = findViewById(R.id.home_welcome);
            welcome.setText("Bienvenue, " + user.getUsername() + "!");
        }, error -> {

        });
    }



}