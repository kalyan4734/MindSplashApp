package com.mindsplash.afterlogin.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.fragments.home.Home;
import com.mindsplash.beforelogin.RegisterActivity;
import com.mindsplash.beforelogin.dialogs.DialogVerifyEmailSent;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.Student;
import com.mindsplash.services.ask_ques.AskQuesService;
import com.mindsplash.services.ask_ques.AskQuesServiceImpl;

public class TypeAsk extends Fragment implements HomeActivity.OnBackPressedListener {
    ImageView backButton;
    TextView header_title;
    MaterialButton postbtn;
    EditText typeEdit;
    private ImageView mLogo;
    private Dialog progressDialog;
    public TypeAsk() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        backButton = view.findViewById(R.id.backButton);
        header_title = (TextView) view.findViewById(R.id.header_title);
        postbtn = view.findViewById(R.id.postbtn);
        typeEdit = view.findViewById(R.id.typeEdit);
        progressDialog = new AppUtils().getProgressDialog(requireActivity());
        header_title.setText("ASK");
        mLogo = view.findViewById(R.id.logo);
        mLogo.setVisibility(View.GONE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                doBack();
            }
        });
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), MyAccount.class));
            }
        });
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!typeEdit.getText().toString().isEmpty()){
                    postQiest(typeEdit.getText().toString());
                }else {
                    AppUtils.showToast(requireActivity(), "Please type your question!");
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void doBack() {
        if (getActivity() != null) {
            Home home = new Home();
            ((HomeActivity) getActivity()).backFragment(home, "Home");
        }
    }
    private void postQiest(String text ) {
        progressDialog.show();
        AppSharedPreference appSharedPreference = AppSharedPreference.getMInstance();
        Student student  = new Gson().fromJson(
                appSharedPreference.getStudentDetails(),
                Student.class);

        AskQuesService conceptService = new AskQuesServiceImpl();
        conceptService.postAskQues(
                student.getId(),
                text, null, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        progressDialog.dismiss();
                        if (object != null) {
                            Toast.makeText(getContext(), "Question has been added successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        progressDialog.dismiss();
                    }
        });
    }

}