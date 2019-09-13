package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.zxing.WriterException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MyQRCodeActivity extends AppCompatActivity {

    public final static String ID_MESSAGE = "ID";
    public final static String USER_ID_MESSAGE = "ID";
    private ImageView IVQRCode;
    private FirebaseFirestore mFirestore;
    private String TAG = "GenerateQRCode";
    private String groupId;
    private String userId;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        groupId = extras.getString("GROUP_ID_MESSAGE");
        IVQRCode = (ImageView) findViewById(R.id.imageView11);

        if(groupId.length() > 0){
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    groupId, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                IVQRCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        }else {
            Toast.makeText(MyQRCodeActivity.this, "Required", Toast.LENGTH_SHORT).show();
        }

        /*boolean save;
        String result;
        try {
            save = QRGSaver.save(savePath, groupId, bitmap, QRGContents.ImageType.IMAGE_JPEG);
            result = save ? "Image Saved" : "Image Not Saved";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void back(View view){
        Intent intentTodayTimetableActivity = new Intent(this, TodayTimetableActivity.class);
        intentTodayTimetableActivity.setAction("MyQRCodeActivity");
        Bundle extras = new Bundle();
        Intent intent = getIntent();
        Bundle extras_ = intent.getExtras();
        extras.putString("USER_ID_MESSAGE", extras_.getString("USER_ID_MESSAGE"));
        extras.putString("ID_MESSAGE", extras_.getString("GROUP_ID_MESSAGE"));
        intentTodayTimetableActivity.putExtras(extras);
        startActivity(intentTodayTimetableActivity);
    }

}
