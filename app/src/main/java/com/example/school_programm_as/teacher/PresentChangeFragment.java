package com.example.school_programm_as.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.school_programm_as.Pupil;
import com.example.school_programm_as.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * @author Ilia Ilmenskii created on 22.09.2019
 * @project School_Programm_AS
 */
public class PresentChangeFragment extends DialogFragment implements View.OnClickListener {

    private Pupil pupil;
    private String lessonId;
    private String groupId;
    private String date;

    PresentChangeFragment(Pupil pupil, String lessonId) {
        this.pupil = pupil;
        this.lessonId = lessonId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pupil_present, container, false);
        View firstBtn = view.findViewById(R.id.Present);
        firstBtn.setOnClickListener(this);
        View secondBtn = view.findViewById(R.id.NotPresent);
        secondBtn.setOnClickListener(this);
        View thirdBtn = view.findViewById(R.id.Latecomer);
        thirdBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final String status;
        switch (view.getId()) {
            case R.id.Present: {
                status = "present";
                break;
            }
            case R.id.Latecomer: {
                status = "respectfull";
                break;
            }
            default: {
                status = "notpresent";
            }
        }
        firebaseFirestore.collection("lessons")
                .document(lessonId)
                .update(new HashMap<String, Object>(){ { put(pupil.userId, status); } });
        TeacherListOfStudents activity = (TeacherListOfStudents) getActivity();
        if (activity != null) {
            activity.setupRecyclerView();
            dismiss();
        }
    }
}
