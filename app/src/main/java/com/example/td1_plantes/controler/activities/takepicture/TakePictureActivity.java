package com.example.td1_plantes.controler.activities.takepicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.GenerateCurrentLocalisationFragment;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.controler.fragments.TakePictureAndDispPictureFragment;
import com.example.td1_plantes.controler.fragments.TitleYellowDescriberDivFragment;
import com.example.td1_plantes.controler.fragments.homefragments.PlantListHomePageFragment;
import com.example.td1_plantes.model.appobjects.smallelements.MyPosition;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author D'Andrea William
 */
public class TakePictureActivity extends AppCompatActivity {



    private Bitmap imageWeWillSendToDataBase;
    private boolean isCheckedPublic;
    private String description;
    private String title;
    private MyPosition emplacement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        isCheckedPublic = true;

        // take_pricture_activity_ajouter_une_photo_title


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.take_pricture_activity_ajouter_une_photo_title, new TitleYellowDescriberDivFragment("Prendre une photo"));
        transaction.add(R.id.take_pricture_activity_ajouter_des_informations, new TitleYellowDescriberDivFragment("Informations"));



        TakePictureAndDispPictureFragment takePictureAndDispPictureFragment = new TakePictureAndDispPictureFragment();
        transaction.add(R.id.take_pricture_activity_ajouter_une_photo_fragment, takePictureAndDispPictureFragment);
        imageWeWillSendToDataBase = takePictureAndDispPictureFragment.getImageWeSendToDatabase();


        transaction.add(R.id.take_pricture_activity_localisation, new GenerateCurrentLocalisationFragment());

        //bottom_app_bar
        transaction.add(R.id.bottom_app_bar, new MyBottomBarFragment(3));

        transaction.commit();




        Button validateButton = (Button) findViewById(R.id.take_pricture_validate_button);



        // PUBLIC - PRIVATE BUTTON
        Chip publicChip = (Chip) findViewById(R.id.take_pricture_chipgroup_public_private_child_1);
        Chip privateChip = (Chip) findViewById(R.id.take_pricture_chipgroup_public_private_child_2);

        publicChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedPublic = true;
            }
        });

        privateChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedPublic = false;
            }
        });



        // DESCRIPTION
        TextInputLayout descriptionInput = (TextInputLayout) findViewById(R.id.take_pricture_description_input);

        descriptionInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                description = text.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        // TITRE
        TextInputLayout titleInput = (TextInputLayout) findViewById(R.id.take_pricture_title_input);

        titleInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                title = text.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });












        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });









    }





}