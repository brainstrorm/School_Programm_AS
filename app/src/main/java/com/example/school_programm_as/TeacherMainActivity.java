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

import java.util.HashMap;
import java.util.Map;

public class TeacherMainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseAuth mAuth;
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
                User user_ = documentSnapshot.toObject(Pupil.class);
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
        DocumentReference docRefGroup = mFirestore.collection("groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(RegistrationFormActivity.this, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(TeacherMainActivity.this, ":)", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                        Toast.makeText(TeacherMainActivity.this, ":(", Toast.LENGTH_SHORT).show();

                    }
                }).getResult();
        groupId = docRefGroup.getId();
        docRefGroup.update("group", groupId, "teacherName", surname + " " + name, "teacherId", teacherId);

        Intent intentCreateClass = new Intent(this, CreateClassActivity.class);
        String message = "TeacherMainActivity";
        intentCreateClass.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentCreateClass);
        mFirestore = FirebaseFirestore.getInstance();
    }

}
