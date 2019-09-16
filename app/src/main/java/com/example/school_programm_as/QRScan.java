package com.example.school_programm_as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QRScan extends AppCompatActivity {



    private TextView txt_result;
    private SurfaceView surfaceView;
    private QREader qrEader;
    private String groupId,userId;

    private static final int REQUEST_CAMERA_PERMISSIONS = 101;

    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if(intent.getAction().equals("StudentProfileActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);


        mFirestore = FirebaseFirestore.getInstance();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission doesn't granted
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSIONS);
        } else {
            //Permissions've been granted already
            onCreateDexter();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSIONS) {
            if (permissions.length > 0 && grantResults[0] != PackageManager.PERMISSION_DENIED) {
                onCreateDexter();
            }
        }
    }

    private void onCreateDexter() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(QRScan.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void setupCamera() {
        txt_result = (TextView) findViewById(R.id.textView5);
        final ToggleButton btn_on_off = (ToggleButton) findViewById(R.id.btn_enable_disable);
        btn_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrEader.isCameraRunning()){
                    btn_on_off.setChecked(false);
                    qrEader.stop();
                }else{
                    btn_on_off.setChecked(true);
                    qrEader.start();
                }
            }
        });
        surfaceView = (SurfaceView) findViewById(R.id.camera_view);
        setupQREader();
    }

    private void setupQREader() {
        qrEader = new QREader.Builder(this, surfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data){
                txt_result.post(new Runnable() {
                    @Override
                    public void run() {
                        txt_result.setText(data);
                        groupId = data;
                        if((groupId == "") || (groupId == null)) {
                            mFirestore.collection("lessons").whereEqualTo("group", groupId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            Intent intent = getIntent();
                                            final String userId = intent.getExtras().getString("USER_ID_MESSAGE");
                                            mFirestore.collection("users").document(userId)
                                                    .update("group", groupId);
                                            for (final QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                DocumentReference docRefLesson = mFirestore.collection("lessons").document(document.getId());
                                                if (docRefLesson != null) {
                                                    docRefLesson.update(userId, "notpresent");
                                                    qrEader.stop();
                                                } else {
                                                    Toast.makeText(QRScan.this, "null", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(getApplicationContext(), "Вы уже находитесь в группе", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getHeight())
                .width(surfaceView.getWidth())
                .build();
    }

    public void Back(View view){
        Intent intentQRScanActivity = new Intent(getApplicationContext(), StudentProfile.class);
        intentQRScanActivity.setAction("QRScanActivity");
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        intentQRScanActivity.putExtras(extras);
        startActivity(intentQRScanActivity);
    }

    public void Enter(View view) {
        Intent intentQRScanActivity = new Intent(getApplicationContext(), MyQR.class);
        intentQRScanActivity.setAction("QRScanActivity");
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        intentQRScanActivity.putExtras(extras);
        startActivity(intentQRScanActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if(qrEader != null){
                            qrEader.initAndStart(surfaceView);
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(QRScan.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if(qrEader != null){
                            qrEader.releaseAndCleanup();
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(QRScan.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }
}
