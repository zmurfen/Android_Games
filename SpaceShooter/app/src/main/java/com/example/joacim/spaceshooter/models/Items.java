package com.example.joacim.spaceshooter.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.joacim.spaceshooter.R;

/**
 * Created by joacim on 2018-01-30.
 * Items used in the game.
 * Currently only used as power-ups for the player
 */

public class Items extends SpaceObject {

    private String itemType;

    public Items(Context context, int posX, int posY) {

        switch ((int)(Math.random() * (4))+1) {
            case 1:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup_attackspeed));
                itemType = "attackSpeed";
                break;
            case 2:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup_dmg));
                itemType = "dmg";
                break;
            case 3:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup_shields));
                itemType = "shields";
                break;
            case 4:
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup_weapons));
                itemType = "weapons";
                break;
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(),getBitmap().getWidth()/2,getBitmap().getHeight()/2,true);
        setBitmap(scaledBitmap);

        setPosX(posX);
        setPosY(posY);

        setRect(new Rect(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(), getPosY()+getBitmap().getHeight()));
    }

    public void update() {
        setPosX(getPosX()-5);
        getRect().set(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(),getPosY()+getBitmap().getHeight());
    }

    public String getItemType() {
        return itemType;
    }
}
