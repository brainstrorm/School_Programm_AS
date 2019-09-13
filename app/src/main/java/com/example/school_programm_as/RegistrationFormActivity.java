package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText ETRepeatPassword;
    private String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.imageViewRegisterAs);
        ETEmail = (EditText)findViewById(R.id.editText4);
        ETPassword = (EditText)findViewById(R.id.editText5);
        ETName = (EditText)findViewById(R.id.editText);
        ETSurname = (EditText)findViewById(R.id.editText2);
        ETPathronimic = (EditText)findViewById(R.id.editText3);
        ETRepeatPassword = (EditText) findViewById(R.id.editText6);
        if(message.equals("student")) {
           role = "pupil";
        }
        if(message.equals("teacher")){
            role = message;
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            role = message+"s";
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            role = "administration";
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
        final String RepeatPassword = ETRepeatPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Map<String, Object> user = new HashMap<>();
                            if(role.equals("pupil")) {
                                user.put("name", Name);
                                user.put("surname", Surname);
                                user.put("pathronimic", Pathronimic);
                                user.put("role", role);
                                user.put("email", Email);
                                user.put("password", Password);
                                user.put("bill", 0);
                                user.put("todayBill", 0);
                                user.put("group", "");
                                user.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                user.put("parentId", "");
                                if(RepeatPassword.equals(Password)) {
                                    mFirestore.collection("users")
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RegistrationFormActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //Log.w(TAG, "Error adding document", e);
                                                    Toast.makeText(RegistrationFormActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            }else if(role.equals("teacher")){
                                user.put("name", Name);
                                user.put("surname", Surname);
                                user.put("pathronimic", Pathronimic);
                                user.put("role", role);
                                user.put("email", Email);
                                user.put("password", Password);
                                user.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                mFirestore.collection("users")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }else if(role.equals("parents")){
                                user.put("name", Name);
                                user.put("surname", Surname);
                                user.put("pathronimic", Pathronimic);
                                user.put("role", role);
                                user.put("email", Email);
                                user.put("password", Password);
                                user.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                mFirestore.collection("users")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }else if(role.equals("administration")){
                                user.put("name", Name);
                                user.put("surname", Surname);
                                user.put("pathronimic", Pathronimic);
                                user.put("role", role);
                                user.put("email", Email);
                                user.put("password", Password);
                                user.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                mFirestore.collection("users")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(RegistrationFormActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();

                                            }
                                        });
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
