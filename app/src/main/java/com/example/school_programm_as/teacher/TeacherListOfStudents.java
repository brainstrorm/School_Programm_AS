package com.example.school_programm_as.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school_programm_as.CreateTimetableActivity;
import com.example.school_programm_as.Group;
import com.example.school_programm_as.Lesson;
import com.example.school_programm_as.Pupil;
import com.example.school_programm_as.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
        ((TextView)findViewById(R.id.Name)).setText(lessonName);


        setupRecyclerView();
    }

    private void setupRecyclerView() {
    }

    public void Back(View view) {
        Intent back = CreateTimetableActivity.provideBackIntent(this);
        startActivity(back);
    }

    class PupilsViewHolder extends RecyclerView.ViewHolder {
        PupilsViewHolder(View view) {
            super(view);
        }

        void bind(Pupil pupil) {

        }
    }

    class PupilsAdapter extends RecyclerView.Adapter<PupilsViewHolder> {

        ArrayList<Pupil> pupils;

        PupilsAdapter(ArrayList<Pupil> pupils) {
            this.pupils = pupils;
        }

        @NonNull
        @Override
        public PupilsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.pupils_item_extended, parent, false);
            return new PupilsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PupilsViewHolder holder, int position) {
            holder.bind(pupils.get(position));
        }

        @Override
        public int getItemCount() {
            return pupils.size();
        }
    }

}
