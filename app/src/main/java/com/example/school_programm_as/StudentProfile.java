package com.example.school_programm_as;

import androidx.annotation.ColorInt;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StudentProfile extends AppCompatActivity {

    public final static String ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String name,place,teacher,surname;
    private int bills;
    private TextView Name,Bills,Place,Teacher;
    private LinearLayout mLinearLayout;
    private String userIdforStudentTimetableDay;



    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    String formattedDate = df.format(c);
    private int id = 1;
    private String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);




        Intent intent = getIntent();
        final String userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
        userIdforStudentTimetableDay = userId;

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


                groupId = pupil_.group;
                Intent intentTimetableMainActivity = new Intent(StudentProfile.this, StudentTimetable.class);
                intentTimetableMainActivity.putExtra(ID_MESSAGE, groupId);


                //groupId = groupId.replaceAll("\\s",""); //потом можно убрать (был пробел)

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
                                    for (final QueryDocumentSnapshot document : task.getResult()){
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

                                        if (formattedDate.equals(lesson.date)) {
                                            class_.setText(lesson.name);
                                        }

                                        mLinearLayout = findViewById(R.id.timetable);
                                        mLinearLayout.addView(class_);
                                        id++;
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
        intentEnter.putExtra(ID_MESSAGE, userIdforStudentTimetableDay);
        startActivity(intentEnter);
    }

    public void QR(View view){
        Intent intentQR = new Intent(this, QRScan.class);
        startActivity(intentQR);
    }

    public void Back(View view){
        //need Logout -> error with Docref
        Intent intentBack = new Intent(this, LoginFormActivity.class);
        startActivity(intentBack);
    }
}
