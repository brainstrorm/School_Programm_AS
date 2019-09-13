package com.example.school_programm_as.admin.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_programm_as.R;
import com.example.school_programm_as.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";

    private RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mFirestore = FirebaseFirestore.getInstance();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.ListOfTeachers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "Trying to get all teachers");
        mFirestore.collection("users").whereEqualTo("role", "teacher")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<User> teachers = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot doc : task.getResult()) {
                                User user = doc.toObject(User.class);
                                Log.d(TAG, String.format("Name : %s, Surname : %s", user.name,
                                        user.surname));
                                teachers.add(user);
                            }
                            teacherAdapter = new TeacherAdapter(teachers);
                            recyclerView.setAdapter(teacherAdapter);
                        } else {
                            Log.d(TAG, "Something failed");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.toString());
                    }
                });
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder {

        private View currentView;
        private User user;

        TeacherViewHolder(View view) {
            super(view);
            currentView = view;
        }

        void bind(final User user) {
            //Bind view
            TextView textView = currentView.findViewById(R.id.TeacherName);
            this.user = user;
            textView.setText(String.format("%s %s", user.name, user.surname));
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(ClassesListActivity.provideIntent(AdminActivity.this, user));
                }
            });
        }
    }

    class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {

        private ArrayList<User> teachers;

        TeacherAdapter(ArrayList<User> teachers) {
            this.teachers = teachers;
        }

        @Override
        public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
            holder.bind(teachers.get(position));
        }

        @NonNull
        @Override
        public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = AdminActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.teacher_name_view, parent, false);
            return new TeacherViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return teachers.size();
        }
    }
}
