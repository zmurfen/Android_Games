package com.example.joacim.spaceshooter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.content.Context;


/**
 * Created by joacim on 2018-01-30.
 * Start the Game from this activity
 * Handle pauses, resume and back presses
 */

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int level = getIntent().getIntExtra("Level", 0);

        context = this;
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        Constants.SCREEN_WIDTH = display.widthPixels;
        Constants.SCREEN_HEIGHT = display.heightPixels;


        //Initializing game view object
        gameView = new GameView(this, level);
        setContentView(gameView);

    }

    //Pausing the game view when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //Resume the game view when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    //Ask the user if he/she really wants to quit or if it was a miss click
    @Override
    public void onBackPressed() {
        gameView.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent startMain = new Intent(context, MainActivity.class);
                        startMain.addCategory(Intent.CATEGORY_LAUNCHER);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        gameView.resume();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
