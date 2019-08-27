package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationFormActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private EditText ETEmail;
    private EditText ETPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        ImageView imageView =  findViewById(R.id.imageView13);

        if(message.equals("teacher")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

        if(message.equals("parent")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }
        if(message.equals("administrator")){
            String imageName = "registration_" + message;
            int id = getResources().getIdentifier("com.example.school_programm_as:drawable/" + imageName, null, null);
            imageView.setImageResource(id);
        }

        ETEmail = (EditText)findViewById(R.id.editText4);
        ETPassword = (EditText)findViewById(R.id.editText5);

    }


    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrationFormActivity.this, "Регистрация прошла успешно",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
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
            createAccount(ETEmail.getText().toString(), ETPassword.getText().toString());
    }
}
