package com.example.school_programm_as.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.school_programm_as.Group;
import com.example.school_programm_as.Lesson;
import com.example.school_programm_as.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherListOfStudents extends AppCompatActivity {

    private static final String GROUP_EXTRA_ID = "group.extra.id";
    private static final String LESSON_EXTRA_NAME = "lesson.extra.name";

    private FirebaseFirestore mFirestore;
    private String groupId;
    private String lessonName;

    private RecyclerView recyclerView;

    public static Intent provideIntent(Context packageContext, final String groupId, String lesson) {
        Intent mIntent = new Intent(packageContext, TeacherListOfStudents.class);
        mIntent.putExtra(GROUP_EXTRA_ID, groupId);
        mIntent.putExtra(LESSON_EXTRA_NAME, lesson);
        return mIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_pupils);
        mFirestore = FirebaseFirestore.getInstance();
        groupId = getIntent().getStringExtra(GROUP_EXTRA_ID);
        lessonName = getIntent().getStringExtra(LESSON_EXTRA_NAME);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
    }


}
