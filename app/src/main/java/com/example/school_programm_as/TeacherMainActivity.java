package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
                                        final HorizontalScrollView scrollView = new HorizontalScrollView(getApplicationContext());
                                        scrollView.setId(id);
                                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                        linearLayout.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.MATCH_PARENT
                                                )
                                        );
                                        scrollView.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                )
                                        );
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

                                        class_.setTextSize(25);
                                        class_.setTextColor(0xFFFFFFFF);
                                        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/helveticaneuemed.ttf");
                                        class_.setTypeface(font);
                                        class_.setTypeface(null, Typeface.BOLD);
                                        class_.setAllCaps(false);

                                        class_.setText(group.name);

                                        class_.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intentTodayTimetableActivity = new Intent(TeacherMainActivity.this, CreateClassActivity.class);
                                                intentTodayTimetableActivity.setAction("ExistingGroup");
                                                Bundle extras = new Bundle();
                                                String message = document.getId();
                                                extras.putString("GROUP_ID_MESSAGE", message);
                                                Toast.makeText(TeacherMainActivity.this, intentTodayTimetableActivity.getStringExtra(TeacherMainActivity.GROUP_ID_MESSAGE), Toast.LENGTH_SHORT).show();
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
                                                extras.putString("USER_ID_MESSAGE", userId);
                                                intentTodayTimetableActivity.putExtras(extras);
                                                startActivity(intentTodayTimetableActivity);
                                            }
                                        });
                                        Button btn_delete = new Button(getApplicationContext());
                                        btn_delete.setBackgroundResource(R.drawable.delete_group);
                                        btn_delete.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                                        LinearLayout.LayoutParams.MATCH_PARENT
                                                )
                                        );
                                        btn_delete.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                mFirestore.collection("lessons").whereEqualTo("group", document.getId())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                              if(task.isSuccessful()){
                                                                  for(final QueryDocumentSnapshot document: task.getResult()){
                                                                      document.getReference().delete();
                                                                  }
                                                              }else{
                                                                  Toast.makeText(getApplicationContext(), "Удаление уроков не  выполнено", Toast.LENGTH_SHORT).show();
                                                              }
                                                            }
                                                        });
                                                mFirestore.collection("users").whereEqualTo("group", document.getId()).whereEqualTo("role", "pupil")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    for(final QueryDocumentSnapshot document: task.getResult()){
                                                                        document.getReference().update("group", "");
                                                                    }
                                                                }else{
                                                                    Toast.makeText(getApplicationContext(), "Обновление поля group учеников не выполнено", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                mFirestore.collection("groups").document(document.getId()).delete();
                                                mLinearLayout.removeView(scrollView);
                                            }
                                        });
                                        Button btn_replace = new Button(getApplicationContext());
                                        btn_replace.setBackgroundResource(R.drawable.replace_group);
                                        btn_replace.setLayoutParams(
                                                new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                                        LinearLayout.LayoutParams.MATCH_PARENT
                                                )
                                        );
                                        btn_replace.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                /*if(id > 1) {
                                                    HorizontalScrollView class_1 = (HorizontalScrollView) findViewById(id - 1);
                                                    HorizontalScrollView class_2 = (HorizontalScrollView) findViewById(id);
                                                    class_1.setVisibility(View.GONE);
                                                    class_2.setVisibility(View.GONE);
                                                    class_2.setVisibility(View.VISIBLE);
                                                    class_1.setVisibility(View.VISIBLE);
                                                }*/
                                            }
                                        });
                                        linearLayout.addView(class_);
                                        linearLayout.addView(btn_delete);
                                        linearLayout.addView(btn_replace);
                                        scrollView.addView(linearLayout);
                                        mLinearLayout.addView(scrollView);
                                        id++;
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
                            userId = intent.getStringExtra("USER_ID_MESSAGE");
                        }
                        if(intent.getAction().equals("LoginFormActivity")){
                            userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
                        }
                        if(intent.getAction().equals("TodayTimetableActivity")){
                            userId = intent.getStringExtra("USER_ID_MESSAGE");
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

    public void logOut(View view){
        Intent logOut  = new Intent(getApplicationContext(), LoginFormActivity.class);
        logOut.setAction("logOut");
        startActivity(logOut);
    }


}
