package com.example.school_programm_as.admin.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.school_programm_as.Pupil;
import com.example.school_programm_as.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * @author Ilia Ilmenskii created on 16.09.2019
 * @project School_Programm_AS
 */
public class BillChangeFragment extends DialogFragment {

    private EditText editText;
    private Pupil pupil;

    BillChangeFragment(Pupil pupil) {
        this.pupil = pupil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_bill_dialog, container, false);
        editText = view.findViewById(R.id.CountOfEdit);
        Button plus = view.findViewById(R.id.PlusBill);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBill(true);
                dismiss();
            }
        });
        Button minus = view.findViewById(R.id.MinusBill);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBill(false);
                dismiss();
            }
        });

        return view;
    }

    private void changeBill(boolean plus) {
        try {
            final int bill = BillChangeFragment.this.pupil.bill;
            final int[] edited = {0};
            try {
                edited[0] = Integer.parseInt(editText.getText().toString());
            } catch (NumberFormatException e) {
                Log.d("AdminActivity", "Empty input");
            }
            if (!plus)
                edited[0] = -edited[0];
            final FirebaseFirestore mFirestrore = FirebaseFirestore.getInstance();
            mFirestrore.collection("users")
                    .document(pupil.userId)
                    .update(new HashMap<String, Object>(){{ put("bill", bill + edited[0]); }});
            ListOfPupilsActivity activity = (ListOfPupilsActivity) getActivity();
            pupil.bill += edited[0];
            if (activity != null)
                activity.updateStudentBill(pupil);
        } catch (ArithmeticException e) {
            Toast.makeText(getContext(), "Incorrect input data", Toast.LENGTH_SHORT).show();
        }
    }
}
