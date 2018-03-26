package com.example.joacim.spaceshooter.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.joacim.spaceshooter.Constants;
import com.example.joacim.spaceshooter.R;

/**
 * Created by joacim on 2018-02-06.
 * Class to generate a space background for the game
 */

public class Background extends SpaceObject {

    public Background(Context context, int posx) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(), Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT,true);
        setBitmap(scaledBitmap);
        setPosX(posx);
        setPosY(0);
    }

    /**
     * Make a scrolling background.
     * Creates the illusion of the ship actually
     * flying through space.
     */
    public void update() {
        if(getPosX() < Constants.SCREEN_WIDTH*-1) {
            setPosX(Constants.SCREEN_WIDTH);
        } else {
            setPosX(getPosX() - 1);
        }
    }
}
