package com.example.td1_plantes.controler.activities.personnalfolder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;

/**
 * @author D'Andrea William
 */
public class PersonnalFolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnal_folder);


        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft2.commit();

        /*

        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", (user) -> {
            TextView email = findViewById(R.id.email);
            TextView username = findViewById(R.id.username);

            email.setText(user.getEmail());
            username.setText(user.getUsername());
        }, (error) -> {

        });

         */



    }
}