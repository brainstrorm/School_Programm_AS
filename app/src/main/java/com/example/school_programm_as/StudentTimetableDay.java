package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentTimetableDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable_day);
    }


    public void Back(View view){
        Intent intentBack = new Intent(this, StudentProfile.class);
        startActivity(intentBack);
    }
}
