package com.example.school_programm_as;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginFormActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText ETEmail;
    private EditText ETPassword;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

                            Toast.makeText(LoginFormActivity.this, "Аутентификация прошла успешно",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            mFirestore.collection("users")
                                 .whereEqualTo("userId", userId);
                                 /*.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                     @Override
                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                         if (task.isSuccessful()) {
                                             for (QueryDocumentSnapshot document : task.getResult()) {
                                                 Toast.makeText(LoginFormActivity.this, "Аутентификация прошла успешно",
                                                         Toast.LENGTH_SHORT).show();
                                                 //Log.d(TAG, document.getId() + " => " + document.getData());
                                             }
                                         } else {
                                             Toast.makeText(LoginFormActivity.this, "Аутентификация провалена",
                                                     Toast.LENGTH_SHORT).show();
                                             //Log.w(TAG, "Error getting documents.", task.getException());
                                         }
                                     }
                                 });*/
                        } else {


                            Toast.makeText(LoginFormActivity.this, "Аутентификация провалена",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

            signin(ETEmail.getText().toString(), ETPassword.getText().toString());
    }

}
