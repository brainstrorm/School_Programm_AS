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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentTimetable extends AppCompatActivity {


    public final static String ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE_USER = "ID_USER";


    private FirebaseFirestore mFirestore;
    private LinearLayout mLinearLayout;
    private String userId,groupId,message;
    private TextView Text;


    Date dayOfTheWeek = null;

    SimpleDateFormat sdfin = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat sdfout = new SimpleDateFormat("EEEE");


    private String dayOfSubj;
    private int id = 1;
    private int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeueMed.ttf");

        Text = findViewById(R.id.NotSubjects);


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("StudentTimetableDayActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
            message = extras.getString("MESSAGE");
        }


        Toast.makeText(StudentTimetable.this, groupId, Toast.LENGTH_SHORT ).show();
        TextView day = (TextView) findViewById(R.id.textDay);
        TextView timetable = (TextView) findViewById(R.id.textTimetable);

        if (message.equals("пятницу") || message.equals("среду")) {
            dayOfSubj = message.substring(0,message.length()-1)+"a";
        }
        else {
            dayOfSubj = message;
        }

        timetable.setText("Занятия на " + message);

        message = message.substring(0,1).toUpperCase() + message.substring(1);

        if (message.equals("Пятницу") || message.equals("Среду")) {

            message = message.substring(0,message.length()-1) + "а";

        }


        day.setText(message);


        day.setTypeface(type);
        timetable.setTypeface(type);

         mLinearLayout = (LinearLayout) findViewById(R.id.timetableday);


        mFirestore = FirebaseFirestore.getInstance();

        if (!groupId.equals("")) {
            mFirestore.collection("lessons").whereEqualTo("group", groupId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (final QueryDocumentSnapshot document : task.getResult()) {
                                    final Lesson lesson = document.toObject(Lesson.class);
                                    final TextView class_ = new TextView(getApplicationContext());
                                    class_.setId(id);
                                    class_.setTextSize(20);
                                    class_.setTextColor(0xFF8E7B89);

                                    class_.setLayoutParams(
                                            new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            )
                                    );


                                    if (lesson.date != null) {
                                        try {
                                            dayOfTheWeek = sdfin.parse(lesson.date);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        String day = sdfout.format(dayOfTheWeek);

                                        if (day.equals(dayOfSubj)) {
                                            class_.setText(lesson.name);
                                            mLinearLayout.addView(class_);
                                            cnt++;
                                        }
                                    }


                                    id++;
                                    Toast.makeText(StudentTimetable.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT).show();
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
        else {
            Text.setText("Занятий нет");
        }


    }

    public void Back(View view){
        Intent intentStudentTimetableDayActivity = new Intent(getApplicationContext(), StudentTimetableDay.class);
        intentStudentTimetableDayActivity.setAction("StudentTimetableActivity");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();



        intentStudentTimetableDayActivity.putExtra("USER_ID_MESSAGE", userId);
        intentStudentTimetableDayActivity.putExtra("GROUP_ID_MESSAGE", groupId);

        startActivity(intentStudentTimetableDayActivity);
    }

}
