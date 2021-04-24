package com.example.td1_plantes.controler.activities.takepicture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author D'Andrea William
 */
public class TakePictureActivity extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private File photoFile = null;
    private String mCurrentPhotoPath;
    private ImageView mImageThumbnail;
    private Button mTakePictureBtn;
    private byte[] mFileBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        // MENU
        //bottom_app_bar
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.add(R.id.bottom_app_bar, new MyBottomBarFragment(3));
        ft2.commit();

        //Code pour ajouter une photo
        mImageThumbnail = (ImageView) findViewById(R.id.img_thumbnail);
        mTakePictureBtn = (Button) findViewById(R.id.btnCapture);
        mTakePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });

    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            try {
                photoFile = createImageFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }catch (IOException e){
                e.printStackTrace();
            }
            //photoFile = File.createTempFile("photo"+time,".jpg",photoDir);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                retrieveCapturedPicture();
            }
        }
    }

    private void retrieveCapturedPicture() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
        mImageThumbnail.setImageBitmap(bitmap);


        try {
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
            mFileBytes = stream1.toByteArray();
        } catch (OutOfMemoryError e) { //possibility of memoryexception
            e.printStackTrace();
        }

    }

    private File createImageFile() throws IOException {
        File photoDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "1mind_" + timeStamp + ".jpg";
        File photo = new File(photoDir,  imageFileName);
        return photo;
    }

}