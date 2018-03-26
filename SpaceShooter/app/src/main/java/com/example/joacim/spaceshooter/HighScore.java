package com.example.joacim.spaceshooter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by joacim on 2018-03-23.
 * Keep track of the high score persistently.
 */

public class HighScore extends AppCompatActivity {

    TextView textView,textView2,textView3,textView4;
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);

        sharedPreferences = getSharedPreferences("Score_saves", Context.MODE_PRIVATE);

        textView.setText("1. "+sharedPreferences.getInt("score1",0));
        textView2.setText("2. "+sharedPreferences.getInt("score2",0));
        textView3.setText("3. "+sharedPreferences.getInt("score3",0));
        textView4.setText("4. "+sharedPreferences.getInt("score4",0));

    }
}
