package com.example.td1_plantes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.fragments.MyBottomBarFragment;
import com.example.td1_plantes.fragments.homefragments.NewsDivHomeFragment;
import com.example.td1_plantes.models.NewsElement;
import com.example.td1_plantes.utils.database.FirebaseFactories.UserFactory;

import java.util.GregorianCalendar;

/**
 * @author D'Andrea William
 */
public class MainActivity extends AppCompatActivity {


    private NewsElement[] news =  {
            new NewsElement(
                    "La biologie, une science patriarcale et « viriliste » ?",
                    "Pour le philosophe Jean-François Braunstein, les discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel.",
                    "https://unsplash.com/photos/NoXUQ54pDac/download?force=true&w=1920",
                    new GregorianCalendar()
                    ),
            new NewsElement(
                    "La biologie, une science patriarcale ?",
                    "Pou es discours qui prétendent déconstruire le genre engendrent des conséquences non négligeables sur le monde réel.",
                    "https://unsplash.com/photos/NoXUQ54pDac/download?force=true&w=1920",
                    new GregorianCalendar()
            )
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // GENERATE THE NEWS DIV FRAGMENT
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_layout_main, new NewsDivHomeFragment());
        ft.commit();






        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(2));
        ft2.commit();



        UserFactory userFactory = new UserFactory();
        userFactory.loadFromFirebase("DSVBZgTAmIdw1k0jqTaW", user -> {
            TextView welcome = findViewById(R.id.home_welcome);
            welcome.setText("Bienvenue, " + user.getUsername() + "!");
        }, error -> {

        });
    }



}