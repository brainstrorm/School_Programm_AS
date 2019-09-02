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
    private String name,place,teacher;
    private int bills;
    private TextView Name,Bills,Place,Teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);


        Intent intent = getIntent();
        String userId = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);

        Name = findViewById(R.id.studentName);
        Bills = findViewById(R.id.amountBills);
        Teacher = findViewById(R.id.studentTeacher);
        Place = findViewById(R.id.studentClass);


        //FirebaseUser user = mAuth.getCurrentUser();
        //String userId = user.getUid();

        mFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = mFirestore.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user_ = documentSnapshot.toObject(Pupil.class);
                name = user_.name;
                bills = ((Pupil) user_).bill;
                place = ((Pupil) user_).group;

                Name.setText(name);
                Bills.setText(bills);
                Place.setText(place);
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
}
