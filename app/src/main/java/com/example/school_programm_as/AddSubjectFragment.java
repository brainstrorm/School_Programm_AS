package com.example.school_programm_as;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddSubjectFragment extends DialogFragment {

    private EditText name;
    private String date;
    private int number;
    private String groupId;

    public AddSubjectFragment(final String date, final int number, final String groupId) {
        // Required empty public constructor
        this.date = date;
        this.number = number;
        this.groupId = groupId;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject, container, false);
        name = (EditText) view.findViewById(R.id.Name);

        Button addLesson = (Button) view.findViewById(R.id.AddLesson);
        addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusLesson();
                dismiss();
            }
        });

        Button cancel = (Button) view.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    private void plusLesson(){
        final String nameOfLesson = name.getText().toString();

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> lesson = new HashMap<>();
        lesson.put("date", date);
        lesson.put("group", groupId);
        lesson.put("name", nameOfLesson);
        lesson.put("number", number);
        mFirestore.collection("lessons").document()
                .set(lesson);
        CreateTimetableActivity activity = (CreateTimetableActivity) getActivity();
        activity.plusLesson(nameOfLesson);
    }

}
