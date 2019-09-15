package com.example.school_programm_as;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class ParentQRScanActivity extends AppCompatActivity {

    private TextView txt_result;
    private SurfaceView surfaceView;
    private QREader qrEader;
    private String pupilId;

    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_qrscan);

        Intent intent = getIntent();

        mFirestore = FirebaseFirestore.getInstance();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ParentQRScanActivity.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


    }

    private void setupCamera() {
        /*txt_result = (TextView) findViewById(R.id.textView5);
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
        });*/
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
                        pupilId = data;
                        Intent intent = getIntent();
                        final String parentId = intent.getStringExtra("PARENT_ID_MESSAGE");
                        Toast.makeText(getApplicationContext(), parentId, Toast.LENGTH_SHORT).show();
                        mFirestore.collection("users").document(pupilId)
                                .update("parentId", parentId);
                        qrEader.stop();
                        Intent intentParentMainActivity = new Intent(getApplicationContext(), ParentMainActivity.class);
                        intentParentMainActivity.setAction("ParentQRScanActivity");
                        intentParentMainActivity.putExtra("PARENT_ID_MESSAGE", intent.getStringExtra("PARENT_ID_MESSAGE"));
                        startActivity(intentParentMainActivity);
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
        Intent intent = getIntent();
        Intent intentParentMainActivity = new Intent(getApplicationContext(), ParentMainActivity.class);
        intentParentMainActivity.setAction("ParentQRScanActivity");
        intentParentMainActivity.putExtra("PARENT_ID_MESSAGE", intent.getExtras().getString("PARENT_ID_MESSAGE"));
        startActivity(intentParentMainActivity);
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
                        Toast.makeText(ParentQRScanActivity.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ParentQRScanActivity.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

}
