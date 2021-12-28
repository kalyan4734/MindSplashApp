package com.mindsplash.afterlogin.common.learn.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.activities.JeeNetQuations;
import com.mindsplash.afterlogin.common.learn.activities.PracticeMain;
import com.mindsplash.network.model.Olympiad;

import java.util.ArrayList;

public class OlympiadDialog extends DialogFragment {
    private static Context context;
    private static ArrayList<Olympiad> olympiadArrayList;
    private TextView txtExamName,txtYear;
    private MaterialButton btnProceed;
    private Spinner spinnerExamNames,spinnerYears;
    ArrayList<String> examNames = null;
    ArrayList<String> years = null;
    private ArrayList<String> olympiadIds;
    private String olympiadId;

    public static OlympiadDialog newInstance(Context contet,ArrayList<Olympiad> olympiads) {
        context = contet;
        olympiadArrayList = olympiads;
        OlympiadDialog dialogFragment = new OlympiadDialog();
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_olympiad_select, container, false);
        init(view);
        onClickExamSpinner();
        onClickYearSpinner();
        onClickProceed();
        return view;
    }

    private void onClickProceed() {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PracticeMain)context).setOlympiadId(olympiadId);
                dismiss();
            }
        });
    }

    private void onClickExamSpinner() {
        spinnerExamNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtExamName.setText(examNames.get(i));
                olympiadId = olympiadIds.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClickYearSpinner() {
        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtYear.setText(years.get(i));
                btnProceed.setBackground(getResources().getDrawable(R.drawable.button_shape));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init(View view) {
        txtExamName = view.findViewById(R.id.txt_exam);
        txtYear = view.findViewById(R.id.txt_year);
        spinnerExamNames = view.findViewById(R.id.spinner_exam_name);
        spinnerYears = view.findViewById(R.id.spinner_year);
        btnProceed = view.findViewById(R.id.btn_proceed);

        if (olympiadArrayList.size()>0) {
            examNames = new ArrayList<>();
            years = new ArrayList<>();
            olympiadIds = new ArrayList<>();
            for (Olympiad olympiad : olympiadArrayList) {
                examNames.add(olympiad.getExam_name());
                years.add(olympiad.getExam_year());
                olympiadIds.add(olympiad.getOlympiad_id());
            }
        }
        ArrayAdapter<String> examAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, examNames);
        examAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerExamNames.setAdapter(examAdapter);

        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, years);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerYears.setAdapter(langAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
