package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TodayTimetableActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private String groupId;
    private TextView TVGroupName;
    private LinearLayout mLinearLayout;
    private int id = 1;
    private String userId;

    public final static String GROUP_ID_MESSAGE = "GROUP_ID_MESSAGE";
    public final static String USER_ID_MESSAGE = "USER_ID_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_timetable);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //groupId = intent.getStringExtra(TeacherMainActivity.GROUP_ID_MESSAGE);
        if(intent.getAction().equals("StudentsListActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("ID_MESSAGE");
        }
        //Toast.makeText(this, groupId, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        TVGroupName = (TextView) findViewById(R.id.textView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("groups").document(groupId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Group group = documentSnapshot.toObject(Group.class);
                TVGroupName.setText(group.name);
            }
        });
        mFirestore.collection("lessons").whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot document: task.getResult()){
                                final Lesson lesson = document.toObject(Lesson.class);
                                final Button lesson_ = new Button(getApplicationContext());
                                lesson_.setId(id);
                                lesson_.setBackgroundResource(R.drawable.class_field);
                                lesson_.setLayoutParams(
                                        new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        )
                                );
                                lesson_.setText(lesson.name);
                                lesson_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intentTodayTimetableActivity = new Intent(getApplicationContext(), StudentsListActivity.class);
                                        Bundle extras = new Bundle();
                                        String message = document.getId();
                                        extras.putString("SUBJECT_MESSAGE", lesson.name);
                                        extras.putString("USER_ID_MESSAGE", userId);
                                        extras.putString("GROUP_ID_MESSAGE", groupId);
                                        intentTodayTimetableActivity.putExtras(extras);
                                        startActivity(intentTodayTimetableActivity);
                                    }
                                });
                                mLinearLayout.addView(lesson_);
                            }
                        }
                    }
                });

    }


    public void QRcode(View view){
        Intent intentQRCode = new Intent(TodayTimetableActivity.this, MyQRCodeActivity.class);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("StudentsListActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("ID_MESSAGE");
        }
        intentQRCode.putExtras(extras);
        startActivity(intentQRCode);
    }

    public void back(View view){
        Intent intentTeacherMainActivity = new Intent(getApplicationContext(), TeacherMainActivity.class);
        intentTeacherMainActivity.setAction("TodayTimetableActivity");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("StudentsListActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("ID_MESSAGE");
        }
        intentTeacherMainActivity.putExtra("USER_ID_MESSAGE", userId);
        startActivity(intentTeacherMainActivity);
    }

}
