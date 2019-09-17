package com.example.school_programm_as.admin.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school_programm_as.Pupil;
import com.example.school_programm_as.R;
import com.example.school_programm_as.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOfPupilsActivity extends AppCompatActivity {

    private static final String EXTRA_GROUP_ID = "group.ID";
    private static final String EXTRA_ADMIN_ID = "admin.ID";
    private static final String EXTRA_TEACHER_ID = "teacher.ID";
    private FirebaseFirestore mFirebase;

    private RecyclerView recyclerView;
    private TextView name;
    public static Intent provideIntent(Context packageContext, final String group, final String adminId, final String teacherId) {
        Intent intent = new Intent(packageContext, ListOfPupilsActivity.class);
        intent.putExtra(EXTRA_GROUP_ID, group);
        intent.putExtra(EXTRA_ADMIN_ID, adminId);
        intent.putExtra(EXTRA_TEACHER_ID, teacherId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_pupils);
        final String groupId = getIntent().getStringExtra(EXTRA_GROUP_ID);

        recyclerView = findViewById(R.id.ListOfPupils);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));

        name = findViewById(R.id.Name);

        mFirebase = FirebaseFirestore.getInstance();
        mFirebase.collection("groups").document(groupId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.get("name").toString());
            }
        });
        mFirebase.collection("users")
                .whereEqualTo("role", "pupil")
                .whereEqualTo("group", groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Pupil> pupils = new ArrayList<>();
                            for (final QueryDocumentSnapshot doc : task.getResult()) {
                                Pupil pupil = doc.toObject(Pupil.class);
                                pupils.add(pupil);
                            }
                            Collections.sort(pupils, new Comparator<Pupil>() {
                                @Override
                                public int compare(Pupil pupil, Pupil t1) {
                                    return pupil.name.compareTo(t1.name);
                                }
                            });
                            recyclerView.setAdapter(new PupilAdapter(pupils));
                            //TODO() add ArrayList to adapter and set adapter to RecyclerView
                        }
                    }
                });
    }

    class PupilViewHolder extends RecyclerView.ViewHolder {

        private View currentView;

        PupilViewHolder(View view) {
            super(view);
            currentView = view;
        }

        @SuppressLint("DefaultLocale")
        void bind(final Pupil pupil) {
            TextView textView = currentView.findViewById(R.id.FirstPupil);
            textView.setText(String.format("%s %s", pupil.name, pupil.surname));
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dialog = new BillChangeFragment(pupil);
                    FragmentManager manager = ListOfPupilsActivity.this.getSupportFragmentManager();
                    dialog.show(manager, "ID");
                }
            });
            View constraint = currentView.findViewById(R.id.constraintLayout2);
            TextView hexagon = constraint.findViewById(R.id.PupilBill);
            hexagon.setText(String.format("%d", pupil.bill));
        }
    }

    class PupilAdapter extends RecyclerView.Adapter<PupilViewHolder> {

        private ArrayList<Pupil> pupils;

        PupilAdapter(ArrayList<Pupil> pupils) {
            this.pupils = pupils;
        }

        @Override
        public void onBindViewHolder(@NonNull PupilViewHolder holder, int position) {
            holder.bind(pupils.get(position));
        }

        @NonNull
        @Override
        public PupilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = ListOfPupilsActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.pupils_item, parent, false);
            return new PupilViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return pupils.size();
        }
    }

    public void Back(View view){
        mFirebase.collection("users").document(getIntent().getStringExtra(EXTRA_TEACHER_ID))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                String adminId = getIntent().getStringExtra(EXTRA_ADMIN_ID);
                startActivity(ClassesListActivity.provideIntent(ListOfPupilsActivity.this, user, adminId));
            }
        });
    }
}
