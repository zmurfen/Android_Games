package com.example.joacim.spaceshooter.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.joacim.spaceshooter.Constants;
import com.example.joacim.spaceshooter.Menu;
import com.example.joacim.spaceshooter.R;
import com.example.joacim.spaceshooter.game.models.Background;
import com.example.joacim.spaceshooter.game.models.Boss;
import com.example.joacim.spaceshooter.game.models.Enemy;
import com.example.joacim.spaceshooter.game.models.Items;
import com.example.joacim.spaceshooter.game.models.Player;

import java.util.ArrayList;


/**
 * Created by joacim on 2018-01-30.
 * The game engine.
 * Controls what happens in the game and
 * draws the graphics
 */

class GameView extends SurfaceView implements Runnable {

    private final Context context;
    private boolean playing;

    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private Player player;
    private Enemy[] enemies;
    private Boss boss;

    private ArrayList<Items> items;
    private ArrayList<Background> backgrounds;
    private long asResetTime, shieldTime, shootTime, enemyShootTime;
    private int enemiesOnScreen, enemiesToBoss, firingSpeedResetTime, score;

    private int[] highScore;
    private SharedPreferences sharedPreferences;


    public GameView(Context context, int level) {
        super(context);
        this.context = context;

        //Initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        /**
         * TODO
         * Add the level creation so
         * that different levels can
         * be used
         */


        //Initialize player
        initPlayer();

        //Initialize enemies
        initEnemies();

        //Shooting timers
        setupShootTimers();

        //Scrolling background objects
        initBackground();

        //Initialize the score tracking
        initScore();

        //List holding upgrade items
        items = new ArrayList<>();
    }

    @Override
    public void run() {
        //The game loop
        while(playing) {
            update();
            draw();
        }
    }

    /**
     * When moving out of the game view
     */
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * When re entering the game view
     */
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Game engine.
     * Updates all of the objects and figures out what to do
     * when something happens.
     */
    private void update() {
        generateShots();

        //If not time for Boss
        if(enemiesToBoss > 0) {
            for (int i = 0; i < enemiesOnScreen; i++) {
                checkShipCollisions(i);
                enemies[i].update();
                for(int j = 0; j < enemies[i].getShots().size(); j++) {
                    checkEnemyShotCollisionWithPlayer(i, j);
                }
            }
        } else {
            if(boss == null) {
                enemiesOnScreen = 0;
                boss = new Boss(context, Constants.SCREEN_WIDTH-100, Constants.SCREEN_HEIGHT/2);
            } else {
                bossShotUpdate();
            }
            boss.update();
        }

        player.update();
        playerShotUpdate();

        itemsUpdate();

        for(int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).update();
        }

