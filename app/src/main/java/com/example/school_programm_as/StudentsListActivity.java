package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class StudentsListActivity extends AppCompatActivity {

    private TextView TVDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        TVDay = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String day = extras.getString("SUBJECT_MESSAGE");
        TVDay.setText(day);
    }

    public void back(View view){
        Intent intentTodayTimetableActivity = new Intent(getApplicationContext(), TodayTimetableActivity.class);
        intentTodayTimetableActivity.setAction("StudentsListActivity");
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", getIntent().getExtras().getString("USER_ID_MESSAGE"));
        extras.putString("GROUP_ID_MESSAGE", getIntent().getExtras().getString("GROUP_ID_MESSAGE"));
        intentTodayTimetableActivity.putExtras(extras);
        startActivity(intentTodayTimetableActivity);
    }

}
