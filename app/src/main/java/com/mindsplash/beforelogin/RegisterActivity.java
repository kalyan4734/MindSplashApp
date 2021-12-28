package com.mindsplash.beforelogin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.beforelogin.dialogs.DialogClassSelection;
import com.mindsplash.beforelogin.dialogs.DialogVerifyEmailSent;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.ConnectionDetector;
import com.mindsplash.network.model.ApiStandardResponse;
import com.mindsplash.network.model.VerifyEmailResponse;
import com.mindsplash.services.auth_reg.MobileVerifyService;
import com.mindsplash.services.auth_reg.auth_reg_impl.MobileVerifyImpl;

public class RegisterActivity extends AppCompatActivity {
    ImageView backButton, profile;
    TextView header_title;
    private ImageView mLogo;
    private ConnectionDetector connectionDetector;
    private Dialog progressDialog;
    private MaterialButton signup, btnVerifyEmail,btnSkip;
    private EditText edtMobileNo, edtFirstName, edtLastName, edtParentEmail;
    private ConstraintLayout edtClass;
    public TextView txtClass;
    private AppSharedPreference appSharedPreference;
    private String mobileNumber;
    private ConstraintLayout cl_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        appSharedPreference = AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(this);
        connectionDetector = new ConnectionDetector(this);
        progressDialog = new AppUtils().getProgressDialog(this);
        mobileNumber = getIntent().getStringExtra("MOBNO");
        init();
        onBackClick();
        onClickSignUp();
        onClickVerifyEmail();
        onClickSkip();
        onClickClass();
        onEmailChange();
    }

    private void onClickSkip() {
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setEnabled(true);
                signup.setBackground(getResources().getDrawable(R.drawable.button_shape));
            }
        });
    }

    private void onEmailChange() {
        edtParentEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0) {
                    signup.setBackground(getResources().getDrawable(R.drawable.gray_rounded_button));
//                    signup.setEnabled(false);
                } else {
                    signup.setBackground(getResources().getDrawable(R.drawable.button_shape));
                    signup.setEnabled(true);
                }
                if (charSequence.length() > 0 && AppUtils.isValidEmail(charSequence.toString())) {
                    btnVerifyEmail.setVisibility(View.GONE);
                }
                else{
                    btnVerifyEmail.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setClassSelected(String classSelected) {
        txtClass.setText(classSelected);
        signup.setBackground(getResources().getDrawable(R.drawable.button_shape));
    }

    private void onClickClass() {
        edtClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClassSelection dialog = DialogClassSelection.newInstance(RegisterActivity.this);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private void onClickVerifyEmail() {
        btnVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectionDetector.isConnectingToInternet())
                    onClickEmailVerify();
                else
                    new AppUtils().showError(RegisterActivity.this, getResources().getString(R.string.check_inter_connection));
            }
        });
    }

    private void onClickEmailVerify() {
        progressDialog.show();
        MobileVerifyService mobileVerifyService = new MobileVerifyImpl();
        mobileVerifyService.verifyEmailId(edtMobileNo.getText().toString().trim(), edtParentEmail.getText().toString(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    DialogVerifyEmailSent dialog = DialogVerifyEmailSent.newInstance(RegisterActivity.this);
                    dialog.show(getSupportFragmentManager(), "dialog");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            signup.setEnabled(true);
                            signup.setBackground(getResources().getDrawable(R.drawable.button_shape));
                        }
                    },1000);
                }
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
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

    private void init() {
        signup = findViewById(R.id.signUp);
        backButton = findViewById(R.id.backButton);
        header_title = findViewById(R.id.header_title);
        header_title.setText("REGISTER");
        mLogo = findViewById(R.id.logo);
        mLogo.setVisibility(View.GONE);
        profile = findViewById(R.id.profile);
        btnSkip = findViewById(R.id.btn_skip);
        profile.setVisibility(View.GONE);
        edtMobileNo = findViewById(R.id.editTextMobile);
        edtFirstName = findViewById(R.id.first_EditText);
        edtLastName = findViewById(R.id.last_EditText);
        edtClass = findViewById(R.id.class_EditText);
        txtClass = findViewById(R.id.txt_class);
        edtParentEmail = findViewById(R.id.pEmail_EditText);
        btnVerifyEmail = findViewById(R.id.btn_verify_email);
        cl_skip = findViewById(R.id.cl_skip);

        edtMobileNo.setText(mobileNumber);
    }

    private void onClickSignUp() {
        signup.setOnClickListener(view -> {
            if (validate())
                if (connectionDetector.isConnectingToInternet())
                    callCreateStudent();
                else
                    new AppUtils().showError(RegisterActivity.this, getResources().getString(R.string.check_inter_connection));
        });
    }

    private boolean validate() {
        boolean flag = false;
        String mobileNumber = edtMobileNo.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String class_ = txtClass.getText().toString().trim();
        String parentEmail = edtParentEmail.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumber))
            AppUtils.showToast(this, getString(R.string.enter_mobile_number));
        else if (mobileNumber.length() != 10)
            AppUtils.showToast(this, getString(R.string.enter_valid_mobile_number));
        else if (TextUtils.isEmpty(firstName))
            AppUtils.showToast(this, getString(R.string.enter_first_name));
        else if (TextUtils.isEmpty(lastName))
            AppUtils.showToast(this, getString(R.string.enter_last_name));
        else if (TextUtils.isEmpty(class_))
            AppUtils.showToast(this, getString(R.string.please_select_class));
        else if (!TextUtils.isEmpty(parentEmail)) {
            if (!AppUtils.isValidEmail(parentEmail))
                AppUtils.showToast(this, getString(R.string.enter_valid_email));
            else
                flag = true;
        }
       else
            flag = true;
        return flag;
    }

    private void callCreateStudent() {
        progressDialog.show();
        MobileVerifyService mobileVerifyService = new MobileVerifyImpl();
        String firstName="";
        String lastName="";
        String class_="";
        String parentEmail="";
        try {
            mobileNumber = edtMobileNo.getText().toString().trim();
            firstName = edtFirstName.getText().toString().trim();
            lastName = edtLastName.getText().toString().trim();
            class_ = txtClass.getText().toString();
//            mobileNumber = Base64.encodeToString(edtMobileNo.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT);
//            firstName = Base64.encodeToString(edtFirstName.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT);
//            lastName = Base64.encodeToString(edtLastName.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT);
//            class_ = Base64.encodeToString(txtClass.getText().toString().getBytes("UTF-8"), Base64.DEFAULT);
//            parentEmail = Base64.encodeToString(edtParentEmail.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT);
        } catch (Exception e) {}
        mobileVerifyService.createStudent(mobileNumber, firstName, lastName, parentEmail, Integer.parseInt(class_), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    appSharedPreference.setRegistered(true);
                    ApiStandardResponse apiStandardResponse = (ApiStandardResponse) object;
                    AppUtils.showSuccess(RegisterActivity.this, apiStandardResponse.getMessage());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else
                    new AppUtils().showError(RegisterActivity.this, "Please try after some time!");
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
                new AppUtils().showError(RegisterActivity.this, object.toString());
            }
        });
    }
}