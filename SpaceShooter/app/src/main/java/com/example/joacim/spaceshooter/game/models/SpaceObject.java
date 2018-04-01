package com.example.joacim.spaceshooter.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by joacim on 2018-01-31.
 * Abstract class for all space related objects
 */

public abstract class SpaceObject {

    private Bitmap bitmap;
    private int posX, posY, hp;
    private Rect rect;

    public SpaceObject() {

    }

    public void update() {

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
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public Rect getRect() {
        return rect;
    }
    public void setRect(Rect rect) {
        this.rect = rect;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

}
