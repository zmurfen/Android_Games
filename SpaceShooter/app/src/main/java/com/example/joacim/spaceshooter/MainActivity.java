package com.example.joacim.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by joacim on 2018-03-30.
 * Class to Login the user or to create a new user.
 * It will load existing progress for the user
 * from the database.
 *
 * A test user exist with the following values
 * name: "test"
 * pass: "12345"
 * email: "test@gmail.com"
 * level: 1
 * gold: 100
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DBHelper db ;
    private User user;
    private EditText textFieldUsername, textFieldPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        user = new User();

        textFieldUsername = findViewById(R.id.username);
        textFieldPassword = findViewById(R.id.password);
        AppCompatButton loginButton = findViewById(R.id.button_login);
        AppCompatButton signupButton = findViewById(R.id.button_signup);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_login:

                user.setName(textFieldUsername.getText().toString());
                user.setPassword(textFieldPassword.getText().toString());

                if(checkLogin()) {
                    Intent intent = new Intent(this, Menu.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.button_signup:
                //Start new intent with a sign up page
                //TODO
                //Create signup activity
                break;
        }
    }

    private boolean checkLogin() {
        User tempUser = db.getUser(user.getName());
        if(tempUser == null) { return false; }
        if(tempUser.getPassword().equals(user.getPassword())) {
            user = tempUser;
            return true;
        }
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            db.close();
        } catch (Exception e) {}
    }
}
