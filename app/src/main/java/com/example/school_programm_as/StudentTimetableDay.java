package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentTimetableDay extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE = "ID";
    public final static String ID_MESSAGE_USER = "ID";
    private String groupId,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable_day);

        Intent intent = getIntent();

        groupId = (StudentProfile.ID_MESSAGE == null)? intent.getStringExtra(StudentProfile.ID_MESSAGE): intent.getStringExtra(StudentTimetable.ID_MESSAGE) ;
        userId = (StudentProfile.ID_MESSAGE_USER == null)? intent.getStringExtra(StudentProfile.ID_MESSAGE_USER):intent.getStringExtra(StudentTimetable.ID_MESSAGE_USER);
    }


    public void Back(View view){
        Intent intentBack = new Intent(StudentTimetableDay.this, StudentProfile.class);
        intentBack.putExtra(ID_MESSAGE_USER,userId);
        startActivity(intentBack);
    }


    public void Mondey(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "понедельник";
        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        intentTimetable.putExtra(ID_MESSAGE, groupId);

        startActivity(intentTimetable);

    }


    public void Tuesday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "вторник";
        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        intentTimetable.putExtra(ID_MESSAGE, groupId);
        startActivity(intentTimetable);


    }


    public void Wednesday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "среду";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        intentTimetable.putExtra(ID_MESSAGE, groupId);
        startActivity(intentTimetable);


    }


    public void Thursday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "четверг";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        intentTimetable.putExtra(ID_MESSAGE, groupId);
        startActivity(intentTimetable);


    }


    public void Friday(View view){

        Intent intentTimetable = new Intent(this, StudentTimetable.class);
        String message = "пятницу";

        intentTimetable.putExtra(EXTRA_MESSAGE, message);
        intentTimetable.putExtra(ID_MESSAGE, groupId);
        startActivity(intentTimetable);

    }



}
