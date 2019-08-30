package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StudentTimetable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeueMed.ttf");

        TextView day = (TextView) findViewById(R.id.textDay);
        TextView timetable = (TextView) findViewById(R.id.textTimetable);

        day.setTypeface(type);

        timetable.setTypeface(type);


    }

    public void Back(View view){
        Intent intentBack = new Intent(this, StudentTimetableDay.class);
        startActivity(intentBack);
    }
}
