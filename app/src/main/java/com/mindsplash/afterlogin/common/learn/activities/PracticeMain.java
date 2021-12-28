package com.mindsplash.afterlogin.common.learn.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.dialogs.OlympiadDialog;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Olympiad;
import com.mindsplash.network.model.OlympiadDetailRes;
import com.mindsplash.services.subject.quations.QuationsService;
import com.mindsplash.services.subject.quations.quationsimpl.QuationsImpl;

import java.util.ArrayList;

public class PracticeMain extends AppCompatActivity {
    private LinearLayout llSchool,llPractice;
    private String chapterName;
    private String subjectName;
    private String subjId;
    private String cid;
    private Dialog progressDialog;
    private QuationsService quationsService;
    private ArrayList<Olympiad> olympiadArrayList;
    private TextView txtSubjectName,txtChapName,txtChapNo;
    private ImageView imgProfile,btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        chapterName = getIntent().getStringExtra(Constants.CHAP_NAME);
        subjectName = getIntent().getStringExtra(Constants.SUBJ_NAME);
        subjId = getIntent().getStringExtra(Constants.SUBJ_ID);
        cid = getIntent().getStringExtra(Constants.CHAP_ID);
        progressDialog = new AppUtils().getProgressDialog(this);
        init();
        onClickSchool();
        onClickJeeNet();
        onClickProfile();
        onBackClick();
    }

    private void onBackClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PracticeMain.this, MyAccount.class));
            }
        });
    }

    private void onClickSchool() {
        llSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSchool.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llSchool.setBackground(getResources().getDrawable(R.drawable.edittext));
                        startActivity(new Intent(PracticeMain.this,SchoolPracticeQuations.class));
                    }
                },600);
            }
        });
    }

    private void onClickJeeNet() {
        llPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPractice.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llPractice.setBackground(getResources().getDrawable(R.drawable.edittext));
                        getOlympiadDetails();
                    }
                },400);
            }
        });
    }

    private void getOlympiadDetails() {
        progressDialog.show();
        if (quationsService == null)
            quationsService = new QuationsImpl();
        quationsService.getOlympiadDetails(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    OlympiadDetailRes olympiadDetailRes = (OlympiadDetailRes) object;
                    olympiadArrayList = (ArrayList<Olympiad>) olympiadDetailRes.getData();
                    OlympiadDialog dialog = OlympiadDialog.newInstance(PracticeMain.this, olympiadArrayList);
                    dialog.show(getSupportFragmentManager(), "dialog");
                } else
                    new AppUtils().showError(PracticeMain.this, getString(R.string.somthing_wrong));
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
                new AppUtils().showError(PracticeMain.this, getString(R.string.somthing_wrong));
            }
        });
    }


    private void init() {
        llSchool = findViewById(R.id.ll_school);
        llPractice = findViewById(R.id.ll_practice);
        imgProfile = findViewById(R.id.profile);

        txtSubjectName = findViewById(R.id.lbl_subject_name);
        txtChapNo = findViewById(R.id.txt_chapter_no);
        txtChapName = findViewById(R.id.txt_chapter_name);
        btnBack = findViewById(R.id.backButton);

        txtSubjectName.setText(subjectName);
        txtChapName.setText(chapterName);
    }

    public void setOlympiadId(String olympiadId) {
        Intent intent = new Intent(PracticeMain.this,JeeNetQuations.class);
        intent.putExtra(Constants.CHAP_NAME,chapterName);
        intent.putExtra(Constants.SUBJ_NAME,subjectName);
        intent.putExtra(Constants.SUBJ_ID,subjId);
        intent.putExtra(Constants.CHAP_ID,cid);
        intent.putExtra(Constants.OLYMPIAD_ID,olympiadId);
        startActivity(intent);
    }
}
