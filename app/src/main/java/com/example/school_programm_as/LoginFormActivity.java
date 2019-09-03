package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



public class LoginFormActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.example.school_programm_AS.MESSAGE";


    private FirebaseAuth mAuth;
    private EditText ETEmail;
    private EditText ETPassword;

    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        Intent intent = getIntent();



        mAuth = FirebaseAuth.getInstance();

        String message = intent.getStringExtra(LoginAuthorization.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.image_authorization);

        if(message.equals("teacher")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + textName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + textName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            String textName = "authorization_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + textName, null, null);
            imageView.setImageResource(id);
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

                            Toast.makeText(LoginFormActivity.this, "Регистрация прошла успешно",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {


                            Toast.makeText(LoginFormActivity.this, "Регистрация провалена",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

            //Intent intentEnter = new Intent(this, StudentProfile.class);
            //startActivity(intentEnter);

        signin(ETEmail.getText().toString(), ETPassword.getText().toString());

        if(message.equals("student")){
            Intent intentPupilMainActivity = new Intent(this, StudentProfile.class);
            FirebaseUser user = mAuth.getCurrentUser();
            String message = user.getUid();
            Toast.makeText(LoginFormActivity.this, message, Toast.LENGTH_SHORT).show();
            intentPupilMainActivity.putExtra(EXTRA_MESSAGE, message);
            startActivity(intentPupilMainActivity);

        }else if(message.equals("teacher")) {
            Intent intentTeacherMainActivity = new Intent(this, TeacherMainActivity.class);
            FirebaseUser user = mAuth.getCurrentUser();
            String message = user.getUid();
            Toast.makeText(LoginFormActivity.this, message, Toast.LENGTH_SHORT).show();
            intentTeacherMainActivity.putExtra(EXTRA_MESSAGE, message);
            startActivity(intentTeacherMainActivity);

        }else if(message.equals("parent")){

            /*Intent intentParentMainActivity = new Intent(this, ParentMainActivity.class);
            FirebaseUser user = mAuth.getCurrentUser();
            String message = user.getUid();
            Toast.makeText(LoginFormActivity.this, message, Toast.LENGTH_SHORT).show();
            intentParentMainActivity.putExtra(EXTRA_MESSAGE, message);
            startActivity(intentParentMainActivity);
            */
        }
        else if(message.equals("administrator")){
            /*Intent intentAdministratorMainActivity = new Intent(this, AdministratorMainActivity.class);
            FirebaseUser user = mAuth.getCurrentUser();
            String message = user.getUid();
            Toast.makeText(LoginFormActivity.this, message, Toast.LENGTH_SHORT).show();
            intentAdministratorMainActivity.putExtra(EXTRA_MESSAGE, message);
            startActivity(intentAdministratorMainActivity);
            */
        }
    }

    public void Back(View view){
        Intent intentBack = new Intent(this, LoginAuthorization.class);
        startActivity(intentBack);
    }

}
