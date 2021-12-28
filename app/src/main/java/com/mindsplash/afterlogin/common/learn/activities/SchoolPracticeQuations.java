package com.mindsplash.afterlogin.common.learn.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.LearnActivity;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterAllQuations;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterOlympiadQuestion;
import com.mindsplash.afterlogin.common.learn.quationsinterfaces.QuationAnswserSelection;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.AllQuation;
import com.mindsplash.network.model.AllQuationsResponse;
import com.mindsplash.network.model.Olympiad;
import com.mindsplash.network.model.OlympiadDetailRes;
import com.mindsplash.network.model.OlympiadQues;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;
import com.mindsplash.services.subject.quations.QuationsService;
import com.mindsplash.services.subject.quations.quationsimpl.QuationsImpl;

import java.util.ArrayList;
import java.util.Map;

public class SchoolPracticeQuations extends AppCompatActivity {
    private static int answeredPosition;
    private RecyclerView rclQuations;
    private QuationsService quationsService;
    private AppSharedPreference appSharedPreference;
    private SharedPreferences sharedPreferences;
    private Dialog progressDialog;
    private ImageView imgProfile;
    private AdapterAllQuations adapter;
    private MaterialButton btnNext, btnSubmit;
    private Map<String, Boolean> answeredMap;
    public static String quationId;
    public static boolean isCorrect;
    public int quationSize;
    public static int position = 0;
    private ImageView imgBack, backButton;
    private ArrayList<AllQuation> allQuations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_quations);
        init();
        getSchoolQuestions();
        onClickProfile();
        onClickBack();
    }

    private void onClickBack() {
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(view -> startActivity(new Intent(SchoolPracticeQuations.this, MyAccount.class)));
    }

    private void getSchoolQuestions() {
        progressDialog.show();
        appSharedPreference = AppSharedPreference.getMInstance();
        sharedPreferences = AppSharedPreference.getInstance(this);
        quationsService = new QuationsImpl();
        quationsService.getAllSchoolQuestions(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    AllQuationsResponse allQuationsResponse = (AllQuationsResponse) object;
                    allQuations = (ArrayList<AllQuation>) allQuationsResponse.getData();
                    setQuestions(allQuations);
                } else
                    new AppUtils().showError(SchoolPracticeQuations.this, getString(R.string.somthing_wrong));
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
            }
        });
    }

    private void setQuestions(ArrayList<AllQuation> allQuations) {
        adapter = new AdapterAllQuations(allQuations, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclQuations.setLayoutManager(layoutManager);
        rclQuations.setHasFixedSize(true);
        rclQuations.setAdapter(adapter);
        onAnswerSelect();
        onNextClick(allQuations);
        onBackClick();
        onSubmitClick();
    }

    private void onBackClick() {
        imgBack.setOnClickListener(view -> {
            if (position > 0) {
                rclQuations.smoothScrollToPosition(position - 1);
                position--;
            }
        });
    }


    private void onSubmitClick() {
        btnSubmit.setOnClickListener(view -> {
            adapter.onAnsSubmited(answeredPosition, quationId, isCorrect);
            adapter.notifyItemChange(quationId);
            btnNext.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        });
    }

    private void onNextClick(ArrayList<AllQuation> olympiadQues) {
        btnNext.setOnClickListener(view -> {
            int size = allQuations.size();
            if (position < size) {
                adapter.onAnsSubmited(0, null, false);
                if (position < olympiadQues.size()) {
                    adapter.notifyItemChanged(position + 1);
                    rclQuations.smoothScrollToPosition(position + 1);
                    position++;
                }
            } else
                btnNext.setBackground(getResources().getDrawable(R.drawable.gray_rounded_button));
        });
    }

    private void onAnswerSelect() {
        adapter.setQuationAnswserSelection((answeredPosition, quationId, isCorrect) -> {
            SchoolPracticeQuations.quationId = quationId;
            SchoolPracticeQuations.isCorrect = isCorrect;
            SchoolPracticeQuations.answeredPosition = answeredPosition;
            btnNext.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        });
    }

    private void init() {
        rclQuations = findViewById(R.id.rcl_quations);
        imgProfile = findViewById(R.id.profile);
        btnNext = findViewById(R.id.btn_next);
        btnSubmit = findViewById(R.id.btn_submit);
        imgBack = findViewById(R.id.img_back);
        backButton = findViewById(R.id.backButton);

        progressDialog = new AppUtils().getProgressDialog(this);
    }
}
