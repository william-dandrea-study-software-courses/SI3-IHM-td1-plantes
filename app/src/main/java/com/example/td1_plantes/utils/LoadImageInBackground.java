package com.example.td1_plantes.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadImageInBackground extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private IVoidEventHandler onfail;
    private String urldisplay;
    public LoadImageInBackground(ImageView bmImage, IVoidEventHandler onfail) {
        this.bmImage = bmImage;
        this.onfail = onfail;
    }

    protected Bitmap doInBackground(String... urls) {
        urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            onfail.onTrigger();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        Log.d("Photo loaded", urldisplay);
    }
}
