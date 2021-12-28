package com.mindsplash.afterlogin.common.learn.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterChapter;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterOlympiadQuestion;
import com.mindsplash.afterlogin.common.learn.dialogs.OlympiadDialog;
import com.mindsplash.afterlogin.common.learn.quationsinterfaces.QuationAnswserSelection;
import com.mindsplash.beforelogin.RegisterActivity;
import com.mindsplash.beforelogin.dialogs.DialogClassSelection;
import com.mindsplash.beforelogin.dialogs.DialogVerifyEmailSent;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Olympiad;
import com.mindsplash.network.model.OlympiadDetailRes;
import com.mindsplash.network.model.OlympiadQues;
import com.mindsplash.network.model.OlympiadQuesRes;
import com.mindsplash.network.model.Quation;
import com.mindsplash.services.subject.quations.QuationsService;
import com.mindsplash.services.subject.quations.quationsimpl.QuationsImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JeeNetQuations extends AppCompatActivity {
    private static int answeredPosition;
    private RecyclerView rclQuations;
    private QuationsService quationsService;
    private ArrayList<Olympiad> olympiadArrayList;
    private Dialog progressDialog;
    private String chapterName;
    private String subjectName;
    private String subjId;
    private String cid;
    private String examName;
    private String year;
    private String olympiadId;
    private AdapterOlympiadQuestion adapter;
    private ImageView imgProfile, imgBack;
    private TextView txtSubjName, txtChapName;
    private MaterialButton btnNext, btnSubmit;
    private Map<String, Boolean> answeredMap;
    public static String quationId;
    public static boolean isCorrect;
    private ImageView imgBackQuation;
    private int position=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jee_quations);
        chapterName = getIntent().getStringExtra(Constants.CHAP_NAME);
        subjectName = getIntent().getStringExtra(Constants.SUBJ_NAME);
        subjId = getIntent().getStringExtra(Constants.SUBJ_ID);
        cid = getIntent().getStringExtra(Constants.CHAP_ID);
        olympiadId = getIntent().getStringExtra(Constants.OLYMPIAD_ID);
        progressDialog = new AppUtils().getProgressDialog(this);
        answeredMap = new HashMap<>();
        init();
        onClickBack();
        onClickProfile();
        getOmpiadQuations(olympiadId);
    }

    private void init() {
        rclQuations = findViewById(R.id.rcl_quations);
        txtSubjName = findViewById(R.id.txt_subject_name);
        txtChapName = findViewById(R.id.txt_chapter_name);
        imgBack = findViewById(R.id.backButton);
        imgProfile = findViewById(R.id.profile);
        btnNext = findViewById(R.id.btn_next);
        btnSubmit = findViewById(R.id.btn_submit);
        imgBackQuation = findViewById(R.id.img_back);

        txtSubjName.setText(subjectName);
        txtChapName.setText(chapterName);
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JeeNetQuations.this, MyAccount.class));
            }
        });
    }

    private void onClickBack() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getOmpiadQuations(String olympiadId) {
        progressDialog.show();
        if (quationsService == null)
            quationsService = new QuationsImpl();
        quationsService.getOlympiadQuestions(olympiadId, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    OlympiadQuesRes olympiadQuesRes = (OlympiadQuesRes) object;
                    ArrayList<OlympiadQues> olympiadArrayList = (ArrayList<OlympiadQues>) olympiadQuesRes.getData();
                    setQuestions(olympiadArrayList);
                } else
                    new AppUtils().showError(JeeNetQuations.this, getString(R.string.somthing_wrong));
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
                new AppUtils().showError(JeeNetQuations.this, getString(R.string.somthing_wrong));
            }
        });
    }

    private void setQuestions(ArrayList<OlympiadQues> olympiadQues) {
        adapter = new AdapterOlympiadQuestion(olympiadQues, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclQuations.setLayoutManager(layoutManager);
        rclQuations.setHasFixedSize(true);
        rclQuations.setAdapter(adapter);
        onAnswerSelect();
        onNextClick(olympiadQues);
        onSubmitClick();
    }

    private void onSubmitClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onAnsSubmited(answeredPosition, quationId, isCorrect);
                adapter.notifyItemChange(quationId);
                btnNext.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);
            }
        });
    }

    private void onBackClick() {
        imgBackQuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    rclQuations.smoothScrollToPosition(position - 1);
                    position--;
                }
            }
        });
    }

    private void onNextClick(ArrayList<OlympiadQues> olympiadQues) {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCurrentPosition() < olympiadQues.size()) {
                    adapter.onAnsSubmited(0, null, false);
                    if (position < olympiadQues.size()) {
                        adapter.notifyItemChanged(position + 1);
                        rclQuations.smoothScrollToPosition(position + 1);
                        position++;
                    }
                } else
                    btnNext.setBackground(getResources().getDrawable(R.drawable.gray_rounded_button));
            }
        });
    }

    private void onAnswerSelect() {
        adapter.setQuationAnswserSelection(new QuationAnswserSelection() {
            @Override
            public void onAnswerSelected(int answeredPosition, String quationId, boolean isCorrect) {
                JeeNetQuations.quationId = quationId;
                JeeNetQuations.isCorrect = isCorrect;
                JeeNetQuations.answeredPosition = answeredPosition;
                btnNext.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });
    }
}
