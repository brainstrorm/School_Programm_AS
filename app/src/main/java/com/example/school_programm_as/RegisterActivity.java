package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();

    }

    public void Student(View view){
        Intent intentStudent = new Intent(this, RegistrationFormActivity.class);
        String message = "student";
        intentStudent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentStudent);
    }
    public void Teacher(View view){
        Intent intentTeacher = new Intent(this, RegistrationFormActivity.class);
        String message = "teacher";
        intentTeacher.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentTeacher);
    }
    public void Parent(View view){
        Intent intentParent = new Intent(this, RegistrationFormActivity.class);
        String message = "parent";
        intentParent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentParent);
    }
    public void Administrator(View view){
        Intent intentAdministrator = new Intent(this, RegistrationFormActivity.class);
        String message = "administrator";
        intentAdministrator.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentAdministrator);
    }

}
