package com.example.joacim.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joacim on 2018-03-30.
 */

public class SignInOrRegister extends AppCompatActivity implements View.OnClickListener{

    private DBHelper db ;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinorregister);

        db = new DBHelper(this);
        user = new User();

        EditText textFieldUsername = findViewById(R.id.username);
        EditText textFieldPassword = findViewById(R.id.password);
        AppCompatButton loginButton = findViewById(R.id.button_login);
        AppCompatButton signupButton = findViewById(R.id.button_signup);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);

        textFieldUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT) {
                    user.setName(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });

        textFieldPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT) {
                    user.setPassword(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        //Check if user exist or not
        //If not Toast with no user with that name exist
        //Or password does not match
        //Or transfer into a new activity menu activity that is to be created

        switch(v.getId()) {
            case R.id.button_login:
                break;
            case R.id.button_signup:
                break;
        }
    }
}
