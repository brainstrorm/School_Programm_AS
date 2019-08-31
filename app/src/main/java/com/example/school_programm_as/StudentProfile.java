package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
    }

    public void Enter(View view){
        Intent intentEnter = new Intent(this, StudentTimetableDay.class);
        startActivity(intentEnter);
    }

    public void QR(View view){
        Intent intentEnter = new Intent(this, QRScan.class);
        startActivity(intentEnter);
    }
}
