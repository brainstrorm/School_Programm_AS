package com.example.school_programm_as;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.example.school_programm_as.admin.presentation.BillChangeFragment;
import com.example.school_programm_as.admin.presentation.ListOfPupilsActivity;
import com.example.school_programm_as.teacher.TeacherListOfStudents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CreateTimetableActivity extends AppCompatActivity {

    public final static String EXTRA_ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";
    public final static String USER_ID_MESSAGE = "";
    public final static String GROUP_ID_MESSAGE = "com.example.school_programm_AS.MESSAGE";

    private static final String TAG = "CreateTimetableActivity";
    private TextView mSetDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private FirebaseFirestore mFirestore;
    private TextView TVDay;
    private LinearLayout mlinearLayout;
    private HashMap<String, Integer> lessons = new HashMap<String, Integer>();
    private EditText ETLesson;
    private String date;
    private int countID = 1;
    private int number = 1;
    private String user_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timetable);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String day = extras.getString("DAY_MESSAGE");
        mlinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mSetDate = (TextView) findViewById(R.id.TVDate);
        TVDay = (TextView) findViewById(R.id.xDay);
        mSetDate.setText(intent.getExtras().getString("DATE_MESSAGE"));
        date = mSetDate.getText().toString();

        mSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTimetableActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                Log.d(TAG, "OnDateSet: date:" + day + "/" + month + "/" + year);
                date = day + "." + month + "." + year;
                mSetDate.setText(date);

            }
        };

        mFirestore = FirebaseFirestore.getInstance();

        if(!intent.getExtras().getString("DATE_MESSAGE").equals("")){
            TVDay.setBackgroundColor(0000);
            TVDay.setText(intent.getExtras().getString("DATE_MESSAGE"));
            mFirestore.collection("lessons")
                    .whereEqualTo("group", intent.getExtras().getString("GROUP_ID_MESSAGE"))
                    .whereEqualTo("date", intent.getExtras().getString("DATE_MESSAGE"))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    String name = document.get("name").toString();
                                    plusLesson(name);
                                }
                            }
                        }
                    });
        }


    }

    public void plusLesson_(View view){
        if(mSetDate.getText() != "") {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            String groupId = extras.getString("GROUP_ID_MESSAGE");
            DialogFragment dialog = new AddSubjectFragment(date, number, groupId);
            number++;
            FragmentManager manager = CreateTimetableActivity.this.getSupportFragmentManager();
            dialog.show(manager, "ID");
        }else{
            Toast.makeText(getApplicationContext(), "Введите дату", Toast.LENGTH_SHORT).show();
        }
    }
    public void plusLesson(final String name){
        /*final HorizontalScrollView scrollView = new HorizontalScrollView(getApplicationContext());
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );
        scrollView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );*/
        final TextView lesson = new TextView(getApplicationContext());
        lesson.setId(countID);
        Toast.makeText(CreateTimetableActivity.this, " " + lesson.getId(), Toast.LENGTH_SHORT).show();
        lesson.setBackgroundResource(R.drawable.lesson_field);
        lesson.setText(name);
        lesson.setTextSize(30);
        lesson.setPadding(50, 20, 0, 0);
        lesson.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TeacherListOfStudents.provideIntent(CreateTimetableActivity.this,
                        getIntent().getStringExtra(GROUP_ID_MESSAGE),
                        name);
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
        mlinearLayout.addView(lesson);
        lessons.put(Integer.toString(lesson.getId()), lesson.getId());
        countID++;
    }

    public void deleteLessons(View view){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String groupId = extras.getString("GROUP_ID_MESSAGE");
        mFirestore.collection("lessons").whereEqualTo("group", groupId).whereEqualTo("date", mSetDate.getText().toString())
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
        number = 1;
        mlinearLayout.removeAllViews();
    }

    public void changeDate(View view){
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                Log.d(TAG, "OnDateSet: date:" + day + "/" + month + "/" + year);
                date = day + "." + month + "." + year;
                mSetDate.setText(date);
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                String groupId = extras.getString("GROUP_ID_MESSAGE");

                mFirestore.collection("lessons").whereEqualTo("group", groupId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(final QueryDocumentSnapshot document: task.getResult()){
                                        Log.d(TAG, "new date:" + date + ", lessonId" + document.getId());
                                        document.getReference().update("date", date);
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Удаление уроков не  выполнено", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
                CreateTimetableActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    public void backToCreateClass(View view){
        Intent intentBackToCreateClassActivity = new Intent(this, CreateClassActivity.class);
        intentBackToCreateClassActivity.setAction("CreateTimetableActivity");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        extras.putString("USER_ID_MESSAGE", intent.getExtras().getString("USER_ID_MESSAGE"));
        extras.putString("GROUP_ID_MESSAGE", intent.getExtras().getString("GROUP_ID_MESSAGE"));
        extras.putString("GROUP_NAME_MESSAGE", intent.getExtras().getString("GROUP_NAME_MESSAGE"));
        intentBackToCreateClassActivity.putExtras(extras);
        startActivity(intentBackToCreateClassActivity);
    }
    //comment for commit

}
