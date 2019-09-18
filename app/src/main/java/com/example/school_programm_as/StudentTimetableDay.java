package com.example.school_programm_as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.Set;

public class StudentTimetableDay extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE = "ID";
    public final static String ID_MESSAGE_USER = "ID";
    private String groupId,userId;
    private Set<String> Check = new HashSet<>();
    private FirebaseFirestore mFirestore;
    private int id = 0;



    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_timetable_day);



        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("StudentProfileActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("StudentTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }





        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        mFirestore = FirebaseFirestore.getInstance();
                mFirestore.collection("lessons").whereEqualTo("group",groupId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){

                                    for (final QueryDocumentSnapshot document : task.getResult()) {

                                        final Lesson lesson = document.toObject(Lesson.class);
                                        Check.add(lesson.date);

                                    }



                                        for (final String cur_date: Check) {




                                            final Button class_ = new Button(getApplicationContext());
                                            class_.setId(id);
                                            class_.setBackgroundResource(R.drawable.rectangle_10);
                                            class_.setLayoutParams(
                                                    new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                                    )
                                            );




                                            class_.setTextSize(25);
                                            class_.setTextColor(0xFFFFFFFF);
                                            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/helveticaneuemed.ttf");
                                            class_.setTypeface(font);
                                            class_.setTypeface(null, Typeface.BOLD);
                                            class_.setAllCaps(false);

                                            class_.setText(cur_date);
                                            class_.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intentStudentTimetableToday = new Intent(StudentTimetableDay.this, StudentTimetable.class);
                                                    intentStudentTimetableToday.setAction("StudentTimetableDayActivity");
                                                    Bundle extras = new Bundle();
                                                    String message = groupId;
                                                    String message_date = cur_date;

                                                    extras.putString("GROUP_ID_MESSAGE", message);
                                                    extras.putString("DATE_MESSAGE", message_date);
                                                    extras.putString("USER_ID_MESSAGE", userId);

                                                    Toast.makeText(StudentTimetableDay.this, "Выполнено", Toast.LENGTH_SHORT).show();

                                                    intentStudentTimetableToday.putExtras(extras);
                                                    startActivity(intentStudentTimetableToday);
                                                }
                                            });


                                            mLinearLayout.addView(class_);
                                            mLinearLayout.removeView(mScrollView);
                                            id++;

                                            Toast.makeText(StudentTimetableDay.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT).show();
                                        }
                                }else{
                                    Toast.makeText(StudentTimetableDay.this, "Информация о группах не получена", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        });
            }



    public void Back(View view){
        Intent intentStudentProfileActivity = new Intent(getApplicationContext(), StudentProfile.class);
        intentStudentProfileActivity.setAction("StudentTimetableDayActivity");
        Intent intent = getIntent();

        Bundle extras_ = intent.getExtras();
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", extras_.getString("USER_ID_MESSAGE"));
        extras.putString("GROUP_ID_MESSAGE", extras_.getString("GROUP_ID_MESSAGE"));
        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);
    }


}
