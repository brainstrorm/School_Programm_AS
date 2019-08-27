package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Login_Authorization.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.image_authorization);

        if(message.equals("teacher")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
            imageView.setImageResource(id);
        }

    }

    @Override
    public void onClick(View view) {

    }
}