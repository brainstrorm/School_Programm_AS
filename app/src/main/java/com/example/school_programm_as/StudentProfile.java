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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StudentProfile extends AppCompatActivity {

    public final static String ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE_USER = "USER";


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String name,place,teacher,surname;
    private int bills;
    private TextView Name,Bills,Place,Teacher;
    private LinearLayout mLinearLayout;
    String userId,groupId;
    private String userIdforStudentTimetableDay;
    private String groupIdforStudentTimetableDay;

    private Button logout_back;

    SimpleDateFormat sdfout = new SimpleDateFormat("EEEE");
    SimpleDateFormat sdfin = new SimpleDateFormat("dd.MM.yyyy");
    Date d = new Date();
    String today = sdfout.format(d);
    Date dayOfTheWeek;

    private int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        logout_back = (Button) findViewById(R.id.button19);

        if(intent.getAction().equals("StudentTimetableDayActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("LoginFormActivity")){

            userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("QRScanActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("ParentMainActivity")){
            logout_back.setBackgroundResource(R.drawable.back_arrow);
            userId = intent.getExtras().getString("PUPIL_ID_MESSAGE");

        }



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




                                        if (lesson.date != null) {

                                            try {
                                                dayOfTheWeek = sdfin.parse(lesson.date);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }


                                            if (today.equals(sdfout.format(dayOfTheWeek))) {


                                                class_.setText(lesson.name);

                                                if (document.get(userId).equals("present")) {

                                                    class_.setBackground(getDrawable(R.drawable.group_76));
                                                }

                                                if (document.get(userId).equals("notpresent")) {

                                                    class_.setBackground(getDrawable(R.drawable.group_14));

                                                }
                                            }
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
        Intent intentStudentProfileActivity = new Intent(StudentProfile.this,StudentTimetableDay.class);
        intentStudentProfileActivity.setAction("StudentProfileActivity");
        Bundle extras = new Bundle();

        extras.putString("GROUP_ID_MESSAGE",groupId);
        extras.putString("USER_ID_MESSAGE",userId);

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);
    }

    public void QR(View view){
        Intent intentStudentProfileActivity = new Intent(StudentProfile.this,QRScan.class);
        intentStudentProfileActivity.setAction("StudentProfileActivity");
        Bundle extras = new Bundle();

        extras.putString("GROUP_ID_MESSAGE",groupId);
        extras.putString("USER_ID_MESSAGE",userId);

        intentStudentProfileActivity.putExtras(extras);
        startActivity(intentStudentProfileActivity);
    }

    public void Back(View view){
        //need Logout -> error with Docref
        Intent intent = getIntent();
        if(intent.getAction().equals("ParentMainActivity")){
            Intent intentParentMainActivity = new Intent(getApplicationContext(), ParentMainActivity.class);
            intentParentMainActivity.setAction("StudentProfile");
            intentParentMainActivity.putExtra("PARENT_ID_MESSAGE", intent.getExtras().getString("PARENT_ID_MESSAGE"));
            startActivity(intentParentMainActivity);
        }else {
            Intent intentBack = new Intent(StudentProfile.this, LoginFormActivity.class);
            startActivity(intentBack);
        }
    }

}
