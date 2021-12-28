package com.mindsplash.afterlogin.common.learn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.fragments.home.adapters.AdapterSubjects;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Subject;

import java.util.ArrayList;

public class LearnActivity extends AppCompatActivity {
    private RecyclerView rclSubjects;
    private AdapterSubjects adapterSubjects;
    private ImageView backButton,imgProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_learn);
        ArrayList<Subject> subjects = (ArrayList<Subject>) getIntent().getExtras().getSerializable("SUB");
        init(subjects);
        onBackClick();
        onClickProfile();
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearnActivity.this, MyAccount.class));
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

    private void init(ArrayList<Subject> subjectArrayList) {
        rclSubjects = findViewById(R.id.rcl_subjects_learn);
        backButton = findViewById(R.id.backButton);
        imgProfile = findViewById(R.id.profile);

        adapterSubjects = new AdapterSubjects(this,subjectArrayList, Constants.LEARN_ACTIVITY);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        rclSubjects.setLayoutManager(layoutManager);
        rclSubjects.setHasFixedSize(true);
        rclSubjects.setAdapter(adapterSubjects);
    }
}
