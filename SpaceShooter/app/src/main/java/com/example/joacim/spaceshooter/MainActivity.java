package com.example.joacim.spaceshooter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ImageButton buttonPlay, scoreButton, dbButton;
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        scoreButton = findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(this);

        dbButton = findViewById(R.id.dbButton);
        dbButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPlay:
                //Starts the level selection activity
                startActivity(new Intent(this, LevelSelectorActivity.class));
                break;
            case R.id.scoreButton:
                //Starts the highscore activity diesplaying the highscores
                startActivity(new Intent(this, HighScore.class));
                break;
            case R.id.dbButton:
                startActivity(new Intent(this, SignInOrRegister.class));
                break;
        }

    }

    /**
     * Maybe use SQLite for more then just scores
     * Users, progress and more
     * Testing SQLite db commands
    */
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
