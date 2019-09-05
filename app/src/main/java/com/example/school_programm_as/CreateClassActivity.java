package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.school_programm_as.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateClassActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE = "ID";
    public final static String EXTRA_MESSAGE = "EXTRA";
    private FirebaseFirestore mFirestore;
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_create_class);
        ConstraintLayout toolbar = findViewById(R.id.toolbar);

        mFirestore = FirebaseFirestore.getInstance();

        //button_back = (Button) findViewById(R.id.button3);

    }

    /*public void back(View view){
        Intent intentBack = new Intent(this, TeacherMainActivity.class);
        String message = "back";
        intentBack.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentBack);
    }*/

    public void Monday(View view){
        Intent intentMonday = new Intent(this, CreateTimetableActivity.class);
        String message = "monday";
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentMonday.putExtra(DAY_MESSAGE, message);
        intentMonday.putExtra(ID_MESSAGE, groupId);
        startActivity(intentMonday);
    }

    public void Tuesday(View view){
        Intent intentTuesday = new Intent(this, CreateTimetableActivity.class);
        String message = "tuesday";
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentTuesday.putExtra(DAY_MESSAGE, message);
        intentTuesday.putExtra(ID_MESSAGE, groupId);
        startActivity(intentTuesday);
    }

    public void Wednesday(View view){
        Intent intentWednesday = new Intent(this, CreateTimetableActivity.class);
        String message = "wednesday";
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentWednesday.putExtra(DAY_MESSAGE, message);
        intentWednesday.putExtra(ID_MESSAGE, groupId);
        startActivity(intentWednesday);
    }

    public void Thursday(View view){
        Intent intentThursday = new Intent(this, CreateTimetableActivity.class);
        String message = "thursday";
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentThursday.putExtra(DAY_MESSAGE, message);
        intentThursday.putExtra(ID_MESSAGE, groupId);
        startActivity(intentThursday);
    }
    public void Friday(View view){
        Intent intentFriday = new Intent(this, CreateTimetableActivity.class);
        String message = "friday";
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentFriday.putExtra(DAY_MESSAGE, message);
        intentFriday.putExtra(ID_MESSAGE, groupId);
        startActivity(intentFriday);
    }

    public void saveClass(View view){
        EditText class_name = (EditText) findViewById(R.id.editText7);
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
        docRefGroup.update("name", class_name.getText().toString().trim());
        //Intent intentSaveClass = new Intent(this, TeacherMainActivity.class);
        //startActivity(intentSaveClass);
    }

}
