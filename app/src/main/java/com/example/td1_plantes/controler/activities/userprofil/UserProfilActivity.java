package com.example.td1_plantes.controler.activities.userprofil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.WichActivitiesWeAre;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.LoadImageInBackground;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.UserAndPlant;
import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.td1_plantes.model.interfaces.IFacebookShare.SHARE_CODE;

/**
 * @author D'Andrea William
 */
public class UserProfilActivity extends AppCompatActivity {

    private String currentUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);




        load();

        // MENU
        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(WichActivitiesWeAre.USER_PAGE));
        ft2.commit();
    }


    private void load() {
        TextView usernameHolder = findViewById(R.id.username_profile);
        TextView photoCountHolder = findViewById(R.id.photo_number_holder);
        TextView contributionCount = findViewById(R.id.contribution_count_holder);
        ImageView avatarHolder = findViewById(R.id.avatar_profil);

        TextView user_status_text = findViewById(R.id.user_status_text);
        ImageView user_status_icon = findViewById(R.id.user_status_icon);

        GridView photos = findViewById(R.id.photos_mosaic);


        GestionDatabase.getContributionsForOneContributor(GestionDatabase.getCurrentUser().getUserId(), contribution -> {
            contributionCount.setText(String.valueOf(contribution.size()));
        });




        usernameHolder.setText(GestionDatabase.getCurrentUser().getSurname() + " " +GestionDatabase.getCurrentUser().getName() );



        GestionDatabase.getAllPlants(plants -> {
            GestionDatabase.getAllUserAndPlant(userAndPlants -> {

                List<Plant> userPlant = new ArrayList<>();

                for (Plant plant : plants) {
                    for (UserAndPlant userAndPlant : userAndPlants) {

                        if (plant.getIdPlant().equals(userAndPlant.getPlant()) && userAndPlant.getUser().equals(GestionDatabase.getCurrentUser().getUserId())) {
                            userPlant.add(plant);
                        }
                    }
                }

                photoCountHolder.setText(String.valueOf(userPlant.size()));
                photos.setAdapter(new PhotoAdapter(getApplicationContext(), userPlant.stream().map(Plant::getImageURL).collect(Collectors.toList())));

            });
        });


        if(GestionDatabase.getCurrentUser().getStatusUser() == StatusUser.EXPERT) {
            user_status_text.setText("Expert");
            user_status_icon.setImageResource(R.drawable.paon);
        } else {
            user_status_text.setText("Passioné");
            user_status_icon.setImageResource(R.drawable.perroquet);
        }



        new LoadImageInBackground(avatarHolder, () -> Log.d("LOAD", "ERror !")).execute(GestionDatabase.getCurrentUser().getAvatar());


        photos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test","LongClick");
                String planteUrl = (String) parent.getItemAtPosition(position);
                /*Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(planteUrl));
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.chooser_title)));*/


                // Toolbar monActionBar  = (Toolbar)findViewById(R.id.mon_actionBar);
                //monActionBar.setVisibility(View.VISIBLE);
                // setSupportActionBar(monActionBar);

                Intent intent = new Intent(getApplicationContext(), FacebookActivity.class);
                intent.putExtra(SHARE_CODE, planteUrl);
                startActivity(intent);
                return true;
            }
        } );

    }

    private void error() {
        Toast.makeText(getApplicationContext(), "An error occured !", Toast.LENGTH_SHORT).show();
    }


}