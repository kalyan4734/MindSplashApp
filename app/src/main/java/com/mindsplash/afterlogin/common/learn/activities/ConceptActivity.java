package com.mindsplash.afterlogin.common.learn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.adapters.ConceptViewPagerAdapter;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.ConceptListData;
import com.mindsplash.network.model.ConceptResponse;
import com.mindsplash.services.subject.concepimpl.ConceptService;
import com.mindsplash.services.subject.concepimpl.ConceptServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ConceptActivity extends AppCompatActivity {
    private TextView lblSubjectName, txtChapterNo, txtChapterName;
    private String chapterName;
    private String subjectName;
    private String subjId;
    private String cid;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ConceptViewPagerAdapter adapter;
    private TextView txtDataNotAvailable;
    private ImageView backButton, imgProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concept);
        chapterName = getIntent().getStringExtra(Constants.CHAP_NAME);
        subjectName = getIntent().getStringExtra(Constants.SUBJ_NAME);
        subjId = getIntent().getStringExtra(Constants.SUBJ_ID);
        cid = getIntent().getStringExtra(Constants.CHAP_ID);
        init();
        getConcepts();
        onBackClick();
        onClickProfile();
    }

    private void getConcepts() {
        ConceptService conceptService = new ConceptServiceImpl();
        conceptService.getDefinitions(subjId, cid, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    ConceptResponse conceptResponse = (ConceptResponse) object;
                    setTabs(conceptResponse.getData());
                    if (conceptResponse.getData() != null)
                        txtDataNotAvailable.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConceptActivity.this, MyAccount.class));
            }
        });
    }

    private void onBackClick() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTabs(ConceptListData data) {
        adapter = new ConceptViewPagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_START);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_concepts);
        viewPager = findViewById(R.id.view_pager);
        backButton = findViewById(R.id.backButton);
        imgProfile = findViewById(R.id.profile);
        txtDataNotAvailable = findViewById(R.id.txt_data);
    }
}
