package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreateTimetableActivity extends AppCompatActivity {

    public final static String EXTRA_ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String USER_ID_MESSAGE = "";
    public final static String GROUP_ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";


    private FirebaseFirestore mFirestore;
    private TextView TVDay;
    private LinearLayout mlinearLayout;
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
    private String user_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timetable);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String day = extras.getString("DAY_MESSAGE");
        mlinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        gestureDetector = initGestureDetector();

        mFirestore = FirebaseFirestore.getInstance();
        TVDay = (TextView) findViewById(R.id.textViewDay);

        if(day.equals("monday")){
            TVDay.setText("Понедельник");
        }
        if(day.equals("tuesday")){
            TVDay.setText("Вторник");
        }
        if(day.equals("wednesday")){
            TVDay.setText("Среда");
        }
        if(day.equals("thursday")){
            TVDay.setText("Четверг");
        }
        if(day.equals("friday")){
            TVDay.setText("Пятница");
        }

    }

    public void plusLesson(View view){
        final HorizontalScrollView scrollView = new HorizontalScrollView(getApplicationContext());
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );
        scrollView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        final EditText lesson = new EditText(getApplicationContext());
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
        Button btn_delete = new Button(getApplicationContext());
        btn_delete.setBackgroundResource(R.drawable.delete_subject);
        btn_delete.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lessons.remove(lesson.getId());
                mlinearLayout.removeView(scrollView);
            }
        });
        linearLayout.addView(lesson);
        linearLayout.addView(btn_delete);
        scrollView.addView(linearLayout);
        mlinearLayout.addView(scrollView);
        lessons.add(lesson.getId());
        countID++;
    }

    public void saveTimetable(View view){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String groupId = extras.getString("GROUP_ID_MESSAGE");
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
        Intent intentBackToCreateClassActivity = new Intent(this, CreateClassActivity.class);
        intentBackToCreateClassActivity.setAction("CreateTimetableActivity");
        Bundle extras_ = new Bundle();
        extras_.putString("USER_ID_MESSAGE", intent.getExtras().getString("USER_ID_MESSAGE"));
        extras_.putString("GROUP_ID_MESSAGE", intent.getExtras().getString("GROUP_ID_MESSAGE"));
        intentBackToCreateClassActivity.putExtras(extras_);
        startActivity(intentBackToCreateClassActivity);
    }

    public void backToCreateClass(View view){
        Intent intentBackToCreateClassActivity = new Intent(this, CreateClassActivity.class);
        intentBackToCreateClassActivity.setAction("CreateTimetableActivity");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        extras.putString("USER_ID_MESSAGE", intent.getExtras().getString("USER_ID_MESSAGE"));
        extras.putString("GROUP_ID_MESSAGE", intent.getExtras().getString("GROUP_ID_MESSAGE"));
        intentBackToCreateClassActivity.putExtras(extras);
        startActivity(intentBackToCreateClassActivity);
    }

}
