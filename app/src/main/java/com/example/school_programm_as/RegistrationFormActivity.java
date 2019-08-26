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

public class RegistrationFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        Intent intent = getIntent();
        String message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.imageView3);

        if(message.equals("teacher")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

    }

    public void Back(View view){
        Intent intentBack = new Intent(this, RegisterActivity.class);
        startActivity(intentBack);
    }

}
