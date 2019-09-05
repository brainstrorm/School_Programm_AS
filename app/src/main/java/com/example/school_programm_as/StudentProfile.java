package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class StudentProfile extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String name,place,teacher,surname;
    private int bills;
    private TextView Name,Bills,Place,Teacher;

    TextView textView[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);


        Intent intent = getIntent();
        final String userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);

        Name = findViewById(R.id.studentName);
        //Bills = findViewById(R.id.amountBills);
        Teacher = findViewById(R.id.studentTeacher);
        Place = findViewById(R.id.studentClass);




        mFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef_users = mFirestore.collection("users").document(userId);
        docRef_users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pupil pupil_ = documentSnapshot.toObject(Pupil.class);
                name = pupil_.name;
                surname = pupil_.surname;
                //bills = ((Pupil) pupil_).bill;
                place ="класс: " + ((Pupil) pupil_).group;


                Name.setText(surname+" "+name);
                //Bills.setText(bills);
                Place.setText(place);


                String groupId = pupil_.group;
                DocumentReference docRef_groups = mFirestore.collection("groups").document(groupId);
                docRef_groups.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Group group_ = documentSnapshot.toObject(Group.class);

                         //teacher = "преподаватель: " + group_.teacherFullName;
                         Teacher.setText(teacher);

                    }

                });

                /*DocumentReference docRef_lessons = mFirestore.collection("lessons").document(groupId);
                docRef_lessons.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Lesson lesson_ = documentSnapshot.toObject(Lesson.class);

                        teacher = "преподаватель: " + group_.teacherName;

                    }
                });
                */
            }
        });



    }

    public void Enter(View view){
        Intent intentEnter = new Intent(this, StudentTimetableDay.class);
        startActivity(intentEnter);
    }

    public void QR(View view){
        Intent intentEnter = new Intent(this, QRScan.class);
        startActivity(intentEnter);
    }

    public void back(View view){

    }
}
