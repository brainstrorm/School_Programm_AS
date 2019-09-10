package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyQR extends AppCompatActivity {

    String userId,groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("QRScanActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }

    }

    public void Back(View view){
        Intent intentMyQRActivity = new Intent(getApplicationContext(), QRScan.class);
        intentMyQRActivity.setAction("MyQRActivity");

        intentMyQRActivity.putExtra("USER_ID_MESSAGE", userId);
        intentMyQRActivity.putExtra("GROUP_ID_MESSAGE", groupId);

        startActivity(intentMyQRActivity);
    }
}
