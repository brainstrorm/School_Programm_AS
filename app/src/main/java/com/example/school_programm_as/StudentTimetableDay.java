package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentTimetableDay extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable_day);

        Intent intent = getIntent();
    }


    public void Back(View view){
        Intent intentBack = new Intent(this, StudentProfile.class);
        startActivity(intentBack);
    }


    public void Mondey(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "понедельник";
        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTimetable);

    }


    public void Tuesday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "вторник";
        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTimetable);


    }


    public void Wednesday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "среду";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTimetable);


    }


    public void Thursday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "четверг";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTimetable);


    }


    public void Friday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "пятницу";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTimetable);

    }



}
