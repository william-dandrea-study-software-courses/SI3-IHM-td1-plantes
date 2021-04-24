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
import com.example.td1_plantes.model.LoadImageInBackground;
import com.example.td1_plantes.model.database.FirebaseFactories.UserFactory;

/**
 * @author D'Andrea William
 */
public class UserProfilActivity extends AppCompatActivity {

    private String currentUsername = "Yann";

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

        GridView photos = findViewById(R.id.photos_mosaic);


        contributionCount.setText("0");
        UserFactory userFactory = new UserFactory();
        userFactory.getFromUsername(currentUsername, u -> {
            usernameHolder.setText(u.getUsername());
            photoCountHolder.setText(Integer.toString(u.getPhotosCount()));

            new LoadImageInBackground(avatarHolder, () -> Log.d("LOAD", "ERror !")).execute(u.getAvatar());

            photos.setAdapter(new PhotoAdapter(getApplicationContext(), u.getPhotos()));
        }, err -> {
            error();
        });
    }

    private void error() {
        Toast.makeText(getApplicationContext(), "An error occured !", Toast.LENGTH_SHORT).show();
    }


}