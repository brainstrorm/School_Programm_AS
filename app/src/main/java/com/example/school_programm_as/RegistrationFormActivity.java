package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegistrationFormActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private EditText ETEmail;
    private EditText ETPassword;
    private EditText ETName;
    private EditText ETSurname;
    private EditText ETPathronimic;
    private String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.imageView3);
        ETEmail = (EditText)findViewById(R.id.editText4);
        ETPassword = (EditText)findViewById(R.id.editText5);
        ETName = (EditText)findViewById(R.id.editText);
        ETSurname = (EditText)findViewById(R.id.editText2);
        ETPathronimic = (EditText)findViewById(R.id.editText3);
        if(message.equals("student")) {
            //user = new Pupil(ETName.getText().toString().trim(), ETSurname.getText().toString().trim(), ETPathronimic.getText().toString().trim(),
                    //"pupil", ETEmail.getText().toString().trim(), ETPassword.getText().toString().trim(), "", 123, "");
           role = "pupil";
        }
        if(message.equals("teacher")){
            //user = new User(ETName.getText().toString().trim(), ETSurname.getText().toString().trim(), ETPathronimic.getText().toString().trim(),
                    //"teacher", ETEmail.getText().toString().trim(), ETPassword.getText().toString().trim(), "");
            role = message;
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            //user = new User(ETName.getText().toString().trim(), ETSurname.getText().toString().trim(), ETPathronimic.getText().toString().trim(),
                    //"parents", ETEmail.getText().toString().trim(), ETPassword.getText().toString().trim(), "");
            role = message+"s";
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            //user = new User(ETName.getText().toString().trim(), ETSurname.getText().toString().trim(), ETPathronimic.getText().toString().trim(),
                    //"administrator", ETEmail.getText().toString().trim(), ETPassword.getText().toString().trim(), "");
            role = message;
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){

        }
    }

    public void createAccount(){
        final String Name = ETName.getText().toString().trim();
        final String Surname = ETSurname.getText().toString().trim();
        final String Pathronimic = ETPathronimic.getText().toString().trim();
        final String Email = ETEmail.getText().toString().trim();
        final String Password = ETPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Map<String, Object> user = new HashMap<>();
                            //User user;
                            //Pupil pupil;
                            if(role.equals("pupil")) {
                                user.put("name", Name);
                                user.put("surname", Surname);
                                user.put("pathronimic", Pathronimic);
                                user.put("role", role);
                                user.put("email", Email);
                                user.put("password", Password);
                                user.put("bill", 0);
                                user.put("group", "");
                                mFirestore.collection("users")
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                //Log.d(RegistrationFormActivity.this, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                                /*user = new User(Name, Surname, Pathronimic, role, Email, Password, mAuth.getInstance().getCurrentUser().getUid());
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrationFormActivity.this, "Регистрация ученика прошла успешно",
                                                    Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RegistrationFormActivity.this, "Говно",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });*/
                            }else{
                                /*pupil = new Pupil(Name, Surname, Pathronimic, role, Email, Password, mAuth.getInstance().getCurrentUser().getUid(), 123,"dfsdf");
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(pupil).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrationFormActivity.this, "Регистрация пользователя прошла успешно",
                                                    Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RegistrationFormActivity.this, "Говно",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });*/
                            }
                        } else {


                            Toast.makeText(RegistrationFormActivity.this, "Регистрация провалена",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
    public void Back(View view){
        Intent intentBack = new Intent(this, RegisterActivity.class);
        startActivity(intentBack);
    }

    public void signUp(View view) {
            createAccount();

    }
}
