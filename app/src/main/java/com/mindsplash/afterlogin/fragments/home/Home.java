package com.mindsplash.afterlogin.fragments.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.AskActivity;
import com.mindsplash.afterlogin.common.learn.CreditPackaeActivity;
import com.mindsplash.afterlogin.common.learn.LearnActivity;
import com.mindsplash.afterlogin.fragments.Ask;
import com.mindsplash.afterlogin.fragments.SearchFragment;
import com.mindsplash.afterlogin.fragments.home.adapters.AdapterSubjects;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.ConnectionDetector;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.Subject;
import com.mindsplash.network.model.SubjectDetails;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    private RecyclerView rclSubjects;
    private MaterialButton btnLearn, btnAsk;
    private ArrayList<Subject> subjectList;
    private AdapterSubjects adapterSubjects;
    private ConnectionDetector connectionDetector;
    private ImageView imgProfile;
    private TextView creditBtn;
    private TextView search_EditText;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        onClickLearn();
        onClickAsk();
        return view;
    }

    private void onClickAsk() {
        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), AskActivity.class));
                Ask hm = new Ask();
                ((HomeActivity) getActivity()).addfragment(hm, "Ask");
            }
        });
        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), CreditPackaeActivity.class));

            }
        });
        search_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment hm = new SearchFragment();
                ((HomeActivity) getActivity()).addfragment(hm, "Search");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (connectionDetector.isConnectingToInternet())
            callGetSubjectsApi();
        else
            new AppUtils().showError(getActivity(), getResources().getString(R.string.check_inter_connection));
    }

    private void callGetSubjectsApi() {
        StudentService studentService = new StudeServiceImpl();
        studentService.getSubjectDetails(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Log.e("response",new Gson().toJson(object));
                if (object != null) {
                    SubjectDetails subjectDetails = (SubjectDetails) object;
                    setSubjects((ArrayList<Subject>) subjectDetails.getData());
                }
            }

            @Override
            public void onError(Object object) {
                new AppUtils().showError(getActivity(), object.toString());
            }
        });

    }

    private void onClickLearn() {
        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LearnActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SUB", subjectList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {
        rclSubjects = view.findViewById(R.id.rcl_subjects);
        btnLearn = view.findViewById(R.id.btn_learn);
        imgProfile = view.findViewById(R.id.profile);
        btnAsk = view.findViewById(R.id.btn_ask);
        creditBtn = view.findViewById(R.id.creditBtn);
        search_EditText = view.findViewById(R.id.search_EditText);
    }

    public void setSubjects(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
        adapterSubjects = new AdapterSubjects(getActivity(), subjectList, null);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rclSubjects.setLayoutManager(layoutManager);
        rclSubjects.setHasFixedSize(true);
        rclSubjects.setAdapter(adapterSubjects);
    }


}