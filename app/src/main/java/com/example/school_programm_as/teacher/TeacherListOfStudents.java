package com.example.school_programm_as.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school_programm_as.CreateTimetableActivity;
import com.example.school_programm_as.Group;
import com.example.school_programm_as.Lesson;
import com.example.school_programm_as.Pupil;
import com.example.school_programm_as.R;
import com.example.school_programm_as.TeacherMainActivity;
import com.example.school_programm_as.admin.domain.UpdateBillInteface;
import com.example.school_programm_as.admin.presentation.BillChangeFragment;
import com.example.school_programm_as.admin.presentation.ListOfPupilsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class TeacherListOfStudents extends AppCompatActivity implements UpdateBillInteface {

    private static final String GROUP_EXTRA_ID = "group.extra.id";
    private static final String LESSON_EXTRA_NAME = "lesson.extra.name";
    private static final String DATE_EXTRA = "date.extra.name";

    private FirebaseFirestore mFirestore;
    private String groupId;
    private String lessonName;
    private String date;
    private String lessonId;

    private RecyclerView recyclerView;
    private PupilsAdapter adapter;

    public static Intent provideIntent(Context packageContext, final String groupId, String lesson, String date) {
        Intent mIntent = new Intent(packageContext, TeacherListOfStudents.class);
        mIntent.putExtra(GROUP_EXTRA_ID, groupId);
        mIntent.putExtra(LESSON_EXTRA_NAME, lesson);
        mIntent.putExtra(DATE_EXTRA, date);
        return mIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_pupils);
        mFirestore = FirebaseFirestore.getInstance();
        groupId = getIntent().getStringExtra(GROUP_EXTRA_ID);
        lessonName = getIntent().getStringExtra(LESSON_EXTRA_NAME);
        date = getIntent().getStringExtra(DATE_EXTRA);
        ((TextView)findViewById(R.id.Name)).setText(lessonName);

        setupRecyclerView();
    }

    public void setupRecyclerView() {
        recyclerView = findViewById(R.id.ListOfPupils);
        final ArrayList<Pupil> pupils = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        mFirestore.collection("users")
                .whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Pupil pupil = doc.toObject(Pupil.class);
                                pupils.add(pupil);
                            }
                            mFirestore.collection("lessons")
                                    .whereEqualTo("group", groupId)
                                    .whereEqualTo("name", lessonName)
                                    .whereEqualTo("date", date)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<String> statuses = new ArrayList<>();
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    lessonId = doc.getId();
                                                    for (Pupil pupil : pupils) {
                                                        String status = doc.getString(pupil.userId);
                                                        statuses.add(status);
                                                    }
                                                    adapter = new PupilsAdapter(pupils);
                                                    recyclerView.setAdapter(adapter);
                                                    adapter.setStatuses(statuses);
                                                    break;
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void updateStudentBill(Pupil pupil) {
        adapter.notifyDataSetChanged();
    }

    public void Back(View view) {
        Intent back = CreateTimetableActivity.provideBackIntent(this);
        startActivity(back);
    }

    class PupilsViewHolder extends RecyclerView.ViewHolder {

        private View currentView;

        PupilsViewHolder(View view) {
            super(view);
            currentView = view;
        }

        @SuppressWarnings("DefaultLocale")
        void bind(final Pupil pupil, final String status) {
            TextView textView = currentView.findViewById(R.id.FirstPupil);
            textView.setText(String.format("%s %s", pupil.name, pupil.surname));
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dialog = new BillChangeFragment(pupil);
                    FragmentManager manager = TeacherListOfStudents.this.getSupportFragmentManager();
                    dialog.show(manager, "ID");
                }
            });
            View constraint = currentView.findViewById(R.id.constraintLayout2);
            TextView hexagon = constraint.findViewById(R.id.PupilBill);
            ImageView present =  currentView.findViewById(R.id.PresentOrNot);
            present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PresentChangeFragment dialog = new PresentChangeFragment(pupil, lessonId);
                    FragmentManager manager = TeacherListOfStudents.this.getSupportFragmentManager();
                    dialog.show(manager, "ID");
                }
            });
            int resource = 0;
            switch (status) {
                case "present": {
                    resource = R.drawable.group_76;
                    break;
                }
                case "notpresent": {
                    resource = R.drawable.group_14;
                    break;
                }
                default: {
                    resource = R.drawable.student_profile_latecomer;
                }
            }
            present.setImageDrawable(getDrawable(resource));
            hexagon.setText(String.format("%d", pupil.bill));
        }
    }

    class PupilsAdapter extends RecyclerView.Adapter<PupilsViewHolder> {

        ArrayList<Pupil> pupils;
        ArrayList<String> statuses;

        void setStatuses(ArrayList<String> statuses) {
            this.statuses = statuses;
        }

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
            holder.bind(pupils.get(position), statuses.get(position));
        }

        @Override
        public int getItemCount() {
            return pupils.size();
        }
    }

}
