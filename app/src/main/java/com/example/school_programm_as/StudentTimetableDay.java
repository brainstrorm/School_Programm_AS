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

        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("StudentProfileActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("StudentTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }

           }


    public void Back(View view){
        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentProfile.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();

        Bundle extras_ = intent.getExtras();
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", extras_.getString("USER_ID_MESSAGE"));
        extras.putString("GROUP_ID_MESSAGE", extras_.getString("GROUP_ID_MESSAGE"));
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);
    }


    public void Mondey(View view){


        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentTimetable.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();
        String message = "понедельник";
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        extras.putString("MESSAGE", message);
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);

    }


    public void Tuesday(View view){


        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentTimetable.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();
        String message = "вторник";
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        extras.putString("MESSAGE", message);

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);

    }


    public void Wednesday(View view){


        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentTimetable.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();
        String message = "среду";
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        extras.putString("MESSAGE", message);
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);


    }


    public void Thursday(View view){


        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentTimetable.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();
        String message = "четверг";
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        extras.putString("MESSAGE", message);
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);


    }


    public void Friday(View view){


        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentTimetable.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();
        String message = "пятницу";
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        extras.putString("MESSAGE", message);
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);
    }



}
