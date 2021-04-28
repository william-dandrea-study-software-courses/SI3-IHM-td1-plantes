package com.example.td1_plantes.controler.activities.userprofil;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.LoadImageInBackground;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;

import java.util.stream.Collectors;

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
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(0));
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


        contributionCount.setText("0");

        usernameHolder.setText(GestionDatabase.getCurrentUser().getSurname());
        photoCountHolder.setText(Integer.toString(GestionDatabase.getCurrentUser().getPhotoCount()));

        if(GestionDatabase.getCurrentUser().getStatusUser() == StatusUser.EXPERT) {
            user_status_text.setText("Expert");
            user_status_icon.setImageResource(R.drawable.paon);
        } else {
            user_status_text.setText("Passioné");
            user_status_icon.setImageResource(R.drawable.perroquet);
        }

        GestionDatabase.getAllPlantsCreateByCurrentUser(plants -> {
            photos.setAdapter(new PhotoAdapter(getApplicationContext(), plants.stream().map(Plant::getImageURL).collect(Collectors.toList())));
        });

        new LoadImageInBackground(avatarHolder, () -> Log.d("LOAD", "ERror !")).execute(GestionDatabase.getCurrentUser().getAvatar());

    }

    private void error() {
        Toast.makeText(getApplicationContext(), "An error occured !", Toast.LENGTH_SHORT).show();
    }


}