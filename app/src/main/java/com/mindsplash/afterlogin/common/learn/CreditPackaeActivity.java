package com.mindsplash.afterlogin.common.learn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.fragments.home.adapters.AdapterSubjects;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.Subject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreditPackaeActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private RecyclerView rclSubjects;
    private AdapterSubjects adapterSubjects;
    private ImageView backButton,imgProfile;
    private LinearLayout anyQueslyt,tenQueslyt,twentyQueslyt;

//    private ActivityCreditPackageBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_credit_package);
        onBackClick();
        onClickProfile();
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreditPackaeActivity.this, MyAccount.class));
            }
        });
    }

    private void onBackClick() {
        backButton = findViewById(R.id.backButton);
        imgProfile = findViewById(R.id.profile);

        twentyQueslyt = findViewById(R.id.twentyQueslyt);
        anyQueslyt = findViewById(R.id.anyQueslyt);
        tenQueslyt = findViewById(R.id.tenQueslyt);
        anyQueslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("500");
            }
        });
   tenQueslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("100");
            }
        });
   twentyQueslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("200");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void startPayment(String payments) {
//        String rZPKey = "rzp_test_B83u1SetMHNsLV";
        AppSharedPreference appSharedPreference = AppSharedPreference.getMInstance();
        Student student  = new Gson().fromJson(
                appSharedPreference.getStudentDetails(),
                Student.class);
        String rZPKey = "rzp_test_B83u1SetMHNsLV";
        Log.e("rzorid", rZPKey);
        Checkout checkout = new Checkout();
        checkout.setKeyID(rZPKey);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "" + getResources().getString(R.string.app_name));
            jsonObject.put("description", "Buying Credit");
            jsonObject.put("currency", "INR");
            jsonObject.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            jsonObject.put("payment_capture", true);
            jsonObject.put("theme.color", "#fdc830");
            jsonObject.put("prefill.email",student.getParentemail() );
            jsonObject.put("prefill.contact", student.getMobileno());
            String payment = "" + payments;
            double totalPay = Double.parseDouble(payment);
            totalPay = totalPay * 100;
            jsonObject.put("amount", "" + totalPay);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            jsonObject.put("retry", retryObj);
            checkout.open(this, jsonObject);

        } catch (JSONException e) {
            Toast.makeText(this, getResources().getString(R.string.Error_in_payment), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(getApplicationContext(), "Successfully payment done", Toast.LENGTH_SHORT).show();
        Log.e("success",new Gson().toJson(paymentData));
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e("fail",paymentData.getData().toString());
    }

}
