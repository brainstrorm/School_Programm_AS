package com.example.school_programm_as.admin.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOfPupilsActivity extends AppCompatActivity {

    private static final String EXTRA_GROUP_NAME = "group.name";

    private FirebaseFirestore mFirebase;

    private RecyclerView recyclerView;
    private PupilAdapter adapter;

    public static Intent provideIntent(Context packageContext, final String group) {
        Intent intent = new Intent(packageContext, ListOfPupilsActivity.class);
        intent.putExtra(EXTRA_GROUP_NAME, group);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_pupils);
        final String groupName = getIntent().getStringExtra(EXTRA_GROUP_NAME);

        recyclerView = findViewById(R.id.ListOfPupils);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));

        mFirebase = FirebaseFirestore.getInstance();
        mFirebase.collection("users")
                .whereEqualTo("role", "pupil")
                .whereEqualTo("group", groupName)
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
}
