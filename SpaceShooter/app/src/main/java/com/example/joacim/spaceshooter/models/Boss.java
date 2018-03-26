package com.example.joacim.spaceshooter.models;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.joacim.spaceshooter.Constants;
import com.example.joacim.spaceshooter.R;

import java.util.ArrayList;

/**
 * Created by joacim on 2018-02-06.
 * Class used to generate the Boss of the level.
 * Currently only one Boss is available.
 */

public class Boss extends SpaceObject {

    private boolean flyingUp = true;
    private ArrayList<Shot> shots;
    private int hp;

    public Boss(Context context, int posx, int posy) {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss1));
        setPosX(posx);
        setPosY(posy);
        setHp(10000);
        setRect(new Rect(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(), getPosY()+getBitmap().getHeight()));
        shots = new ArrayList<>();

    }

    //Update Boss movements and shots
    @Override
    public void update() {
        if(getPosX() > Constants.SCREEN_WIDTH-getBitmap().getWidth()) {
            setPosX(getPosX() - 5);
        }

        //Make the boss go up and down
        if(flyingUp && getPosY() > 0) {
            setPosY(getPosY() - 5);
        } else {
            flyingUp = false;
        }
        if(!flyingUp && getPosY()+getBitmap().getHeight() < Constants.SCREEN_HEIGHT) {
            setPosY(getPosY() + 5);
        } else {
            flyingUp = true;
        }
        getRect().set(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(),getPosY()+getBitmap().getHeight());
        for(int i = 0; i < getShots().size(); i++) {
            if(getShots().get(i).getPosX() < -100) {
                getShots().remove(i);
            }
        }
    }

    public void shoot(Context context) {
        for(int i = 0; i < 10; i++) {
            int speedY = (int)(Math.random() * (4) - 2);
            //Context context, int startX, int startY, int speedX, int speedY, int dmg
            shots.add(new Shot(context, getPosX(), getPosY()+getBitmap().getHeight()/2, 10, speedY, 5,R.drawable.laser_blue03));
        }
    }

    public ArrayList<Shot> getShots() {
        return this.shots;
    }
}
