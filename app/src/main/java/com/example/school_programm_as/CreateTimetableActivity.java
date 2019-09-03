package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTimetableActivity extends AppCompatActivity {

    private TextView TVDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timetable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String day = intent.getStringExtra(CreateClassActivity.DAY_MESSAGE);

        TVDay = (TextView) findViewById(R.id.textViewDay);

        if(day.equals("monday")){
            TVDay.setText("понедельник");
        }
        if(day.equals("tuesday")){
            TVDay.setText("вторник");
        }
        if(day.equals("wednesday")){
            TVDay.setText("среда");
        }
        if(day.equals("thursday")){
            TVDay.setText("четверг");
        }
        if(day.equals("friday")){
            TVDay.setText("пятница");
        }

    }

}
