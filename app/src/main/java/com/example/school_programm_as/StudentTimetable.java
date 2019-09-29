package com.example.school_programm_as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1beta1.StructuredQuery;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StudentTimetable extends AppCompatActivity {


    public final static String ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE_USER = "ID_USER";


    private FirebaseFirestore mFirestore;
    private LinearLayout mLinearLayout;
    private String userId,groupId,date;
    private TextView Text;
    private List<Lesson> subjects = new ArrayList<>();


    Date dayOfTheWeek = null;


    private int id = 1;
    private int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);

        Text = findViewById(R.id.NotSubjects);


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("StudentTimetableDayActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
            date = extras.getString("DATE_MESSAGE");
        }


        Toast.makeText(StudentTimetable.this, groupId, Toast.LENGTH_SHORT ).show();
        TextView day = (TextView) findViewById(R.id.textDay);



        day.setText(date);

         mLinearLayout = (LinearLayout) findViewById(R.id.timetableday);


        mFirestore = FirebaseFirestore.getInstance();

        if (!groupId.equals("")) {




            mFirestore.collection("lessons").whereEqualTo("group",groupId).whereEqualTo("date",date).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (final QueryDocumentSnapshot document : task.getResult()) {
                                    final Lesson lesson = document.toObject(Lesson.class);
                                    final TextView class_ = new TextView(getApplicationContext());
                                    class_.setId(id);
                                    class_.setTextColor(0xFF8E7B89);
                                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/helveticaneuemed.ttf");
                                    class_.setTypeface(font);

                                    class_.setLayoutParams(
                                            new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            )
                                    );


                                    if (lesson.date != null) {

                                        String day = lesson.date;

                                        //if (day.equals(dayOfSubj)) {

                                            class_.setTextSize(25);
                                            class_.setGravity(1);

                                            subjects.add(lesson);
                                            // class_.setText(lesson.name);
                                            mLinearLayout.addView(class_);
                                            cnt++;
                                        //}
                                    }


                                    id++;
                                    Toast.makeText(StudentTimetable.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT).show();
                                }

                                for (int j = 0 ; j < subjects.size(); j++) {
                                    for (int i = 0; i < subjects.size() - 1; i++) {
                                        Lesson p = subjects.get(i);
                                        Lesson cnt;
                                        Lesson next = subjects.get(i + 1);
                                        if (p.getNumber() >= next.getNumber()) {
                                            cnt = p;
                                            subjects.set(i, next);
                                            subjects.set(i + 1, cnt);
                                        }
                                    }
                                }

                                for (int i = 0 ; i < subjects.size(); i++) {
                                    final TextView class_ = new TextView(getApplicationContext());
                                    class_.setId(id);
                                    class_.setTextColor(0xFF8E7B89);
                                    Typeface font = Typeface.createFromAsset(getAssets(),"fonts/helveticaneuemed.ttf");
                                    class_.setTypeface(font);
                                    class_.setTextSize(25);
                                    class_.setGravity(1);


                                    class_.setLayoutParams(
                                            new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            )
                                    );

                                    class_.setText(subjects.get(i).name);
                                    mLinearLayout.addView(class_);

                                }

                        } else {
                                Toast.makeText(StudentTimetable.this, "Информация о группах не получена", Toast.LENGTH_SHORT).show();
                            }

                            if (cnt == 0) {
                                Text.setText("Занятий нет");
                            }
                        }
                    });
        }

            }



    public void Back(View view){
        Intent intentStudentTimetableDayActivity = new Intent(getApplicationContext(), StudentTimetableDay.class);
        intentStudentTimetableDayActivity.setAction("StudentTimetableActivity");
        Intent intent = getIntent();
        Bundle extras = new Bundle();

        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
       /// extras.putString("DATE_MESSAGE", date);

        if(getIntent().getExtras().getString("PARENT_ID_MESSAGE") != null)
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        intentStudentTimetableDayActivity.putExtras(extras);
        startActivity(intentStudentTimetableDayActivity);
    }

}
