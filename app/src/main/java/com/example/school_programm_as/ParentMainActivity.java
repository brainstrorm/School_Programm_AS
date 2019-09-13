package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ParentMainActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private TextView TVstudentName;
    private LinearLayout children;
    private int btnId = 1;
    public final static String ID_MESSAGE = "ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(LoginFormActivity.EXTRA_MESSAGE);

        TVstudentName = (TextView) findViewById(R.id.studentName);
        children = (LinearLayout) findViewById(R.id.timetable);

        mFirestore = FirebaseFirestore.getInstance();
        final DocumentReference docRefParent = mFirestore.collection("users").document(userId);
        docRefParent.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User parent = documentSnapshot.toObject(User.class);
                TVstudentName.setText(parent.name + " "+parent.surname);

            }
        });
        mFirestore.collection("users").whereEqualTo("parentId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (final QueryDocumentSnapshot document : task.getResult()){
                                final Pupil pupil = document.toObject(Pupil.class);
                                final Button btn_pupil = new Button(getApplicationContext());
                                btn_pupil.setId(btnId);
                                btn_pupil.setBackgroundResource(R.drawable.class_field);
                                btn_pupil.setLayoutParams(
                                        new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        )
                                );
                                btn_pupil.setText(pupil.name + " " + pupil.surname);
                                btn_pupil.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intentStudentProfileActivity = new Intent(ParentMainActivity.this, StudentProfile.class);
                                        intentStudentProfileActivity.setAction("ParentMainActivity");
                                        String message = pupil.userId;
                                        intentStudentProfileActivity.putExtra(ID_MESSAGE, message);
                                        startActivity(intentStudentProfileActivity);
                                    }
                                });
                                children.addView(btn_pupil);
                                Toast.makeText(ParentMainActivity.this, "Данные о детях успешно загружены", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ParentMainActivity.this, "Вы еще не добавили детей", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}
