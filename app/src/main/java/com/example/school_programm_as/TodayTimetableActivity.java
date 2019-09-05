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

public class TodayTimetableActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private String groupId;
    private TextView TVGroupName;
    private LinearLayout mLinearLayout;
    private int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_timetable);

        Intent intent = getIntent();
        groupId = intent.getStringExtra(TeacherMainActivity.ID_MESSAGE);

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
                                Lesson lesson = document.toObject(Lesson.class);
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
                                /*lesson_.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intentTodayTimetableActivity = new Intent(TeacherMainActivity.this, CreateClassActivity.class);
                                        String message = document.getId();
                                        intentTodayTimetableActivity.putExtra(ID_MESSAGE, message);
                                        startActivity(intentTodayTimetableActivity);
                                    }
                                });*/
                                mLinearLayout.addView(lesson_);
                            }
                        }
                    }
                });

    }

}
