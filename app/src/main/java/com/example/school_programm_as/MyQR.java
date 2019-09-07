package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr);
    }

    public void Back(View view){
        Intent intentBack = new Intent(this, QRScan.class);
        startActivity(intentBack);
    }
}
