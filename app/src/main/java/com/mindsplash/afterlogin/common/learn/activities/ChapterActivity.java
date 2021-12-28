package com.mindsplash.afterlogin.common.learn.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterChapter;
import com.mindsplash.afterlogin.fragments.home.adapters.AdapterSubjects;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.ConnectionDetector;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Chapter;
import com.mindsplash.network.model.ChapterListResponse;
import com.mindsplash.network.model.SubjectDetails;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterActivity extends AppCompatActivity {
    private RecyclerView rclChapter;
    private StudentService studentService;
    private ConnectionDetector connectionDetector;
    private String subjectId;
    private AdapterChapter adapterChapter;
    private TextView txtNoOfChapters,txtSubjectName;
    private ImageView backButton;
    private String subjectName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chapter);
        subjectId = getIntent().getStringExtra(Constants.SUBJ_ID);
        subjectName = getIntent().getStringExtra(Constants.SUBJ_NAME);
        init();
        getChapters();
        onBackClick();
    }

    private void onBackClick() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getChapters() {
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet())
            callApiTogetChapters();

    }

    private void callApiTogetChapters() {
        studentService = new StudeServiceImpl();
        studentService.getChapters(subjectId, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object!=null) {
                    ChapterListResponse chapterListResponse = (ChapterListResponse) object;
                    setChapters((ArrayList<Chapter>) chapterListResponse.getData());
                    txtNoOfChapters.setText(chapterListResponse.getData().size()+" Chapters");
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void setChapters(ArrayList<Chapter> data) {
        adapterChapter = new AdapterChapter(this,data,subjectName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclChapter.setLayoutManager(layoutManager);
        rclChapter.setHasFixedSize(true);
        rclChapter.setAdapter(adapterChapter);
    }

    private void init() {
        rclChapter = findViewById(R.id.rcl_chapters);
        backButton = findViewById(R.id.backButton);
        txtSubjectName = findViewById(R.id.txt_header);
        txtNoOfChapters = findViewById(R.id.txt_no_of_chapters);
        txtSubjectName.setText(subjectName);
    }
}
