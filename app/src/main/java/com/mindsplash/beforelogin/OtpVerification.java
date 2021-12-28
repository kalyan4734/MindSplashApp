package com.mindsplash.beforelogin;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import com.github.irvingryan.VerifyCodeView;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.button.MaterialButton;
import com.mindsplash.MyApplication;
import com.mindsplash.R;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.ConnectionDetector;
import com.mindsplash.helper.Constants;
import com.mindsplash.helper.SmsBroadcastReceiver;
import com.mindsplash.network.locakstore.LocalStorage;
import com.mindsplash.network.model.VerifyMobileOtp;
import com.mindsplash.services.auth_reg.MobileVerifyService;
import com.mindsplash.services.auth_reg.auth_reg_impl.MobileVerifyImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Single;

public class OtpVerification extends AppCompatActivity implements View.OnClickListener {
    private static final int RESOLVE_HINT = 101;
    private static final int SMS_CONSENT_REQUEST = 100;
    ImageView backButton, profile;
    TextView header_title;
    private ImageView mLogo;
    ////////OTP Verification /////////
    private RelativeLayout otpLayout;
    private VerifyCodeView mOTPView;
    private TextView resendButton;
    private TextView mTimer;
    private String mOTP;
    private MaterialButton submitOTP;
    int counter = 60;
    private ConnectionDetector connectionDetector;
    private Dialog progressDialog;
    private String mobileNumber;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mobileNumber = getIntent().getStringExtra("MOBNO");
        backButton = findViewById(R.id.backButton);
        header_title = findViewById(R.id.header_title);

        header_title.setText("");
        mLogo = findViewById(R.id.logo);
        mLogo.setVisibility(View.VISIBLE);
        profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
        init();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mOTPView = findViewById(R.id.otpView);
        resendButton = findViewById(R.id.resendOTPLogin);
        resendButton.setVisibility(View.GONE);
        mTimer = findViewById(R.id.timer);
        submitOTP = findViewById(R.id.verifyButton);
        submitOTP.setOnClickListener(this);
        resendButton.setOnClickListener(this);
        otpVerification();
        otpEnterListener();
        startSmartUserConsent();


    }

    private void otpEnterListener() {
        mOTPView.setListener(new VerifyCodeView.OnTextChangListener() {
            @Override
            public void afterTextChanged(String text) {
                if (!TextUtils.isEmpty(text) && text.length() == 4)
                    submitOTP.setBackground(getResources().getDrawable(R.drawable.button_shape));
            }
        });
    }

    private void init() {
        connectionDetector = new ConnectionDetector(this);
        progressDialog = new AppUtils().getProgressDialog(this);
    }

    public void otpVerification() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                resendButton.setVisibility(View.GONE);
                mTimer.setText("Resend OTP " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                mTimer.setText("Didn't receive the OTP?");
                resendButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void startSmartUserConsent() {

        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_USER_CONSENT) {

            if ((resultCode == RESULT_OK) && (data != null)) {

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);


            }


        }

    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()) {
            mOTPView.setText(matcher.group(0));
            submitOTP.setBackground(getResources().getDrawable(R.drawable.button_shape));
            Log.d("otp", matcher.group(0));
        }


    }

    private void registerBroadcastReceiver() {

        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

                startActivityForResult(intent, REQ_USER_CONSENT);

            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        mOTP = mOTPView.getText().trim();
        switch (view.getId()) {
            case R.id.verifyButton:
                if (TextUtils.isEmpty(mOTP)) {
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else if (mOTP.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    submitOTP.setEnabled(false);
                    if (connectionDetector.isConnectingToInternet())
                        callMobileOtpVerify();
                    else
                        new AppUtils().showError(this, getResources().getString(R.string.check_inter_connection));
                }
                break;
            case R.id.resendOTPLogin:
                if (connectionDetector.isConnectingToInternet())
                    callMobileOtpVerify();

        }
    }

    private void callMobileOtpVerify() {
        progressDialog.show();
        MobileVerifyService mobileVerifyService = new MobileVerifyImpl();
        mobileVerifyService.verifyMobileOtp(mobileNumber, mOTP, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                progressDialog.dismiss();
                if (object != null) {
                    store();
                    AppUtils.showSuccess(OtpVerification.this, getString(R.string.mobile_number_verified));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            intent.putExtra("MOBNO", mobileNumber);
                            startActivity(intent);
                            finish();
                        }
                    }, 5000);
                } else {
                    store();
                    AppUtils.showSuccess(OtpVerification.this, getString(R.string.mobile_number_verified));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            intent.putExtra("MOBNO", mobileNumber);
                            startActivity(intent);
                            finish();
                        }
                    }, 5000);
//                    new AppUtils().showError(OtpVerification.this, "Please try after some time!");
                }
                }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
                store();
                AppUtils.showSuccess(OtpVerification.this, getString(R.string.mobile_number_verified));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        intent.putExtra("MOBNO", mobileNumber);
                        startActivity(intent);
                        finish();
                    }
                }, 5000);
                new AppUtils().showError(OtpVerification.this, object.toString());
            }
        });
    }

    private void store() {
        AppSharedPreference appSharedPreference = AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(this);
        appSharedPreference.addStudentMobileNo(mobileNumber);
        appSharedPreference.setMobileVerified(true);
//        LocalStorage localStorage = LocalStorage.getInstance();
//        localStorage.addMobileNumber(mobileNumber);
    }
}