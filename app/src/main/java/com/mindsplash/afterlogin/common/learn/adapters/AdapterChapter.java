package com.mindsplash.afterlogin.common.learn.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.activities.ConceptPractiveActivity;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Chapter;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;

import java.util.ArrayList;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.ChapterView> {
    private ArrayList<Chapter> chapterArrayList;
    private String subjectName;
    private Context contex;
    private AppSharedPreference appSharedPreference;

    public AdapterChapter(Context context, ArrayList<Chapter> chapterArrayList, String subjectName) {
        this.subjectName = subjectName;
        this.chapterArrayList = chapterArrayList;
        this.contex = context;
        appSharedPreference = AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(context);
    }

    @NonNull
    @Override
    public ChapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chapter,parent,false);
        return new ChapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterView holder, int position) {
        Chapter chapter = chapterArrayList.get(position);
        holder.txtChapterName.setText(chapter.getChaptername());
        holder.lblChapterNo.setText(chapter.getChapterno());
    }

    @Override
    public int getItemCount() {
        return chapterArrayList.size();
    }

    public class ChapterView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtChapterName,lblChapterNo;
        private LinearLayout llChapter;
        private ImageView imgBookmark;
        public ChapterView(View view) {
            super(view);
            txtChapterName = view.findViewById(R.id.txt_chapter_name);
            lblChapterNo = view.findViewById(R.id.txt_no_of_chapters);
            llChapter = view.findViewById(R.id.ll_chapters);
            imgBookmark = view.findViewById(R.id.bookmark);
            llChapter.setOnClickListener(this);
            txtChapterName.setOnClickListener(this);
            imgBookmark.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.txt_chapter_name:
                    Intent intent = new Intent(contex, ConceptPractiveActivity.class);
                    intent.putExtra(Constants.SUBJ_NAME, subjectName);
                    intent.putExtra(Constants.CHAP_NAME, chapterArrayList.get(getAdapterPosition()).getChaptername());
                    intent.putExtra(Constants.SUBJ_ID, chapterArrayList.get(getAdapterPosition()).getSid());
                    intent.putExtra(Constants.CHAP_ID, chapterArrayList.get(getAdapterPosition()).getCid());
                    contex.startActivity(intent);
                    break;
                case R.id.bookmark:
                    Dialog dialog = new AppUtils().getProgressDialog(contex);
                    StudentService studentService = new StudeServiceImpl();
                    dialog.show();
                    if (appSharedPreference.getStudent()!=null) {
                        Student student = appSharedPreference.getStudent();
                        studentService.bookmark(student.getId(), subjectName, "", "", new CallBack() {
                            @Override
                            public void onSuccess(Object object) {
                                dialog.dismiss();
                                if (object!=null) {
                                    Toast.makeText(contex, subjectName+" bookmarked sucessfully.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(Object object) {
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
            }
        }
    }
}
