package com.example.joacim.spaceshooter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra("User");

        ImageButton buttonPlay, scoreButton;
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        scoreButton = findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonPlay:
                //Starts the level selection activity
                Intent intent = new Intent(this, LevelSelectorActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                break;
            case R.id.scoreButton:
                //Starts the highscore activity diesplaying the highscores
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
                        user = null;
                        Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                        startMain.addCategory(Intent.CATEGORY_LAUNCHER);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
}
