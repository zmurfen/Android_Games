package com.example.joacim.spaceshooter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by joacim on 2018-03-30.
 * Handles the level images displayed
 * in the GridView
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageIds[position]);
        return imageView;
    }

    private Integer[] imageIds = {
            R.drawable.numeral1, R.drawable.numeral2,
            R.drawable.numeral3, R.drawable.numeral4,
            R.drawable.numeral5, R.drawable.numeral6,
            R.drawable.numeral7, R.drawable.numeral8,
            R.drawable.numeral9
    };
}
