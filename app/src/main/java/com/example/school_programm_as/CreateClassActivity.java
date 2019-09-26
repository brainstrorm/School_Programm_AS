package com.example.school_programm_as;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.L;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school_programm_as.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CreateClassActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String ID_MESSAGE = "ID";
    public final static String EXTRA_MESSAGE = "EXTRA";
    private final String TAG = "CreateClassActivity";
    private FirebaseFirestore mFirestore;
    private Button button_back;
    private String userId;
    private int countId = 1;
    private Set<String> dates;

    private LinearLayout mlinearLayout;
    private TextView TVGroup;
    private EditText nameField;

    private String groupName;
    private String groupId;
    private ImageButton QR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        mlinearLayout = (LinearLayout) findViewById(R.id.datesList);
        TVGroup = (TextView) findViewById(R.id.xGroup);
        nameField = (EditText) findViewById(R.id.editText7);
        QR = (ImageButton) findViewById(R.id.imageButton6);
        dates = new TreeSet<>();
        mFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        groupId = intent.getExtras().getString("GROUP_ID_MESSAGE");
        if(intent.getAction().equals("MyQRCodeActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("ExistingGroup")){
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
            ///QR.setVisibility(View.GONE);

        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
            //EditText class_name = (EditText) findViewById(R.id.editText7);
            //nameField.setHint(intent.getExtras().getString("GROUP_NAME_MESSAGE"));
        }
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        ConstraintLayout toolbar = findViewById(R.id.toolbar);

        mFirestore.collection("lessons").whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                dates.add(document.get("date").toString());
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Не найдены дни", Toast.LENGTH_SHORT).show();
                        }
                        List<String> newDates = new ArrayList<>(dates);
                        Collections.sort(newDates, new Comparator<String>() {
                            @Override
                            public int compare(String s, String t1) {
                                String[] firstDay = s.split("\\.");
                                String[] secondDay = t1.split("\\.");
                                if (firstDay[2].compareTo(secondDay[2]) == 0) {
                                    if (firstDay[1].compareTo(secondDay[1]) == 0) {
                                        return firstDay[0].compareTo(secondDay[0]);
                                    }
                                    return firstDay[1].compareTo(secondDay[1]);
                                }
                                return firstDay[2].compareTo(secondDay[2]);
                            }
                        });
                        String groupId = getIntent().getExtras().getString("GROUP_ID_MESSAGE");
                        for (String date: newDates) {
                            plusDate(date, groupId);
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        nameField = (EditText) findViewById(R.id.editText7);
        TVGroup = (TextView) findViewById(R.id.xGroup);
        TVGroup.setBackgroundColor(0000);
        TVGroup.setTextSize(22);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/helveticaneuemed.ttf");
        TVGroup.setTextColor(0xFFFFFFFF);
        TVGroup.setTypeface(font);
        TVGroup.setTypeface(null, Typeface.BOLD);
        groupId = getIntent().getExtras().getString("GROUP_ID_MESSAGE");
        if(getIntent().getAction().equals("ExistingGroup") || getIntent().getAction().equals("MyQRCodeActivity")) {
            FirebaseFirestore.getInstance().collection("groups").document(groupId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            groupName = documentSnapshot.get("name").toString();
                            nameField.setText(groupName);
                            TVGroup.setText(groupName);
                        }
                    });
        }else if(getIntent().getAction().equals("CreateTimetableActivity") && !getIntent().getExtras().getString("GROUP_NAME_MESSAGE").equals("")){
            groupName = getIntent().getExtras().getString("GROUP_NAME_MESSAGE");
            nameField.setText(groupName);
            TVGroup.setText(groupName);//кста шрифт
        }else{
            TVGroup.setBackgroundResource(R.drawable.new_group);
        }
        super.onResume();
    }

    public void back(View view){
        Intent intentBack = new Intent(this, TeacherMainActivity.class);
        intentBack.setAction("CreateClassActivity");
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("ExistingGroup")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");

        }
        if(intent.getAction().equals("MyQRCodeActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        intentBack.putExtra("USER_ID_MESSAGE", userId);
        EditText class_name = (EditText) findViewById(R.id.editText7);
        if(class_name.getText().toString().trim().equals("")) {
            mFirestore.collection("lessons").whereEqualTo("group", groupId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(final QueryDocumentSnapshot document: task.getResult()){
                                    document.getReference().delete();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Удаление уроков не  выполнено", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            mFirestore.collection("users").whereEqualTo("group", groupId).whereEqualTo("role", "pupil")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(final QueryDocumentSnapshot document: task.getResult()){
                                    document.getReference().update("group", "");
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Обновление поля group учеников не выполнено", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
            docRefGroup.delete();
        }
        startActivity(intentBack);
    }

    public void plusDay_(View view){
        Intent plusDay = new Intent(this, CreateTimetableActivity.class);
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("ExistingGroup")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        Bundle extras_ = new Bundle();
        extras_.putString("GROUP_ID_MESSAGE", groupId);
        extras_.putString("USER_ID_MESSAGE", userId);
        EditText class_name = (EditText) findViewById(R.id.editText7);
        extras_.putString("GROUP_NAME_MESSAGE", class_name.getText().toString());
        extras_.putString("DATE_MESSAGE", "");
        plusDay.putExtras(extras_);
        //Toast.makeText(this, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE), Toast.LENGTH_SHORT).show();
        startActivity(plusDay);
    }

    public void plusDate(final String date, String groupId){
        LinearLayout mlinearLayout = (LinearLayout) findViewById(R.id.datesList);
        final Button date_ = new Button(getApplicationContext());
        date_.setBackgroundResource(R.drawable.class_field);
        date_.setPadding(50, 20, 0, 0);
        date_.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        date_.setTextSize(25);
        date_.setTextColor(0xFFFFFFFF);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/helveticaneuemed.ttf");
        date_.setTypeface(font);
        date_.setTypeface(null, Typeface.BOLD);
        date_.setAllCaps(false);

        date_.setText(date);
        date_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDate = new Intent(getApplicationContext(), CreateTimetableActivity.class);
                Intent intent = getIntent();
                String groupId = "";
                Bundle extras = intent.getExtras();
                if(intent.getAction().equals("TeacherMainActivity")) {
                    userId = extras.getString("USER_ID_MESSAGE");
                    groupId = extras.getString("GROUP_ID_MESSAGE");
                }
                if(intent.getAction().equals("CreateTimetableActivity")){
                    userId = extras.getString("USER_ID_MESSAGE");
                    groupId = extras.getString("GROUP_ID_MESSAGE");
                }
                if(intent.getAction().equals("ExistingGroup")){
                    userId = extras.getString("USER_ID_MESSAGE");
                    groupId = extras.getString("GROUP_ID_MESSAGE");
                }
                if(intent.getAction().equals("MyQRCodeActivity")) {
                    userId = intent.getExtras().getString("USER_ID_MESSAGE");
                    groupId = extras.getString("GROUP_ID_MESSAGE");
                }
                Bundle extras_ = new Bundle();
                extras_.putString("GROUP_ID_MESSAGE", groupId);
                extras_.putString("USER_ID_MESSAGE", userId);
                EditText class_name = (EditText) findViewById(R.id.editText7);
                extras_.putString("GROUP_NAME_MESSAGE", class_name.getText().toString());
                extras_.putString("DATE_MESSAGE", date);
                openDate.putExtras(extras_);
                //Toast.makeText(this, intent.getStringExtra(TeacherMainActivity.USER_ID_MESSAGE), Toast.LENGTH_SHORT).show();
                //juzg
                startActivity(openDate);

            }
        });
        /*Button btn_delete = new Button(getApplicationContext());
        btn_delete.setBackgroundResource(R.drawable.delete_subject);
        btn_delete.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxKey = 0;
                for(String key: lessons.keySet()){
                    EditText lessonET = (EditText) findViewById(lessons.get(key));
                    if(Integer.parseInt(key) > (Integer)lesson.getId()){
                        lessonET.setId(Integer.parseInt(key)-1);
                        Toast.makeText(getApplicationContext(), Integer.toString(lessonET.getId()), Toast.LENGTH_SHORT).show();
                        lessons.put(Integer.toString(lessonET.getId()), lessonET.getId());
                        maxKey = Integer.parseInt(key);
                    }
                }
                lessons.remove(Integer.toString(maxKey));
                mlinearLayout.removeView(scrollView);
            }
        });*/
        //linearLayout.addView(lesson);
        //linearLayout.addView(btn_delete);
        //scrollView.addView(linearLayout);
        Log.d(TAG, "Date" + date);
        mlinearLayout.addView(date_);
    }

    public void saveClass(View view){
        Intent intentSaveClass = new Intent(this, TeacherMainActivity.class);
        intentSaveClass.setAction("CreateClassActivity");
        EditText class_name = (EditText) findViewById(R.id.editText7);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //class_name.setText(extras.getString("GROUP_NAME_MESSAGE"));
        String groupId = extras.getString("GROUP_ID_MESSAGE");
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        intentSaveClass.putExtra("USER_ID_MESSAGE", userId);
        if(!class_name.getText().toString().trim().equals("")) {
            DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
            docRefGroup.update("name", class_name.getText().toString().trim());
            //startActivity(intentSaveClass);
        }else{
            Toast.makeText(this, "Введите название группы!", Toast.LENGTH_SHORT).show();
        }
    }

    public void QRCode(View view){
        Intent intent = getIntent();
        groupId = intent.getExtras().getString("GROUP_ID_MESSAGE");
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("ExistingGroup")){
            userId = intent.getExtras().getString("USER_ID_MESSAGE");

        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        if(intent.getAction().equals("MyQRCodeActivity")) {
            userId = intent.getExtras().getString("USER_ID_MESSAGE");
        }
        Intent intent1 = new Intent(getApplicationContext(), MyQRCodeActivity.class);
        Bundle extras = new Bundle();
        extras.putString("USER_ID_MESSAGE", userId);
        extras.putString("GROUP_ID_MESSAGE", groupId);
        //nameField = (EditText) findViewById(R.id.editText7);
        extras.putString("GROUP_NAME_MESSAGE", nameField.getText().toString());
        intent1.putExtras(extras);
        startActivity(intent1);
    }

    /*@Override
    protected void onStop() {
        Intent intent = getIntent();
        String groupId = "";
        Bundle extras = intent.getExtras();
        if(intent.getAction().equals("TeacherMainActivity")) {
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        if(intent.getAction().equals("CreateTimetableActivity")){
            userId = extras.getString("USER_ID_MESSAGE");
            groupId = extras.getString("GROUP_ID_MESSAGE");
        }
        mFirestore.collection("lessons").whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot document: task.getResult()){
                                document.getReference().delete();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Удаление уроков не  выполнено", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mFirestore.collection("users").whereEqualTo("group", groupId).whereEqualTo("role", "pupil")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot document: task.getResult()){
                                document.getReference().update("group", "");
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Обновление поля group учеников не выполнено", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        DocumentReference docRefGroup = mFirestore.collection("groups").document(groupId);
        docRefGroup.delete();
        super.onStop();
    }*/
}
