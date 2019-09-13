package com.example.school_programm_as.admin.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school_programm_as.Group;
import com.example.school_programm_as.R;
import com.example.school_programm_as.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClassesListActivity extends AppCompatActivity {

    private static final String EXTRA_TEACHER_ID = "teacher.id";
    private static final String EXTRA_TEACHER_NAME = "teacher.name";

    private FirebaseFirestore mFirebase;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;

    public static Intent provideIntent(Context packageContext, User user) {
        Intent intent = new Intent(packageContext, ClassesListActivity.class);
        intent.putExtra(EXTRA_TEACHER_NAME, String.format("%s %s", user.name, user.surname));
        intent.putExtra(EXTRA_TEACHER_ID, user.userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);
        mFirebase = FirebaseFirestore.getInstance();
        String teacherId = getIntent().getStringExtra(EXTRA_TEACHER_ID);
        recyclerView = findViewById(R.id.ListOfClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebase.collection("groups").whereEqualTo("teacherId", teacherId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Group> groups = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot doc : task.getResult()) {
                                Group group = doc.toObject(Group.class);
                                Log.d("AdminActivity", group.name);
                                groups.add(group);
                            }
                            groupAdapter = new GroupAdapter(groups);
                            recyclerView.setAdapter(groupAdapter);
                        }
                    }
                });
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private View currentView;
        private Group group;

        GroupViewHolder(View view) {
            super(view);
            currentView = view;
        }

        void bind(Group group) {
            this.group = group;
            //TODO():Fix to new layout
            TextView textView = currentView.findViewById(R.id.TeacherName);
            textView.setText(group.name);
        }
    }

    class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

        private ArrayList<Group> groups;

        GroupAdapter(ArrayList<Group> groups) {
            this.groups = groups;
        }

        @NonNull
        @Override
        public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = ClassesListActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.teacher_name_view, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
            holder.bind(groups.get(position));
        }

        @Override
        public int getItemCount() {
            return groups.size();
        }
    }
}
