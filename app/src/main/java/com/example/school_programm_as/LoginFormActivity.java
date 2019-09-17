package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.school_programm_as.admin.presentation.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginFormActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private FirebaseAuth mAuth;
    private EditText ETEmail;
    private EditText ETPassword;
    private FirebaseFirestore mFirestore;
    private String message;
    private String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        Intent intent = getIntent();



        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        message = intent.getStringExtra(LoginAuthorization.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.image_authorization);

        if(intent.getAction().equals("logOut")){
            mAuth.signOut();
            Intent intentLoginAothorization = new Intent(getApplicationContext(), LoginAuthorization.class);
            startActivity(intentLoginAothorization);
        }else {

            String message = intent.getStringExtra(LoginAuthorization.EXTRA_MESSAGE);

            if (message.equals("teacher")) {
                String textName = "authorization_" + message;
                int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
                imageView.setImageResource(id);
            }

            if (message.equals("parent")) {
                String textName = "authorization_" + message;
                int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
                imageView.setImageResource(id);
            }
            if (message.equals("administrator")) {
                String textName = "authorization_" + message;
                int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + textName, null, null);
                imageView.setImageResource(id);
            }
        }


        ETEmail = (EditText) findViewById(R.id.btn_sign_in_email);
        ETPassword = (EditText) findViewById(R.id.btn_sign_in_password);

        findViewById(R.id.btn_enter).setOnClickListener(this);

    }

    public void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginFormActivity.this, "Авторизация прошла успешно",
                                    Toast.LENGTH_SHORT).show();
                            if(message.equals("student")){
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userUid = user.getUid();
                                userUid = userUid.replaceAll("\\s", "");
                                mFirestore.collection("users").document(userUid)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user1 = documentSnapshot.toObject(User.class);
                                        role = user1.role;
                                        if(role.equals("pupil")) {
                                            Intent intentPupilMainActivity = new Intent(LoginFormActivity.this, StudentProfile.class);
                                            intentPupilMainActivity.setAction("LoginFormActivity");
                                            Toast.makeText(LoginFormActivity.this, user1.userId, Toast.LENGTH_SHORT).show();

                                            intentPupilMainActivity.putExtra(EXTRA_MESSAGE, user1.userId);


                                            startActivity(intentPupilMainActivity);
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Вы не ученик", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else if(message.equals("teacher")) {
                                Intent intentTeacherMainActivity = new Intent(LoginFormActivity.this, TeacherMainActivity.class);
                                intentTeacherMainActivity.setAction("LoginFormActivity");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userUid = user.getUid();
                                userUid = userUid.replaceAll("\\s","");
                                mFirestore.collection("users").document(userUid)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user1 = documentSnapshot.toObject(User.class);
                                        role = user1.role;
                                        if(role.equals("teacher")) {
                                            Intent intentTeacherMainActivity = new Intent(LoginFormActivity.this, TeacherMainActivity.class);
                                            intentTeacherMainActivity.setAction("LoginFormActivity");
                                            Toast.makeText(LoginFormActivity.this, user1.userId, Toast.LENGTH_SHORT).show();

                                            intentTeacherMainActivity.putExtra(EXTRA_MESSAGE, user1.userId);

                                            startActivity(intentTeacherMainActivity);
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Вы не учитель", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else if(message.equals("parent")){

                                Intent intentParentMainActivity = new Intent(LoginFormActivity.this, ParentMainActivity.class);
                                intentParentMainActivity.setAction("LoginFormActivity");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userUid = user.getUid();
                                Toast.makeText(LoginFormActivity.this, userUid, Toast.LENGTH_SHORT).show();
                                intentParentMainActivity.putExtra(EXTRA_MESSAGE, userUid);
                                startActivity(intentParentMainActivity);
                            }
                            else if(message.equals("administrator")){
                                Intent intentAdministratorMainActivity = new Intent(LoginFormActivity.this, AdminActivity.class);
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userUid = user.getUid();
                                Toast.makeText(LoginFormActivity.this, userUid, Toast.LENGTH_SHORT).show();
                                intentAdministratorMainActivity.putExtra("USER_ID_MESSAGE", userUid);
                                startActivity(intentAdministratorMainActivity);
                            }
                        } else {


                            Toast.makeText(LoginFormActivity.this, "Авторизация провалена. Введен неверный логин или пароль.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        signin(ETEmail.getText().toString(), ETPassword.getText().toString());

    }

    public void Back(View view){
        Intent intentBack = new Intent(this, LoginAuthorization.class);
        startActivity(intentBack);
    }

}
