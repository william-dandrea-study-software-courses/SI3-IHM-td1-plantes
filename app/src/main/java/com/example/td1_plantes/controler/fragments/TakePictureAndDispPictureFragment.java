package com.example.td1_plantes.controler.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class TakePictureAndDispPictureFragment extends Fragment {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;



    Button captureButton;
    ImageView imageView;

    Bitmap imageWeSendToDatabase;

    private static final String AUTHORITY="com.td1plante.fileprovider";

    FirebaseStorage storage = FirebaseStorage.getInstance();



    public TakePictureAndDispPictureFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_picture_and_disp_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        captureButton = getView().findViewById(R.id.fragment_take_picture_and_disp_picture_button);
        imageView = getView().findViewById(R.id.fragment_take_picture_and_disp_picture_image_view);


        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 1000);

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imageWeSendToDatabase = bitmap;

            File f = new File(getContext().getCacheDir(), "image");
            try {
                f.createNewFile();


                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

                System.out.println("FILE : " + fos.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }


            // sendToFirebase(bitmap);

            // System.out.println("URI : " + getImageUri(getContext(), bitmap).toString());
        }
    }


    public Bitmap getImageWeSendToDatabase() {
        return imageWeSendToDatabase;
    }

    public boolean verifyIfPictureIsPresent() {
        if (imageWeSendToDatabase == null) {
            return false;
        }
        return true;
    }



    public void sendToFirebase(Bitmap imageBitmap) {

        StorageReference storageReference = storage.getReferenceFromUrl("gs://tp1-plante.appspot.com");
        StorageReference mountainImagesRef = storageReference.child("test.jpg");


    }














}