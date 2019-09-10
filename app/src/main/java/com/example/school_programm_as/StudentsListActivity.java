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
import android.widget.TextView;
import android.widget.Toast;

public class StudentsListActivity extends AppCompatActivity {

    private TextView TVDay;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        TVDay = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String day = extras.getString("SUBJECT_MESSAGE");
        final String lessonId = extras.getString("LESSON_ID_MESSAGE");
        //Toast.makeText(getApplicationContext(), lessonId, Toast.LENGTH_SHORT).show();
        final String groupId = extras.getString("GROUP_ID_MESSAGE");
        TVDay.setText(day);

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("lessons").document(lessonId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(final DocumentSnapshot documentSnapshot) {
                        final DocumentSnapshot lesson = documentSnapshot;
                        mFirestore.collection("users").whereEqualTo("group",groupId).whereEqualTo("role", "pupil")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(final QueryDocumentSnapshot document : task.getResult()) {
                                                final Pupil pupil = document.toObject(Pupil.class);
                                                DocumentReference docReflesson = mFirestore.collection("lessons").document(lesson.getId());
                                                docReflesson.update(pupil.userId, "present");
                                                Toast.makeText(getApplicationContext(), pupil.userId, Toast.LENGTH_SHORT).show();
                                                //lesson.getDocumentReference(lesson.getId()).update("hK955CPOlsUra1e15cTgNEPvat22", "present");
                                            }
                                        }else{

                                        }
                                    }
                                });
                    }
                });

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
