package com.example.joacim.spaceshooter.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.joacim.spaceshooter.Constants;
import com.example.joacim.spaceshooter.R;

import java.util.ArrayList;

/**
 * Created by joacim on 2018-01-30.
 * The Player class to create and use the player spaceship
 */

public class Player extends SpaceObject {

    private int xSpeed, ySpeed, newX, newY, upgradeLevel, dmg, firingSpeed, shieldTimer, maxHealth, hp;
    private boolean invulnerable = false;
    private ArrayList<Shot> shots;
    private ArrayList<Heart> life;

    public Player(Context context) {

        //Get the sprite and make it smaller and turn it 90 degrees
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_ship2_red));
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(getBitmap(),getBitmap().getWidth()/2,getBitmap().getHeight()/2,true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        setBitmap(rotatedBitmap);

        setPosX(Constants.SCREEN_WIDTH/2);
        setPosY(Constants.SCREEN_HEIGHT/2);
        setRect(new Rect(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(), getPosY()+getBitmap().getHeight()));
        xSpeed = 10;
        ySpeed = 10;
        setHp(100);
        setMaxHealth(100);
        upgradeLevel = 1;
        dmg = 10;

        firingSpeed = 300;
        shots = new ArrayList<>();
        fillHearts(context);
    }

    @Override
    public void update() {
        //Move the Player towards the touch position
        if(newX < getPosX()) {
            if(getPosX() - newX < 10) {
                setPosX(newX);
            } else {
                setPosX(getPosX()-xSpeed);
            }
        }
        if(newX > getPosX()) {
            if(newX - getPosX() < 10) {
                setPosX(newX);
            } else {
                setPosX(getPosX()+xSpeed);
            }
        }
        if(newY < getPosY()) {
            if(getPosY() - newY < 10) {
                setPosY(newY);
            } else {
                setPosY(getPosY()-ySpeed);
            }
        }
        if(newY > getPosY()) {
            if(newY - getPosY() < 10) {
                setPosY(newY);
            } else {
                setPosY(getPosY()+ySpeed);
            }
        }

        getRect().set(getPosX(),getPosY(),getPosX()+getBitmap().getWidth(),getPosY()+getBitmap().getHeight());

        for(int j = 0; j < getShots().size(); j++) {
            if(getShots().get(j).getPosX() > Constants.SCREEN_WIDTH || getShots().get(j).getPosY() > Constants.SCREEN_HEIGHT
                    || getShots().get(j).getPosY() < 0) {
                getShots().remove(j);
                continue;
            }
            getShots().get(j).update(true);
        }
    }

    public void upgrade(String upgradeType) {
        switch(upgradeType) {
            case "attackSpeed":
                setFiringSpeed(150);
                break;
            case "dmg":
                if(getDmg() < 50) {
                    setDmg(getDmg() + 10);
                }
                break;
            case "shields":
                setShieldTimer(10000);
                setInvulnerable(true);
                break;
            case "weapons":
                if(getUpgradeLevel()<3) {
                    setUpgradeLevel(getUpgradeLevel() + 1);
                }
                break;
        }
    }

    /**
     * Generates shots that is to be fired from the Player ship.
     * Number of shots depends on upgrade level.
     */
    public void shoot(Context context) {
        switch (getUpgradeLevel()) {
            case 1:
                shots.add(new Shot(context, getPosX(), getPosY() + getBitmap().getHeight()/4, 10, 0, getDmg(), R.drawable.laser_red03));
                break;
            case 2:
                for(int i = -1; i <= 1; i+=2) {
                    shots.add(new Shot(context, getPosX(), getPosY() + getBitmap().getHeight()/4 + i, 10, i*2, getDmg(), R.drawable.laser_red03));
                }
                break;
            case 3:
                for(int i = -1; i <= 1; i++) {
                    shots.add(new Shot(context, getPosX(), getPosY() + getBitmap().getHeight()/4 + i, 10, i*2, getDmg(), R.drawable.laser_red03));
                }
                break;
        }
    }

    private void fillHearts(Context context) {
        life = new ArrayList<>();
        for(int i = 0; i < getMaxHealth()/10; i++) {
            life.add(new Heart(context, i));
        }
    }

    public void updateHearts(Context context) {
        int tempHealth = getHp();
        int rest = getHp() % 10;
        Bitmap bp;
        for(int i = 0; i < getLife().size(); i++) {
            if(tempHealth > 5) {
                bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_full);
                bp = Bitmap.createScaledBitmap(bp,bp.getWidth()/2,bp.getHeight()/2,true);
                life.get(i).setBitmap(bp);
            } else {
                if(rest == 5) {
                    bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_half);
                    bp = Bitmap.createScaledBitmap(bp,bp.getWidth()/2,bp.getHeight()/2,true);
                    life.get(i).setBitmap(bp);
                    rest = 0;
                } else {
                    bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_empty);
                    bp = Bitmap.createScaledBitmap(bp,bp.getWidth()/2,bp.getHeight()/2,true);
                    life.get(i).setBitmap(bp);
                }
            }
            tempHealth -= 10;
        }
    }

    public ArrayList<Shot> getShots() {
        return this.shots;
    }
    public void setNewX(int x) {
        this.newX = x;
    }
    public void setNewY(int y) {
        this.newY = y;
    }
    public int getUpgradeLevel() {
        return upgradeLevel;
    }
    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public int getFiringSpeed() {
        return firingSpeed;
    }
    public void setFiringSpeed(int firingSpeed) {
        this.firingSpeed = firingSpeed;
    }
    public int getShieldTimer() {
        return shieldTimer;
    }
    public void setShieldTimer(int shieldTimer) {
        this.shieldTimer = shieldTimer;
    }
    public boolean isInvulnerable() {
        return invulnerable;
    }
    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    public ArrayList<Heart> getLife() {
        return life;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
