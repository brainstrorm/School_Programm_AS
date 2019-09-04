package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreateTimetableActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private TextView TVDay;
    private LinearLayout linearLayout;
    private GestureDetector gestureDetector;
    private ArrayList<Integer> lessons = new ArrayList<>();
    private EditText ETLesson;
    private GestureDetector initGestureDetector() {
        return new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            private SwipeDetector detector = new SwipeDetector();

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                try {
                    if (detector.isSwipeDown(e1, e2, velocityY)) {
                        return false;
                    } else if (detector.isSwipeUp(e1, e2, velocityY)) {
                        showToast("Up Swipe");
                    }else if (detector.isSwipeLeft(e1, e2, velocityX)) {
                        showToast("Left Swipe");
                    } else if (detector.isSwipeRight(e1, e2, velocityX)) {
                        showToast("Right Swipe");
                    }
                } catch (Exception e) {} //for now, ignore
                return false;
            }

            private void showToast(String phrase){
                Toast.makeText(getApplicationContext(), phrase, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int countID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timetable);

        Intent intent = getIntent();
        String day = intent.getStringExtra(CreateClassActivity.DAY_MESSAGE);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        gestureDetector = initGestureDetector();

        mFirestore = FirebaseFirestore.getInstance();
        TVDay = (TextView) findViewById(R.id.textViewDay);

        if(day.equals("monday")){
            TVDay.setText("понедельник");
        }
        if(day.equals("tuesday")){
            TVDay.setText("вторник");
        }
        if(day.equals("wednesday")){
            TVDay.setText("среда");
        }
        if(day.equals("thursday")){
            TVDay.setText("четверг");
        }
        if(day.equals("friday")){
            TVDay.setText("пятница");
        }

    }

    public void plusLesson(View view){
        EditText lesson = new EditText(getApplicationContext());
        //Integer id = lesson.generateViewId();
        //Toast.makeText(CreateTimetableActivity.this, " " + lesson.getId(), Toast.LENGTH_SHORT).show();
        lesson.setId(countID);
        Toast.makeText(CreateTimetableActivity.this, " " + lesson.getId(), Toast.LENGTH_SHORT).show();
        lesson.setBackgroundResource(R.drawable.lesson_field);
        lesson.setHint("Введите текст");
        lesson.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        lesson.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        lesson.setPadding(10,0,0,0);
        lesson.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        /*lesson.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Button b = new Button(getApplicationContext());
                b.setId(countID++);
                b.setBackgroundResource(R.drawable.delete_lesson);
                b.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                );
                linearLayout.addView(b);
                return gestureDetector.onTouchEvent(event);
            }
        });*/
        /*lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        linearLayout.addView(lesson);
        lessons.add(lesson.getId());
        countID++;
    }

    public void saveTimetable(View view){
        Intent intent = getIntent();
        String groupId = intent.getStringExtra(CreateClassActivity.ID_MESSAGE);
        for(int i = 0; i < lessons.size(); i++){
            EditText lessonET = (EditText) findViewById(lessons.get(i));
            Map<String, Object> lesson_ = new HashMap<>();
            lesson_.put("name", lessonET.getText().toString().trim());
            lesson_.put("number", lessonET.getId());
            lesson_.put("group", groupId);
            mFirestore.collection("lessons")
                    .add(lesson_)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CreateTimetableActivity.this, ":)", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateTimetableActivity.this, ":(", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

}
