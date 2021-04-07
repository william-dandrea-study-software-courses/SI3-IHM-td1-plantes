package com.example.td1_plantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.td1_plantes.fragments.MyBottomBarFragment;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.td1_plantes.utils.database.FirebaseFactories.UserFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(4));
        ft2.commit();
                return false;
            }
        });

        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", (user) -> {
            TextView email = findViewById(R.id.email);
            TextView username = findViewById(R.id.username);

            email.setText(user.getEmail());
            username.setText(user.getUsername());
        }, (error) -> {

        });

        BottomNavigationView bottomNavigationView2 = findViewById(R.id.bottomNavigationView);
        bottomNavigationView2.setBackground(null);
        bottomNavigationView2.getMenu().getItem(2).setEnabled(false);
        //R.id.repository
        bottomNavigationView2.getMenu().getItem(4).setChecked(true);

    }
}