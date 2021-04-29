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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class TakePictureAndDispPictureFragment extends Fragment {


    private Uri finalImageUrl;

    Button captureButton;
    ImageView imageView;

    Bitmap imageWeSendToDatabase;


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




            sendToFirebase(bitmap);

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

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(UUID.randomUUID().toString() + ".jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Toast.makeText(getContext(), "Problème d'upload", Toast.LENGTH_SHORT);

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Photo bien uploadé", Toast.LENGTH_SHORT);
                    Uri downloadUri = task.getResult();
                    finalImageUrl = downloadUri;
                    System.out.println(downloadUri);
                } else {
                    Toast.makeText(getContext(), "Problème lors de l'upload", Toast.LENGTH_SHORT);
                }
            }
        });


    }


    public File bitMapToFile(Bitmap bitmap) {
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

        return f;
    }


    public Uri getFinalImageUrl() {
        return finalImageUrl;
    }


}