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
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        intentBack.putExtra("USER_ID_MESSAGE", userId);
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
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("DAY_MESSAGE", message);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        intentMonday.putExtras(extras_);
        //Toast.makeText(this, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE), Toast.LENGTH_SHORT).show();
        startActivity(intentMonday);
    }

    public void Tuesday(View view){
        Intent intentTuesday = new Intent(this, CreateTimetableActivity.class);
        String message = "tuesday";
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("DAY_MESSAGE", message);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        intentTuesday.putExtras(extras_);
        startActivity(intentTuesday);
    }

    public void Wednesday(View view){
        Intent intentWednesday = new Intent(this, CreateTimetableActivity.class);
        String message = "wednesday";
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("DAY_MESSAGE", message);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        intentWednesday.putExtras(extras_);
        startActivity(intentWednesday);
    }

    public void Thursday(View view){
        Intent intentThursday = new Intent(this, CreateTimetableActivity.class);
        String message = "thursday";
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("DAY_MESSAGE", message);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        intentThursday.putExtras(extras_);
        startActivity(intentThursday);
    }
    public void Friday(View view){
        Intent intentFriday = new Intent(this, CreateTimetableActivity.class);
        String message = "friday";
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("DAY_MESSAGE", message);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        intentFriday.putExtras(extras_);
        startActivity(intentFriday);
    }

    public void saveClass(View view){
        Intent intentSaveClass = new Intent(this, TeacherMainActivity.class);
        intentSaveClass.setAction("CreateClassActivity");
        EditText class_name = (EditText) findViewById(R.id.editText7);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String groupId = extras.getString("GROUP_ID_MESSAGE");
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
        }
        //Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        intentSaveClass.putExtra("USER_ID_MESSAGE", userId);
        if(!class_name.getText().toString().trim().equals("")) {
            DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
            docRefGroup.update("name", class_name.getText().toString().trim());
            startActivity(intentSaveClass);
        }else{
            Toast.makeText(this, "Введите название группы!", Toast.LENGTH_SHORT).show();
        }
    }

}
