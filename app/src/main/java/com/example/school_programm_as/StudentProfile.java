package com.example.school_programm_as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class StudentProfile extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String name,place,teacher,surname;
    private int bills;
    private TextView Name,Bills,Place,Teacher;
    private LinearLayout mLinearLayout;


    TextView textView[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);


        Intent intent = getIntent();
        final String userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);

        Name = findViewById(R.id.studentName);
        Bills = findViewById(R.id.amountBills);
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
                bills = pupil_.bill;

                Name.setText(surname+" "+name);
                Bills.setText(Integer.toString(bills));
                Place.setText(place);


                String groupId = pupil_.group;
                groupId = groupId.replaceAll("\\s","");

                DocumentReference docRef_groups = mFirestore.collection("groups").document(groupId);
                docRef_groups.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Group group_ = documentSnapshot.toObject(Group.class);

                        place ="класс: " + group_.name;
                        teacher = "преподаватель: " + group_.teacherFullName;
                         Teacher.setText(teacher);
                         Place.setText(place);

                    }

                });


                mLinearLayout = (LinearLayout) findViewById(R.id.timetable);

                mFirestore.collection("lessons").whereEqualTo("group", groupId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        Lesson lesson = document.toObject(Lesson.class);
                                        TextView class_ = new TextView(getApplicationContext());

                                        class_.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                )
                                        );

                                        class_.setText(lesson.name);
                                        mLinearLayout = findViewById(R.id.timetable);
                                        mLinearLayout.addView(class_);
                                        Toast.makeText(StudentProfile.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT ).show();
                                    }
                                }else{
                                    Toast.makeText(StudentProfile.this, "Информация о группах не получена", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        });

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
