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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentTimetable extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private LinearLayout mLinearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeueMed.ttf");

        Intent intent = getIntent();

        final String message = intent.getStringExtra(StudentTimetableDay.EXTRA_MESSAGE);

        final String groupId = intent.getStringExtra(StudentProfile.EXTRA_MESSAGE);

        TextView day = (TextView) findViewById(R.id.textDay);
        TextView timetable = (TextView) findViewById(R.id.textTimetable);

        day.setText(message);
        timetable.setText("Занятия на " + message);

        //day.setTypeface(type);
        //timetable.setTypeface(type);

         mLinearLayout = (LinearLayout) findViewById(R.id.timetableday);


        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("lessons").whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Lesson lesson = document.toObject(Lesson.class);
                                TextView class_ = new TextView(getApplicationContext());
                                class_.setTextSize(20);
                                class_.setTextColor(0xFF8E7B89);

                                class_.setLayoutParams(
                                        new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        )
                                );


                                    class_.setText(lesson.name);

                                mLinearLayout = findViewById(R.id.timetableday);
                                mLinearLayout.addView(class_);
                                Toast.makeText(StudentTimetable.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT ).show();
                            }
                        }else{
                            Toast.makeText(StudentTimetable.this, "Информация о группах не получена", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });


    }

    public void Back(View view){
        Intent intentBack = new Intent(this, StudentTimetableDay.class);
        startActivity(intentBack);
    }
}
