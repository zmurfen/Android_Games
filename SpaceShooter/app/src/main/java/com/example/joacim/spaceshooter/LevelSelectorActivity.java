package com.example.joacim.spaceshooter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.AdapterView;


/**
 * Created by joacim on 2018-03-30.
 * Create a basic grid view to display the levels
 * Let the user choose what level to play and then
 * launch the specified level.
 */

public class LevelSelectorActivity extends AppCompatActivity{

    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        context = this;

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                position++;
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("Level", position);
                startActivity(intent);
            }
        });
    }
}
