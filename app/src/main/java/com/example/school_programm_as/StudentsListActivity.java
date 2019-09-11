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
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentsListActivity extends AppCompatActivity {

    private TextView TVDay;
    private LinearLayout studentsList1;
    private LinearLayout studentsList2;
    private FirebaseFirestore mFirestore;
    private LinearLayout LLStates;
    private int buttonId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        TVDay = (TextView) findViewById(R.id.textView);
        studentsList1 = (LinearLayout) findViewById(R.id.studentsList1);
        studentsList2 = (LinearLayout) findViewById(R.id.studentsList2);
        LLStates = (LinearLayout) findViewById(R.id.states);

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
                                                final Button btn_pupil = new Button(getApplicationContext());
                                                btn_pupil.setId(buttonId);
                                                btn_pupil.setPadding(0,0,50,0);
                                                btn_pupil.setText(pupil.name + " " + pupil.pathronimic + " " + pupil.surname);
                                                switch (lesson.get(pupil.userId).toString()){
                                                    case("present"):
                                                        btn_pupil.setBackgroundResource(R.drawable.btn_pupil_present);
                                                        break;
                                                    case ("notpresent"):
                                                        btn_pupil.setBackgroundResource(R.drawable.btn_pupil_notpresent);
                                                        break;
                                                    case ("latecomer"):
                                                        btn_pupil.setBackgroundResource(R.drawable.btn_pupil_latecomer);
                                                        break;
                                                }
                                                btn_pupil.setLayoutParams(
                                                        new LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                                        )
                                                );
                                                btn_pupil.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        LLStates.setBackgroundResource(R.drawable.states);
                                                        Button btn_notpresent = new Button(getApplicationContext());
                                                        Button btn_latecomer = new Button(getApplicationContext());
                                                        final Button btn_present = new Button(getApplicationContext());
                                                        btn_notpresent.setId(buttonId);
                                                        btn_latecomer.setId(buttonId+1);
                                                        btn_present.setId(buttonId+2);
                                                        btn_notpresent.setBackgroundResource(R.drawable.notpresent_state);
                                                        btn_latecomer.setBackgroundResource(R.drawable.latecomer_state);
                                                        btn_present.setBackgroundResource(R.drawable.present_state);
                                                        btn_notpresent.setLayoutParams(new LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                                        ));
                                                        btn_latecomer.setLayoutParams(new LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                                        ));
                                                        btn_present.setLayoutParams(new LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                                        ));
                                                        btn_notpresent.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                DocumentReference docReflesson = mFirestore.collection("lessons").document(lesson.getId());
                                                                docReflesson.update(pupil.userId, "notpresent");
                                                                btn_pupil.setBackgroundResource(R.drawable.btn_pupil_notpresent);
                                                                LLStates.removeAllViews();
                                                                LLStates.setBackgroundColor(0000);
                                                                //Toast.makeText(getApplicationContext(), pupil.userId, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        btn_latecomer.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                DocumentReference docReflesson = mFirestore.collection("lessons").document(lesson.getId());
                                                                docReflesson.update(pupil.userId, "latecomer");
                                                                btn_pupil.setBackgroundResource(R.drawable.btn_pupil_latecomer);
                                                                LLStates.removeAllViews();
                                                                LLStates.setBackgroundColor(0000);
                                                                //Toast.makeText(getApplicationContext(), pupil.userId, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        btn_present.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                DocumentReference docReflesson = mFirestore.collection("lessons").document(lesson.getId());
                                                                docReflesson.update(pupil.userId, "present");
                                                                btn_pupil.setBackgroundResource(R.drawable.btn_pupil_present);
                                                                LLStates.removeAllViews();
                                                                LLStates.setBackgroundColor(0000);
                                                                //Toast.makeText(getApplicationContext(), pupil.userId, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        LLStates.addView(btn_notpresent);
                                                        LLStates.addView(btn_latecomer);
                                                        LLStates.addView(btn_present);

                                                    }
                                                });
                                                if(buttonId % 2 == 1) {
                                                    studentsList1.addView(btn_pupil);
                                                }else{
                                                    studentsList2.addView(btn_pupil);
                                                }
                                                buttonId++;
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
