package com.example.school_programm_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MyQR extends AppCompatActivity {

    String userId,groupId;

    private ImageView IVQRCode;
    private FirebaseFirestore mFirestore;
    private String TAG = "GenerateQRCode";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
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
                    userId, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                IVQRCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        }else {
            Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
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
        Intent intentMyQRActivity = new Intent(getApplicationContext(), QRScan.class);
        intentMyQRActivity.setAction("MyQRActivity");
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        intentMyQRActivity.putExtras(extras);
        startActivity(intentMyQRActivity);
    }
}
