package com.mindsplash.afterlogin.common.learn.activities;

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
import com.mindsplash.helper.Constants;

public class ConceptPractiveActivity extends AppCompatActivity {
    private LinearLayout llConcept,llPractice;
    private String chapterName;
    private String subjectName;
    private String subjId;
    private String cId;
    private TextView txtChapterName;
    private ImageView imgProfile,imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_learn);
        chapterName = getIntent().getStringExtra(Constants.CHAP_NAME);
        subjectName = getIntent().getStringExtra(Constants.SUBJ_NAME);
        subjId = getIntent().getStringExtra(Constants.SUBJ_ID);
        cId = getIntent().getStringExtra(Constants.CHAP_ID);
        init();
        onClickConcept();
        onClickPractice();
        onClickBack();
        onClickProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llConcept.setBackground(getResources().getDrawable(R.drawable.edittext));
        llPractice.setBackground(getResources().getDrawable(R.drawable.edittext));
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConceptPractiveActivity.this, MyAccount.class));
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

    private void onClickConcept() {
        llConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llConcept.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llConcept.setBackground(getResources().getDrawable(R.drawable.edittext));
                        Intent intent = new Intent(ConceptPractiveActivity.this,ConceptActivity.class);
                        intent.putExtra(Constants.CHAP_NAME,chapterName);
                        intent.putExtra(Constants.SUBJ_NAME,subjectName);
                        intent.putExtra(Constants.SUBJ_ID,subjId);
                        intent.putExtra(Constants.CHAP_ID,cId);
                        startActivity(intent);
                    }
                },600);
            }
        });
    }

    private void onClickPractice() {
        llPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPractice.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llPractice.setBackground(getResources().getDrawable(R.drawable.edittext));
                        Intent intent = new Intent(ConceptPractiveActivity.this,PracticeMain.class);
                        intent.putExtra(Constants.CHAP_NAME,chapterName);
                        intent.putExtra(Constants.SUBJ_NAME,subjectName);
                        intent.putExtra(Constants.SUBJ_ID,subjId);
                        intent.putExtra(Constants.CHAP_ID,cId);
                        startActivity(intent);
                    }
                },600);
            }
        });
    }

    private void init(){
        llConcept = findViewById(R.id.concepts);
        llPractice = findViewById(R.id.practice);
        txtChapterName = findViewById(R.id.txt_chapter_name);
        imgBack = findViewById(R.id.backButton);
        imgProfile = findViewById(R.id.profile);

        txtChapterName.setText(chapterName);
    }
}
