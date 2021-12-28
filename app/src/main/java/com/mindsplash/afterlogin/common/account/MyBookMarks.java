package com.mindsplash.afterlogin.common.account;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterMyBookmarks;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.MyBookMarkResponse;
import com.mindsplash.network.model.Student;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;

public class MyBookMarks extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdapterMyBookmarks adapter;
    private ImageView backButton;
    private ImageView imgProfile;
    private AppSharedPreference appSharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        appSharedPreference= AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(this);
        init();
        onBackClick();
        getMyBookMarks();
    }

    private void getMyBookMarks() {
        StudentService studentService = new StudeServiceImpl();
//        Student student = appSharedPreference.getStudent();
        Student student  = new Gson().fromJson(
                appSharedPreference.getStudentDetails(),
                Student.class);
        studentService.getMyBookMarks("10", new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object!=null) {
                    MyBookMarkResponse myBookMarkResponse = (MyBookMarkResponse) object;
                    setTabs(myBookMarkResponse.getData());
                }
            }

            @Override
            public void onError(Object object) {

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

    private void setTabs(MyBookMarkResponse.BookmarkData data) {
        adapter = new AdapterMyBookmarks(getSupportFragmentManager(),data);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_START);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_bookmarks);
        viewPager = findViewById(R.id.view_pager);
        backButton = findViewById(R.id.backButton);
    }
}