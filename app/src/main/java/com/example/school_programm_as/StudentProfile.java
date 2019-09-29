package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentProfile extends AppCompatActivity {

    public final static String ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE_USER = "USER";


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String name, place, teacher, surname;
    private int bills, todayBills;
    private TextView Name, Bills, Place, Teacher, TodayBills;
    private LinearLayout mLinearLayout;
    private String userId,groupId;
    private List<Lesson> subjects = new ArrayList<>();

    private String userIdforStudentTimetableDay;
    private String groupIdforStudentTimetableDay;
    int cnt;

    private Button logout_back;
    private Button QRCode;

    SimpleDateFormat sdfin = new SimpleDateFormat("dd.MM.yyyy");
    Date d = new Date();
    String today = sdfin.format(d);

    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mFirestore = FirebaseFirestore.getInstance();

        final TextView Text;
        Text = findViewById(R.id.noLessons);


        logout_back = (Button) findViewById(R.id.button19);
        QRCode = (Button) findViewById(R.id.button18);

        if(intent.getAction().equals("StudentTimetableDayActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
            if(intent.getExtras().getString("PARENT_ID_MESSAGE")!= null){
                logout_back.setBackgroundResource(R.drawable.back_arrow);
                QRCode.setVisibility(View.INVISIBLE);
            }
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
            /*mFirestore.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            groupId = documentSnapshot.get("group").toString();
                        }
                    });*/
            QRCode.setVisibility(View.INVISIBLE);
        }



        Name = findViewById(R.id.studentName);
        Bills = findViewById(R.id.amountBills);
        Teacher = findViewById(R.id.studentTeacher);
        Place = findViewById(R.id.studentClass);
        TodayBills = findViewById(R.id.amountToday);




        DocumentReference docRef_users = mFirestore.collection("users").document(userId);
        docRef_users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pupil pupil_ = documentSnapshot.toObject(Pupil.class);
                name = pupil_.name;
                surname = pupil_.surname;
                bills = pupil_.bill;
                todayBills = pupil_.todayBill;

                Name.setText(surname + " " + name);
                Bills.setText(Integer.toString(bills));
                Place.setText(place);
                TodayBills.setText("Сегодня заработал: " + todayBills);


                groupId = pupil_.group;

                if((groupId != null) && (groupId != "")) {
                    DocumentReference docRef_groups = mFirestore.collection("groups").document(groupId);
                    docRef_groups.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Group group_ = documentSnapshot.toObject(Group.class);

                            place = "класс: " + group_.name;
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
                                    for (final QueryDocumentSnapshot document : task.getResult()) {
                                        final Lesson lesson = document.toObject(Lesson.class);



                                        if (lesson.date != null) {



                                            if (today.equals(lesson.date)) {


                                                if (document.get(userId) != null) {
                                                    switch (document.get(userId).toString()) {
                                                        case "present":
                                                            lesson.state = 0;
                                                            break;

                                                        case "notpresent":
                                                            lesson.state = 1;
                                                            break;

                                                        case "latecomer":
                                                            lesson.state = 2;
                                                            break;

                                                        default:
                                                            break;
                                                    }
                                                }

                                                subjects.add(lesson);

                                                cnt++;


                                            }
                                        }
                                        mLinearLayout = findViewById(R.id.timetable);
                                        id++;
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

                                    for (int i = 0; i < subjects.size(); i++) {
                                        final TextView class_ = new TextView(getApplicationContext());

                                        class_.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                )
                                        );
                                        if (subjects.size() != 0) {
                                            switch (subjects.get(i).state) {
                                                case 0:
                                                    class_.setBackground(getDrawable(R.drawable.group_76));
                                                    break;

                                                case 1:
                                                    class_.setBackground(getDrawable(R.drawable.group_14));
                                                    break;

                                                case 2:
                                                    class_.setBackground(getDrawable(R.drawable.student_profile_latecomer));

                                                    break;

                                                default:
                                                    class_.setBackground(getDrawable(R.drawable.group_14));

                                                    break;
                                            }


                                            class_.setTextSize(22);
                                            class_.setTextColor(0xFF8E7B89);
                                            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/helveticaneuemed.ttf");
                                            class_.setTypeface(font);
                                            class_.setText(subjects.get(i).name);
                                            mLinearLayout.addView(class_);
                                            id++;
                                        }
                                    }


                                    if (cnt == 0) {
                                                Text.setText("Занятий нет");
                                            }


                                                 Toast.makeText(StudentProfile.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT).show();
                                        } else {
                                        Toast.makeText(StudentProfile.this, "Информация о группах не получена", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Teacher.setText("Вы еще не добавлены в группу");
                }
                }

        });




    }

    public void Enter(View view){
        Intent intentStudentProfileActivity = new Intent(StudentProfile.this,StudentTimetableDay.class);
        intentStudentProfileActivity.setAction("StudentProfileActivity");
        Bundle extras = new Bundle();

        extras.putString("GROUP_ID_MESSAGE",groupId);
        extras.putString("USER_ID_MESSAGE",userId);
        if(getIntent().getAction().equals("ParentMainActivity")) {
            extras.putString("PARENT_ID_MESSAGE", getIntent().getExtras().getString("PARENT_ID_MESSAGE"));
        }
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

    public void Back(View view) {
        Intent intent = getIntent();
        if (intent.getAction().equals("ParentMainActivity") || intent.getExtras().getString("PARENT_ID_MESSAGE") != null) {
            Intent intentParentMainActivity = new Intent(getApplicationContext(), ParentMainActivity.class);
            intentParentMainActivity.setAction("StudentProfile");
            intentParentMainActivity.putExtra("PARENT_ID_MESSAGE", intent.getExtras().getString("PARENT_ID_MESSAGE"));
            startActivity(intentParentMainActivity);
        } else {
            Intent intentBack = new Intent(StudentProfile.this, LoginFormActivity.class);
            intentBack.setAction("logOut");
            startActivity(intentBack);
        }
    }

}
