package com.example.joacim.spaceshooter.game.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.joacim.spaceshooter.Constants;
import com.example.joacim.spaceshooter.R;

import java.util.ArrayList;

/**
 * Created by joacim on 2018-01-30.
 * Enemy class to generate enemy space ships
 */

public class Enemy extends SpaceObject{

    private int speed, hp;
    private ArrayList<Shot> shots;

    public Enemy(Context context) {
        //Get the sprite and make it smaller and turn it 270 degrees
        switch ((int)(Math.random() * (4))) {
            case 0:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_black1));
                break;
            case 1:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_blue1));
                break;
            case 2:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_green1));
                break;
            case 3:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_red1));
                break;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(),getBitmap().getWidth()/2,getBitmap().getHeight()/2,true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        setBitmap(rotatedBitmap);

        shots = new ArrayList<>();

        //Set start pos X outside of screen
        setPosX(Constants.SCREEN_WIDTH + getBitmap().getWidth());

        //Set random Y pos to start at
        posYUpdate();

        //Set random speed between 5 and 10 pixels
        speed = ((int)(Math.random() * (10 - 5))+5);
        setRect(new Rect(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(), getPosY()+getBitmap().getHeight()));

        setHp(30);
    }

    /**
     * If the enemy crossed the left side of the screen
     * respawn it on the other side as a new enemy and a
     * new Y position.
     * Restore the health of the enemy to make it look like a new one.
     * Also remove shots that have traveled 100 pixels past the left
     * side of the screen.
     */
    @Override
    public void update() {
        if(getPosX() < 0) {
            setPosX(Constants.SCREEN_WIDTH);
            posYUpdate();
            setHp(30);
        } else {
            setPosX(getPosX() - speed);
        }

        getRect().set(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(),getPosY()+getBitmap().getHeight());
        for(int i = 0; i < getShots().size(); i++) {
            if(getShots().get(i).getPosX() < -100) {
                getShots().remove(i);
            }
        }
    }

    //Generate a random Y position within the screen size.
    private void posYUpdate() {
        setPosY((int)(Math.random() * (Constants.SCREEN_HEIGHT)));
        while(getPosY() > (Constants.SCREEN_HEIGHT - getBitmap().getHeight())) {
            setPosY((int)(Math.random() * (Constants.SCREEN_HEIGHT)));
        }
    }

    public void shoot(Context context) {
        shots.add(new Shot(context, getPosX(), getPosY(), getSpeed()+5, 0, 5, R.drawable.laser_red03));
    }

    public ArrayList<Shot> getShots() {
        return this.shots;
    }
    public int getSpeed() {
        return speed;
    }

}
