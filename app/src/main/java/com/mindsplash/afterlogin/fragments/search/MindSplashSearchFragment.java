package com.mindsplash.afterlogin.fragments.search;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mindsplash.R;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterChapter;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterConcept;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterSearch;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterSubject;
import com.mindsplash.afterlogin.fragments.search.adapter.OnClickVideo;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.Chapter;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.ConceptListData;
import com.mindsplash.network.model.Question;
import com.mindsplash.network.model.SearchResponse;
import com.mindsplash.network.model.Subject;
import com.mindsplash.services.search.searchserviceimpl.SearchServiceImpl;

import java.util.ArrayList;

public class MindSplashSearchFragment  extends  Fragment implements OnClickVideo {

    private static final String ARG_PARAM1 = "param1";
    private String searchTerm;
    private AdapterSearch adapterGoogle;
    private AdapterChapter adapterChapter;
    private AdapterSubject adapterSubject;
    private AdapterConcept adapterConcepts;
    private RecyclerView rcList = null;
    private Dialog progressDialog = null;
    private RecyclerView rcSubjects = null;
    private RecyclerView rcChapter = null;
    private RecyclerView rcConcept = null;
    private TextView tvSubjects = null;
    private TextView tvConcepts = null;
    private TextView tvChapter = null;
    private TextView tvQuestion = null;



    public MindSplashSearchFragment(){
        // Required empty public constructor
    }

    public static MindSplashSearchFragment newInstance(String param1) {
        MindSplashSearchFragment fragment = new MindSplashSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            searchTerm = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_mind_splash_search, container, false);
        tvQuestion = v.findViewById(R.id.tv_question);
        tvSubjects = v.findViewById(R.id.tv_subject);
        tvChapter = v.findViewById(R.id.tv_chapter);
        tvConcepts = v.findViewById(R.id.tv_concept);
        rcList = v.findViewById(R.id.rv_list);
        rcChapter = v.findViewById(R.id.rv_chapter);
        rcConcept = v.findViewById(R.id.rv_concepts);
        rcSubjects = v.findViewById(R.id.rv_subject);
        progressDialog = getProgressDialog(requireContext());
        String searchStringNoSpaces = searchTerm.replace(" ", "+");
        if(!searchTerm.equals("")) {
            getSearchResults(searchTerm);
        }
        return v;
    }

    @Override
    public void onClick(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public Dialog getProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        return progressDialog;
    }
    private void setQuestions(ArrayList<Question> data) {
        try {
            tvQuestion.setVisibility(View.VISIBLE);
            rcList.setVisibility(View.VISIBLE);
            tvQuestion.setText("Practice Questions"+"("+data.size()+")");
            adapterGoogle = new AdapterSearch(data,requireContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
            rcList.setLayoutManager(layoutManager);
            rcList.setHasFixedSize(true);
            rcList.setAdapter(adapterGoogle);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void setConcepts(ArrayList<Concept> data) {
        tvConcepts.setVisibility(View.VISIBLE);
        rcConcept.setVisibility(View.VISIBLE);
        tvConcepts.setText("Concepts"+"("+data.size()+")");
        adapterConcepts = new AdapterConcept(data,requireContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        rcConcept.setLayoutManager(layoutManager);
        rcConcept.setHasFixedSize(true);
        rcConcept.setAdapter(adapterConcepts);
    }

    private void setSubjects(ArrayList<Subject> data) {
        tvSubjects.setVisibility(View.VISIBLE);
        rcSubjects.setVisibility(View.VISIBLE);
        tvSubjects.setText("Subjects"+"("+data.size()+")");
        adapterSubject = new AdapterSubject(data,requireContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        rcSubjects.setLayoutManager(layoutManager);
        rcSubjects.setHasFixedSize(true);
        rcSubjects.setAdapter(adapterSubject);
    }

    private void setChapter(ArrayList<Chapter> data) {
        tvChapter.setVisibility(View.VISIBLE);
        rcChapter.setVisibility(View.VISIBLE);
        tvChapter.setText("Chapters"+"("+data.size()+")");
        adapterChapter = new AdapterChapter(data,requireContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        rcChapter.setLayoutManager(layoutManager);
        rcChapter.setHasFixedSize(true);
        rcChapter.setAdapter(adapterChapter);
    }






    private void getSearchResults(String searchTerm) {
        SearchServiceImpl searchService = new SearchServiceImpl();
        progressDialog.show();
        searchService.getSearchList(searchTerm, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    progressDialog.dismiss();
                    SearchResponse searchResponse = (SearchResponse) object;

                    if(searchResponse.data.question.size()>0)
                       setQuestions((ArrayList<Question>) searchResponse.data.question);
                     if(searchResponse.data.chapter.size()>0)
                        setChapter((ArrayList<Chapter>) searchResponse.data.chapter);
                     if(searchResponse.data.concept.size()>0)
                        setConcepts((ArrayList<Concept>) searchResponse.data.concept.get(0));
                     if(searchResponse.data.subject.size()>0)
                        setSubjects((ArrayList<Subject>) searchResponse.data.subject);
                     if(searchResponse.data.question.size()>0 || searchResponse.data.chapter.size()>0 || searchResponse.data.concept.size()>0 ||searchResponse.data.subject.size()>0 ) {
                     }
                    else
                        Toast.makeText(requireContext(),"no Data Found",Toast.LENGTH_SHORT).show();
                    Log.e("result",searchResponse.data.question.size()+"");
                }
            }

            @Override
            public void onError(Object object) {
                Log.e("error",object.toString());
                progressDialog.dismiss();
                Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}