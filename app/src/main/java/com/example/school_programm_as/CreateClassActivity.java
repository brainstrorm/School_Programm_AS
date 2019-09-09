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
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
        }
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_create_class);
        ConstraintLayout toolbar = findViewById(R.id.toolbar);

        mFirestore = FirebaseFirestore.getInstance();

        //button_back = (Button) findViewById(R.id.button3);

    }

    public void back(View view){
        Intent intentBack = new Intent(this, TeacherMainActivity.class);
        intentBack.setAction("CreateClassActivity");
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        intentBack.putExtra(EXTRA_MESSAGE, userId);
        EditText class_name = (EditText) findViewById(R.id.editText7);
        if(class_name.getText().toString().trim().equals("")) {
            DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
            docRefGroup.delete();
        }
        startActivity(intentBack);
    }

    public void Monday(View view){
        Intent intentMonday = new Intent(this, CreateTimetableActivity.class);
        String message = "monday";
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        intentMonday.putExtra(DAY_MESSAGE, message);
        intentMonday.putExtra(ID_MESSAGE, groupId);
        intentMonday.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        Toast.makeText(this, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE), Toast.LENGTH_SHORT).show();
        startActivity(intentMonday);
    }

    public void Tuesday(View view){
        Intent intentTuesday = new Intent(this, CreateTimetableActivity.class);
        String message = "tuesday";
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        intentTuesday.putExtra(DAY_MESSAGE, message);
        intentTuesday.putExtra(ID_MESSAGE, groupId);
        intentTuesday.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        startActivity(intentTuesday);
    }

    public void Wednesday(View view){
        Intent intentWednesday = new Intent(this, CreateTimetableActivity.class);
        String message = "wednesday";
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        intentWednesday.putExtra(DAY_MESSAGE, message);
        intentWednesday.putExtra(ID_MESSAGE, groupId);
        intentWednesday.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        startActivity(intentWednesday);
    }

    public void Thursday(View view){
        Intent intentThursday = new Intent(this, CreateTimetableActivity.class);
        String message = "thursday";
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        intentThursday.putExtra(DAY_MESSAGE, message);
        intentThursday.putExtra(ID_MESSAGE, groupId);
        intentThursday.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        startActivity(intentThursday);
    }
    public void Friday(View view){
        Intent intentFriday = new Intent(this, CreateTimetableActivity.class);
        String message = "friday";
        Intent intent = getIntent();
        String groupId = "";
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getStringExtra(CreateTimetableActivity.USER_ID_MESSAGE);
            groupId = intent.getStringExtra(CreateTimetableActivity.GROUP_ID_MESSAGE);
        }
        intentFriday.putExtra(DAY_MESSAGE, message);
        intentFriday.putExtra(ID_MESSAGE, groupId);
        intentFriday.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        startActivity(intentFriday);
    }

    public void saveClass(View view){
        Intent intentSaveClass = new Intent(this, TeacherMainActivity.class);
        intentSaveClass.setAction("CreateClassActivity");
        EditText class_name = (EditText) findViewById(R.id.editText7);
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(TeacherMainActivity.EXTRA_MESSAGE);
        intentSaveClass.putExtra(EXTRA_MESSAGE, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE));
        if(!class_name.getText().toString().trim().equals("")) {
            DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
            docRefGroup.update("name", class_name.getText().toString().trim());
            startActivity(intentSaveClass);
        }else{
            Toast.makeText(this, "Введите название группы!", Toast.LENGTH_SHORT).show();
        }
        //Intent intentSaveClass = new Intent(this, TeacherMainActivity.class);
        //startActivity(intentSaveClass);
    }

}
