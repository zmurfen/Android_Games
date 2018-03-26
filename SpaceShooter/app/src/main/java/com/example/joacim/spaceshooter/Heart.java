package com.example.joacim.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by joacim on 2018-03-25.
 * Class to show how much life the player has.
 */

public class Heart {


    private Bitmap bitmap;
    private int posX, posY;

    public Heart(Context context, int num) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_full);
        bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/2,bitmap.getHeight()/2,true);
        posX = 100+(num*(bitmap.getWidth()));
        posY = 50;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

}
