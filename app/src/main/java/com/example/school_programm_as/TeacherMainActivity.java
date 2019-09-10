package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.school_programm_as.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherMainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String GROUP_ID_MESSAGE = "id";
    public final static String USER_ID_MESSAGE = "id";
    private FirebaseFirestore mFirestore;
    private String name;
    private String surname;
    private String teacherId;
    private String groupId;
    private TextView Name;
    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private ArrayList<Group> mGroups = new ArrayList<>();
    private int id = 1;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        Intent intent = getIntent();
        if(intent.getAction().equals("CreateClassActivity")){
            userId = intent.getStringExtra("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("LoginFormActivity")){
            userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
        }
        if(intent.getAction().equals("TodayTimetableActivity")){
            userId = intent.getStringExtra("USER_ID_MESSAGE");
        }

        mFirestore = FirebaseFirestore.getInstance();

        Name = findViewById(R.id.textView);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        mFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = mFirestore.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user_ = documentSnapshot.toObject(User.class);
                name = user_.name;
                surname = user_.surname;
                teacherId = user_.userId;
                Name.setText(surname+" "+name);
                Toast.makeText(TeacherMainActivity.this, teacherId, Toast.LENGTH_SHORT ).show();
                mFirestore.collection("groups").whereEqualTo("teacherId", teacherId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (final QueryDocumentSnapshot document : task.getResult()){
                                        final Group group = document.toObject(Group.class);
                                        final Button class_ = new Button(getApplicationContext());
                                        class_.setId(id);
                                        class_.setBackgroundResource(R.drawable.class_field);
                                        class_.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                )
                                        );
                                        class_.setText(group.name);
                                        class_.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intentTodayTimetableActivity = new Intent(TeacherMainActivity.this, TodayTimetableActivity.class);
                                                intentTodayTimetableActivity.setAction("TeacherMainActivity");
                                                Bundle extras = new Bundle();
                                                String message = document.getId();
                                                extras.putString("GROUP_ID_MESSAGE", message);
                                                Toast.makeText(TeacherMainActivity.this, intentTodayTimetableActivity.getStringExtra(TeacherMainActivity.GROUP_ID_MESSAGE), Toast.LENGTH_SHORT).show();
                                                Intent intent = getIntent();
                                                if(intent.getAction().equals("CreateClassActivity")){
                                                    userId = intent.getStringExtra(CreateClassActivity.EXTRA_MESSAGE);
                                                }
                                                if(intent.getAction().equals("LoginFormActivity")){
                                                    userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
                                                }
                                                extras.putString("USER_ID_MESSAGE", userId);
                                                intentTodayTimetableActivity.putExtras(extras);
                                                startActivity(intentTodayTimetableActivity);
                                            }
                                        });
                                        mLinearLayout.addView(class_);
                                        Toast.makeText(TeacherMainActivity.this, "Информация о группах успешно получена", Toast.LENGTH_SHORT ).show();
                                    }
                                }else{
                                    Toast.makeText(TeacherMainActivity.this, "Информация о группах не получена", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        });
            }
        });
    }

    public void createClass(View view){
        //Создание новой ячейки в Firestore в коллекции groups и заполнение ее полей
        Map<String, Object> group = new HashMap<>();
        group.put("name", "");
        group.put("teacherFullName", "");
        group.put("teacherId", "");
        mFirestore.collection("groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TeacherMainActivity.this, ":)", Toast.LENGTH_SHORT).show();
                        groupId = documentReference.getId();
                        documentReference.update("teacherFullName", surname + " " + name, "teacherId", teacherId);
                        Intent intentCreateClass = new Intent(TeacherMainActivity.this, CreateClassActivity.class);
                        intentCreateClass.setAction("TeacherMainActivity");
                        Bundle extras = new Bundle();
                        extras.putString("GROUP_ID_MESSAGE", groupId);
                        Intent intent = getIntent();
                        if(intent.getAction().equals("CreateClassActivity")){
                            userId = intent.getStringExtra(CreateClassActivity.EXTRA_MESSAGE);
                        }
                        if(intent.getAction().equals("LoginFormActivity")){
                            userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
                        }
                        extras.putString("USER_ID_MESSAGE", userId);
                        intentCreateClass.putExtras(extras);
                        Toast.makeText(TeacherMainActivity.this, groupId, Toast.LENGTH_SHORT).show();
                        startActivity(intentCreateClass);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherMainActivity.this, ":(", Toast.LENGTH_SHORT).show();

                    }
                });

    }


}