        if(boss != null) {
            if (boss.getHp() <= 0) {
                //Game Finished Winner!
                //For now before i manage to create a map selector just go back to enemies spawning
                boss = null;
                enemiesToBoss = 50;
                enemiesOnScreen = 5;
                score += 500;
            }
        }
        if(player.getHp()<=0) {
            //Game Over
            assignAndEditFinalScore();
            playing = false;
        }

    }

    /**
     * Draw the graphics of the game.
     * Draw bitmaps/sprites of player, enemies, items and other objects.
     */
    private void draw() {
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            //Draw background image
            for(int i = 0; i < backgrounds.size(); i++) {
                canvas.drawBitmap(
                        backgrounds.get(i).getBitmap(),
                        backgrounds.get(i).getPosX(),
                        backgrounds.get(i).getPosY(),
                        paint);
            }

            //drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getPosX(),
                    player.getPosY(),
                    paint);

            //drawing the enemies
            for(int i = 0; i < enemiesOnScreen; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getPosX(),
                        enemies[i].getPosY(),
                        paint);
            }

            //Draw player shots
            for(int i = 0; i < player.getShots().size(); i++) {
                canvas.drawBitmap(
                        player.getShots().get(i).getBitmap(),
                        player.getShots().get(i).getPosX(),
                        player.getShots().get(i).getPosY(),
                        paint);
            }

            //Draw enemy shots
            for(int j = 0; j < enemiesOnScreen; j++) {
                for (int i = 0; i < enemies[j].getShots().size(); i++) {
                    canvas.drawBitmap(
                            enemies[j].getShots().get(i).getBitmap(),
                            enemies[j].getShots().get(i).getPosX(),
                            enemies[j].getShots().get(i).getPosY(),
                            paint);
                }
            }

            //Draw boss
            if(boss != null) {
                canvas.drawBitmap(
                        boss.getBitmap(),
                        boss.getPosX(),
                        boss.getPosY(),
                        paint);

                //Draw boss shots
                for(int i = 0; i < boss.getShots().size(); i++) {
                    canvas.drawBitmap(
                            boss.getShots().get(i).getBitmap(),
                            boss.getShots().get(i).getPosX(),
                            boss.getShots().get(i).getPosY(),
                            paint);
                }
            }

            //Draw items
            for(int i = 0; i < items.size(); i++) {
                canvas.drawBitmap(
                        items.get(i).getBitmap(),
                        items.get(i).getPosX(),
                        items.get(i).getPosY(),
                        paint);

            }

            //Draw Life
            for(int i = 0; i < player.getLife().size(); i++) {
                canvas.drawBitmap(
                        player.getLife().get(i).getBitmap(),
                        player.getLife().get(i).getPosX(),
                        player.getLife().get(i).getPosY(),
                        paint);
            }

            //Draw score on screen
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("Score: "+score,Constants.SCREEN_WIDTH-300,50,paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(playing) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    player.setNewX((int) motionEvent.getX());
                    player.setNewY((int) motionEvent.getY());
                    return true;
            }
        } else {
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                Intent startMain = new Intent(context, Menu.class);
                startMain.addCategory(Intent.CATEGORY_LAUNCHER);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(startMain);
            }
        }
        return false;
    }

    /**
     * Generate shots for the player, enemies and boss
     * Timers are used to dictate the attack speed of them
     */
    private void generateShots() {
        if(System.currentTimeMillis() >= (shootTime + player.getFiringSpeed())) {
            player.shoot(context);
            shootTime = System.currentTimeMillis();
        }
        if(boss == null) {
            if (System.currentTimeMillis() >= (enemyShootTime + 1000)) {
                for (int i = 0; i < enemiesOnScreen; i++) {
                    enemies[i].shoot(context);
                }
                enemyShootTime = System.currentTimeMillis();
            }
        } else {
            if(System.currentTimeMillis() >= (enemyShootTime + 500)){
                boss.shoot(context);
                enemyShootTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Updates shots position, collision and graphics for the player and the boss
     */
    private void bossShotUpdate() {
        for(int i = 0; i < boss.getShots().size(); i++) {
            if(player.getRect().intersect(boss.getShots().get(i).getRect()) && !player.isInvulnerable() && (boss.getShots().get(i).getExplosion()==0)) {
                player.setHp(player.getHp()-boss.getShots().get(i).getDmg());
                player.updateHearts(context);
                changeBossShotBitmap(i);
            }
            boss.getShots().get(i).update(false);
            switch(boss.getShots().get(i).getExplosion()) {
                case 0:
                    break;
                case 1:case 2:
                    boss.getShots().get(i).setExplosion(boss.getShots().get(i).getExplosion()+1);
                    break;
                default:
                    boss.getShots().remove(i);
                    break;
            }
        }
    }

    private void playerShotUpdate() {
        for(int i = 0; i < player.getShots().size(); i++) {
            if (checkPlayerShotCollisionWithEnemies(i) || checkPlayerShotCollisionWithBoss(i)) {
                changePlayerShotBitmap(i);
            }
            switch(player.getShots().get(i).getExplosion()) {
                case 0:
                    break;
                case 1:case 2:
                    player.getShots().get(i).setExplosion(player.getShots().get(i).getExplosion()+1);
                    break;
                default:
                    player.getShots().remove(i);
                    break;
            }
            if(boss != null) {
                if(boss.getRect().intersect(player.getShots().get(i).getRect())) {
                    boss.setHp(boss.getHp()-player.getDmg());
                }
            }
        }
    }

    private void checkShipCollisions(int enemy) {
        if (player.getRect().intersect(enemies[enemy].getRect()) && !player.isInvulnerable()) {
            player.setHp(player.getHp() - 10);
            player.updateHearts(context);
            enemiesToBoss--;
            enemies[enemy] = new Enemy(context);
        }
    }

    private void checkEnemyShotCollisionWithPlayer(int enemyNum, int shotNum) {
        if(checkShotCollisionEnemyShots(enemies[enemyNum], shotNum)) {
            player.setHp(player.getHp() - enemies[enemyNum].getShots().get(shotNum).getDmg());
            player.updateHearts(context);
            enemies[enemyNum].getShots().get(shotNum).setExplosion(1);
        }
        enemies[enemyNum].getShots().get(shotNum).update(false);
        switch (enemies[enemyNum].getShots().get(shotNum).getExplosion()) {
            case 0:
                break;
            case 1:case 2:
                enemies[enemyNum].getShots().get(shotNum).setExplosion(enemies[enemyNum].getShots().get(shotNum).getExplosion() + 1);
                break;
            default:
                enemies[enemyNum].getShots().remove(shotNum);
                break;

        }
    }

    private boolean checkPlayerShotCollisionWithEnemies(int shotNum) {
        for (int i = 0; i < enemiesOnScreen; i++) {
            if (player.getShots().get(shotNum).getRect().intersect(enemies[i].getRect())) {
                enemies[i].setHp(enemies[i].getHp() - player.getShots().get(shotNum).getDmg());
                if (enemies[i].getHp() <= 0 && enemiesToBoss > 0) {
                    if((int)(Math.random() * (4)) == 1) {
                        items.add(new Items(context, enemies[i].getPosX(), enemies[i].getPosY()));
                    }
                    enemies[i] = new Enemy(context);
                    enemiesToBoss--;
                    score += 10;
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkPlayerShotCollisionWithBoss(int j) {
        if(boss == null) { return false; }
        return player.getShots().get(j).getRect().intersect(boss.getRect());
    }

    private boolean checkShotCollisionEnemyShots(Enemy enemy, int j) {
        if(player.isInvulnerable() || (enemy.getShots().get(j).getExplosion() > 0)) { return false; }
        return player.getRect().intersect(enemy.getShots().get(j).getRect());
    }

    /**
     * Sets explosion graphics to the shots
     * for both player and boss.
     */
    private void changeBossShotBitmap(int i) {
        boss.getShots().get(i).setNewBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_red08));
        boss.getShots().get(i).setSpeedX(0);
        boss.getShots().get(i).setSpeedY(0);
        boss.getShots().get(i).setExplosion(boss.getShots().get(i).getExplosion() + 1);
    }

    private void changePlayerShotBitmap(int i) {
        player.getShots().get(i).setNewBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_red08));
        player.getShots().get(i).setSpeedX(0);
        player.getShots().get(i).setSpeedY(0);
        player.getShots().get(i).setExplosion(player.getShots().get(i).getExplosion() + 1);
    }

    /**
     * Updates item position.
     * Sets necessary parameter values on the player
     * depending on the item that is collected.
     */
    private void itemsUpdate() {
        for(int i = 0; i < items.size(); i++) {
            items.get(i).update();
            if(checkItemCollision(i)) {
                if(items.get(i).getItemType().equals("attackSpeed")) {
                    asResetTime = System.currentTimeMillis();
                }
                if(items.get(i).getItemType().equals("shields")) {
                    shieldTime = System.currentTimeMillis();
                }
                player.upgrade(items.get(i).getItemType());
                items.remove(i);
            }
        }
        if(System.currentTimeMillis() >= (asResetTime + firingSpeedResetTime)) {
            player.setFiringSpeed(300);
        }
        if(System.currentTimeMillis() >= (shieldTime + player.getShieldTimer())) {
            player.setInvulnerable(false);
        }
    }

    private boolean checkItemCollision(int i) {
        return player.getRect().intersect(items.get(i).getRect());
    }

    /**
     * Updates the save file with the final score.
     */
    private void assignAndEditFinalScore() {
        SharedPreferences.Editor e = sharedPreferences.edit();
        for(int i = 0; i < 4; i++) {
            int j = i+1;
            if(highScore[i] < score) {
                highScore[i] = score;
                e.putInt("score"+j,highScore[i]);
                break;
            }
        }
        e.apply();
    }

    /**
     * Initializer functions to setup the game properly
     */
    private void initScore() {
        score = 0;
        sharedPreferences = context.getSharedPreferences("Score_saves", Context.MODE_PRIVATE);
        highScore = new int[4];
        for(int i = 0; i < highScore.length; i++) {
            int j = i+1;
            highScore[i] = sharedPreferences.getInt("score"+j, 0);
        }
    }

    private void initPlayer() {
        player = new Player(context);
        player.upgrade("shields");
        shieldTime = System.currentTimeMillis();
    }

    private void initEnemies() {
        enemiesOnScreen = 5;
        enemiesToBoss = 50;
        enemies = new Enemy[enemiesOnScreen];
        for(int i = 0; i < enemiesOnScreen; i++) {
            enemies[i] = new Enemy(context);
        }
    }

    private void setupShootTimers() {
        shootTime = System.currentTimeMillis();
        enemyShootTime = System.currentTimeMillis();
        asResetTime = System.currentTimeMillis();
        firingSpeedResetTime = 10000;
    }

    private void initBackground() {
        backgrounds = new ArrayList<>();
        backgrounds.add(new Background(context, 0));
        backgrounds.add(new Background(context, Constants.SCREEN_WIDTH));
    }

}
