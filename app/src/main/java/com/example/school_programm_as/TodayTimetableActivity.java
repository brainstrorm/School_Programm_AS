package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TodayTimetableActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private String groupId;
    private TextView TVGroupName;
    private LinearLayout mLinearLayout;
    private int id = 1;
    private String userId;
   private   int cnt = 0;

    SimpleDateFormat sdfin = new SimpleDateFormat("dd.MM.yyyy");
    Date d = new Date();
    String today = sdfin.format(d);

    private List<Lesson> subjects = new ArrayList<>();

    public final static String GROUP_ID_MESSAGE = "GROUP_ID_MESSAGE";
    public final static String USER_ID_MESSAGE = "USER_ID_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_timetable);

        final TextView Text;
        Text = findViewById(R.id.noLessons);

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





        mFirestore.collection("lessons").whereEqualTo("group", groupId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot document: task.getResult()) {

                                final Lesson lesson = document.toObject(Lesson.class);

                                if (lesson.date != null) {



                                    if (today.equals(lesson.date)) {

                                        cnt++;

                                        subjects.add(lesson);

                                          }

                                    }
                            }
                            id = 0;
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

                            for(final QueryDocumentSnapshot document: task.getResult()) {

                                final Lesson lesson = subjects.get(id);
                                if (lesson.date != null) {




                                    if (today.equals(lesson.date)){

                                        final Button lesson_ = new Button(getApplicationContext());
                                        lesson_.setId(id);
                                        lesson_.setBackgroundResource(R.drawable.class_field);
                                        lesson_.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                )
                                        );


                                        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/helveticaneuemed.ttf");
                                        lesson_.setTextColor(0xFFFFFFFF);
                                        lesson_.setTextSize(22);
                                        lesson_.setTypeface(font);
                                        lesson_.setTypeface(null, Typeface.BOLD);
                                        lesson_.setAllCaps(false);




                                        lesson_.setText(subjects.get(id).name);
                                        lesson_.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intentTodayTimetableActivity = new Intent(getApplicationContext(), StudentsListActivity.class);
                                                Bundle extras = new Bundle();
                                                String message = document.getId();
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                extras.putString("SUBJECT_MESSAGE", lesson.name);
                                                extras.putString("USER_ID_MESSAGE", userId);
                                                extras.putString("GROUP_ID_MESSAGE", groupId);
                                                extras.putString("LESSON_ID_MESSAGE", message);
                                                intentTodayTimetableActivity.putExtras(extras);
                                                startActivity(intentTodayTimetableActivity);
                                            }
                                        });
                                        mLinearLayout.addView(lesson_);
                                    }
                                }
                                id++;
                            }

                        }
                        if (cnt == 0) {
                            Text.setText("Занятий нет");
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
        Bundle extras_ = new Bundle();
        extras_.putString("USER_ID_MESSAGE", userId);
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        intentQRCode.putExtras(extras_);
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
