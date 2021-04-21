package com.example.td1_plantes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.td1_plantes.R;
import com.example.td1_plantes.utils.LoadImageInBackground;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> images;

    public PhotoAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.image_adapter, parent, false);
        ImageView img = convertView.findViewById(R.id.photo_adapter_imv);
        new LoadImageInBackground(img, () -> Log.e("Load photo", "Error during loading photo")).execute(images.get(position));
        return convertView;
    }
}
