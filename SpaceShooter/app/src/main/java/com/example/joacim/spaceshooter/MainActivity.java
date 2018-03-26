package com.example.joacim.spaceshooter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonPlay, scoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        scoreButton = findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPlay:
                /*TODO
                Change this from starting the game to go to level selector
                The level selector should start the game with certain parameters
                set so that the level becomes unique
                Currently there is only one level.*/
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.scoreButton:
                //TODO
                //Add score button
                startActivity(new Intent(this, HighScore.class));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /*Maybe use SQLite for more then just scores
        Users, progress and more
        Testing SQLite db commands*/
    private void testDB() {
        //TODO
        //Implement SQLite db when creating player account
        //and when progress is possible to be made.
        SQLiteDatabase sqliteDatabase = openOrCreateDatabase("spaceShooter", MODE_PRIVATE, null);
        sqliteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Score(name VARCHAR,score int);");
        sqliteDatabase.execSQL("INSERT INTO Score VALUES('testPlayer',12345);");
        Cursor resultSet = sqliteDatabase.rawQuery("Select * from Score",null);
        resultSet.moveToFirst();
        String player = resultSet.getString(0);
        int score = resultSet.getInt(1);
        System.out.print("Player: " + player);
        System.out.println("Score: " + score);

    }
}
