package com.example.joacim.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by joacim on 2018-01-31.
 * Shot class used to imitate lasers
 * shot by spaceships.
 */

public class Shot extends SpaceObject{

    private int speedX, speedY, dmg, explosion;

    public Shot(Context context, int startX, int startY, int speedX, int speedY, int dmg, int laser) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), laser));
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(),getBitmap().getWidth()/2,getBitmap().getHeight()/2,true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        setBitmap(rotatedBitmap);
        setRect(new Rect(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(), getPosY()+getBitmap().getHeight()));
        this.speedX = speedX;
        this.speedY = speedY;
        setPosX(startX);
        setPosY(startY);
        this.dmg = dmg;

    }

    public void update(boolean player) {
        if(player) {
            setPosX(getPosX()+speedX);
        } else {
            setPosX(getPosX()-speedX);
        }
        setPosY(getPosY()+speedY);
        getRect().set(getPosX(), getPosY(), getPosX() + getBitmap().getWidth(), getPosY() + getBitmap().getHeight());
    }

    public void setNewBitmap(Bitmap bitmap) {
        setBitmap(bitmap);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(),getBitmap().getWidth()/2,getBitmap().getHeight()/2,true);
        setBitmap(scaledBitmap);
        setPosY(getPosY()-getBitmap().getHeight()/2);
    }


    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    public int getDmg() {
        return dmg;
    }
    public int getExplosion() {
        return explosion;
    }
    public void setExplosion(int explosion) {
        this.explosion = explosion;
    }

}
