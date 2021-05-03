package com.example.td1_plantes.controler.activities.userprofil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.WichActivitiesWeAre;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import static com.example.td1_plantes.model.interfaces.IFacebookShare.SHARE_CODE;

public class FacebookActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    ShareButton shareButton;
    String urlPhoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        loginButton = findViewById(R.id.loginButton);
        shareButton = findViewById(R.id.shareButton);

        callbackManager = CallbackManager.Factory.create();

        Intent intent = getIntent();

        if (null != intent) {
            if (intent.hasExtra(SHARE_CODE))
                urlPhoto = intent.getStringExtra(SHARE_CODE);
        }

        // MENU
        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(WichActivitiesWeAre.NOTHING));
        ft2.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data) ;

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setImageUrl(Uri.parse(urlPhoto))
                .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        shareButton.setShareContent(sharePhotoContent);
    }
}