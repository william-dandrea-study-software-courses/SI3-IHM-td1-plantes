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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.controler.fragments.TakePictureAndDispPictureFragment;
import com.example.td1_plantes.controler.fragments.TitleYellowDescriberDivFragment;
import com.example.td1_plantes.controler.fragments.homefragments.PlantListHomePageFragment;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);


        // take_pricture_activity_ajouter_une_photo_title


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.take_pricture_activity_ajouter_une_photo_title, new TitleYellowDescriberDivFragment("Prendre une photo"));
        transaction.add(R.id.take_pricture_activity_ajouter_des_informations, new TitleYellowDescriberDivFragment("Informations"));



        TakePictureAndDispPictureFragment takePictureAndDispPictureFragment = new TakePictureAndDispPictureFragment();
        transaction.add(R.id.take_pricture_activity_ajouter_une_photo_fragment, takePictureAndDispPictureFragment);
        imageWeWillSendToDataBase = takePictureAndDispPictureFragment.getImageWeSendToDatabase();









        //bottom_app_bar
        transaction.add(R.id.bottom_app_bar, new MyBottomBarFragment(3));



        transaction.commit();

    }





}