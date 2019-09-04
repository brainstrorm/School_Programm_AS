package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class TeacherMainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseFirestore mFirestore;
    private String name;
    private String surname;
    private String teacherId;
    private String groupId;
    private TextView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);
        mFirestore = FirebaseFirestore.getInstance();

        Name = findViewById(R.id.textView);

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
            }
        });


    }

    public void createClass(View view){
        //Создание новой ячейки в Firestore в коллекции groups и заполнение ее полей
        Map<String, Object> group = new HashMap<>();
        group.put("group", "");
        group.put("name", "");
        group.put("teacherName", "");
        group.put("teacherId", "");
        mFirestore.collection("groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TeacherMainActivity.this, ":)", Toast.LENGTH_SHORT).show();
                        groupId = documentReference.getId();
                        documentReference.update("group", groupId, "teacherFullName", surname + " " + name, "teacherId", teacherId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherMainActivity.this, ":(", Toast.LENGTH_SHORT).show();

                    }
                });

        Intent intentCreateClass = new Intent(this, CreateClassActivity.class);
        String message = "TeacherMainActivity";
        intentCreateClass.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentCreateClass);
    }

    public void logOut(View view){
        Intent intentLogOut = new Intent(this, LoginFormActivity.class);
        String message = "logout";
        intentLogOut.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentLogOut);
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void logOut(View view){
        Intent intentLogOut = new Intent(this, LoginFormActivity.class);
        String message = "logout";
        intentLogOut.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentLogOut);
        mFirestore = FirebaseFirestore.getInstance();
    }

}
