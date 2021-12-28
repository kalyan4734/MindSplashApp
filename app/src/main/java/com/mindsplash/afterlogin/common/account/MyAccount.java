package com.mindsplash.afterlogin.common.account;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.common.learn.CreditPackaeActivity;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;

public class MyAccount extends AppCompatActivity {
    private ImageView imgBack;
    private AppSharedPreference appSharedPreference;
    private EditText edtFirstName, edtLastName, edtParentEmail;
    private TextView txtClass;
    private ImageView imgDropDown, imgUp;
    private ConstraintLayout clProfile;
    private MaterialButton btn_credits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
        onClickBack();
        setAccountDetails();
        onClickDropDown();
        onClickUp();
    }

    private void onClickUp() {
        imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clProfile.setVisibility(View.GONE);
                imgDropDown.setVisibility(View.VISIBLE);
                imgUp.setVisibility(View.GONE);
            }
        });
        btn_credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreditPackaeActivity.class));

            }
        });
    }

    private void onClickDropDown() {
        imgDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clProfile.setVisibility(View.VISIBLE);
                imgDropDown.setVisibility(View.GONE);
                imgUp.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setAccountDetails(Student student) {
        if (student != null) {
            edtFirstName.setText(student.getFirstname());
            edtLastName.setText(student.getLastname());
            edtParentEmail.setText(student.getParentemail());
            txtClass.setText(student.getClass_());
        } else
            callApi();
    }

    private void setAccountDetails() {
        appSharedPreference = AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(this);
        if (appSharedPreference.getStudentDetails() != null) {
            Student student = new Gson().fromJson(appSharedPreference.getStudentDetails(), Student.class);
            if (student != null) {
                edtFirstName.setText(student.getFirstname());
                edtLastName.setText(student.getLastname());
                edtParentEmail.setText(student.getParentemail());
                txtClass.setText(student.getClass_());
            } else
                callApi();
        }
    }

    private void callApi() {
        Dialog progressDialog = new AppUtils().getProgressDialog(this);
        progressDialog.show();
        StudentService studentService = new StudeServiceImpl();
        studentService.getStudentDetails(appSharedPreference.getStudentMobileNo(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    StudentDetails student = (StudentDetails) object;
                    appSharedPreference.addStudentDetails(new Gson().toJson(student.getStudent()));
                    setAccountDetails(student.getStudent());
                } else
                    new AppUtils().showError(MyAccount.this, "Student Not Found");
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
                new AppUtils().showError(MyAccount.this, object.toString());
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

    private void init() {
        imgBack = findViewById(R.id.backButton);
        imgDropDown = findViewById(R.id.img_drop_down);
        imgUp = findViewById(R.id.img_close);
        txtClass = findViewById(R.id.txt_class);
        clProfile = findViewById(R.id.cl_profile_);

        edtFirstName = findViewById(R.id.first_EditText);
        edtLastName = findViewById(R.id.last_EditText);
        edtParentEmail = findViewById(R.id.pEmail_EditText);
        btn_credits = findViewById(R.id.btn_credits);
    }
}
